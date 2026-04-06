package com.example.Synapse.config;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.RemoveBucketArgs;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // Аннотация Lombok — сама сделает конструктор для minioClient
public class MinioInitializer {

    private final MinioClient minioClient;

    @Value("${minio.bucket}")
    private String bucketName;

    @PostConstruct
    public void init() {
        try {

            boolean found = minioClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(bucketName)
                    .build());

            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder()
                        .bucket(bucketName)
                        .build());
                System.out.println("Bucket " + bucketName + " created!");
            }
        } catch (Exception e) {
            throw new RuntimeException("Cant initialize MinIO: " + e.getMessage());
        }
    }
}