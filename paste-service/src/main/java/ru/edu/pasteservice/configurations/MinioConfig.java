package ru.edu.pasteservice.configurations;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RequiredArgsConstructor
@Configuration
public class MinioConfig {

    @Value("${minio.keys.access-key}")
    private String accessKey;
    @Value("${minio.keys.secret-key}")
    private String secretKey;

    @Bean
    public MinioClient minioClient() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        MinioClient minioClient = MinioClient.builder().endpoint("http://localhost:9000")
                .credentials(accessKey, secretKey).build();
        if(!minioClient.bucketExists(BucketExistsArgs.builder().bucket("pastes").build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket("pastes").build());
        }
        return minioClient;
    }
}
