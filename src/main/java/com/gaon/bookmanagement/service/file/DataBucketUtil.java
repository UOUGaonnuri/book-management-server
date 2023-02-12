package com.gaon.bookmanagement.service.file;

import com.gaon.bookmanagement.dto.response.FileDto;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.concurrent.ThreadLocalRandom;


@Slf4j
@Component
public class DataBucketUtil {

    @Value("${gcp.config.file}")
    private String gcpConfigFile;

    @Value("${gcp.project.id}")
    private String gcpProjectId;

    @Value("${gcp.bucket.id}")
    private String gcpBucketId;

    @Value("${gcp.dir.name}")
    private String gcpDirectoryName;

    public FileDto uploadFile(MultipartFile file, String fileName, String fileContentType) throws IOException{

        byte[] fileData = FileUtils.readFileToByteArray(convertFile(file));

        InputStream inputStream = new ClassPathResource(gcpConfigFile).getInputStream();

        StorageOptions options = StorageOptions.newBuilder()
                .setProjectId(gcpProjectId)
                .setCredentials(GoogleCredentials.fromStream(inputStream))
                .build();

        Storage storage = options.getService();
        Bucket bucket = storage.get(gcpBucketId, Storage.BucketGetOption.fields());

        RandomString id = new RandomString(6, ThreadLocalRandom.current());
        Blob blob = bucket.create(gcpDirectoryName + "/" + fileName + "-" + id.nextString() + checkFileExtension(fileName), fileData, fileContentType);

        return new FileDto(blob.getName(), blob.getMediaLink());
    }

    private File convertFile(MultipartFile file) {
        try {
            if(file.getOriginalFilename() == null) {
                throw new IllegalArgumentException("Origianl File Name is null!!!");
            }
            File convertedFile = new File(file.getOriginalFilename());
            FileOutputStream fileOutputStream = new FileOutputStream(convertedFile);
            fileOutputStream.write(file.getBytes());
            fileOutputStream.close();
            log.info("Converting multipart file : {}", convertedFile);
            return convertedFile;

        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("FileNotFoundException message : {}", e);
        } catch (IOException e) {
            throw new IllegalArgumentException("IOException message : {}", e);
        }
    }

    private String checkFileExtension(String fileName) {
        if(fileName != null && fileName.contains(".")) {
            String[] extensionList = {".jpeg", ".png", ".pdf"};

            for(String extension : extensionList) {
                if(fileName.endsWith(extension)) {
                    return extension;
                }
            }
        }

        throw new IllegalArgumentException("Not a permitted file type");
    }

}
