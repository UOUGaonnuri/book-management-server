#도커 이미지를 빌드하기 위한 파일 -> 이 이미지를 기반으로 도커 컨테이너가 실행됨
FROM openjdk:11
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]