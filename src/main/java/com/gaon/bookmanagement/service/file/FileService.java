package com.gaon.bookmanagement.service.file;

import com.gaon.bookmanagement.dto.response.FileDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class FileService {

    private final DataBucketUtil dataBucketUtil;

    public FileDto uploadFIle(MultipartFile file) {
        String fileOriginalName = file.getOriginalFilename();
        if(fileOriginalName == null) {
            throw new IllegalArgumentException("Original file name null!");
        }
        Path path = new File(fileOriginalName).toPath();

        try {
            String contentType = Files.probeContentType(path);
            FileDto fileDto = dataBucketUtil.uploadFile(file, fileOriginalName, contentType);

            if(fileDto != null) {
                log.info("file upload success");
                log.info("file name : {}, file url : {}", fileDto.getFileName(), fileDto.getFileUrl());
            }

            return fileDto;
        } catch (IOException e) {
            log.info("Error occurred while file uploading. Error : ", e);
            throw new IllegalThreadStateException("Error occurred while file uploading");
        }
    }
}
