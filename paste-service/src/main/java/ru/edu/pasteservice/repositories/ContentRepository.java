package ru.edu.pasteservice.repositories;

import io.minio.*;
import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@RequiredArgsConstructor
@Component
public class ContentRepository {
    private final MinioClient minioClient;

    public void putContent(String bucket, String contentName, String content) throws MinioException {
        try {
            byte[] data = content.getBytes();
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucket)
                    .stream(new ByteArrayInputStream(data), data.length, -1)
                    .object(contentName + ".txt")
                    .contentType("text/plain")
                    .build());
        } catch (Exception exception) {
            throw new MinioException(exception.getMessage());
        }
    }

    public String getContent(String bucket, String contentName) throws MinioException {
        try(InputStream inputStream = minioClient.getObject(GetObjectArgs.builder()
                .bucket(bucket)
                .object(contentName + ".txt")
                .build())) {
            return new String(inputStream.readAllBytes());
        } catch (Exception exception) {
            throw new MinioException(exception.getMessage());
        }
    }

    public void deleteContent(String bucket, String contentName) throws MinioException {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucket)
                    .object(contentName + ".txt")
                    .build());
        } catch (Exception exception) {
            throw new MinioException(exception.getMessage());
        }
    }
}
