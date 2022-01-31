package com.algaworks.algafood.core.storage;

import com.algaworks.algafood.core.storage.StorageProperties.S3;
import com.algaworks.algafood.core.storage.StorageProperties.TypeStorage;
import com.algaworks.algafood.domain.service.FileStorageService;
import com.algaworks.algafood.infrastructure.service.LocalFileStorageService;
import com.algaworks.algafood.infrastructure.service.S3FileStorageService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class StorageConfig {

    @Bean
    public S3Client amazonS3(StorageProperties storageProperties) {
        S3 s3Properties = storageProperties.getS3();
        AwsCredentials credentials = AwsBasicCredentials.create(s3Properties.getAccessKey(), s3Properties.getSecretKey());

        return S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .region(s3Properties.getRegion())
                .build();
    }

    @Bean
    public FileStorageService storageService(StorageProperties storageProperties) {
        if (storageProperties.getType() == TypeStorage.LOCAL) {
            return new LocalFileStorageService(storageProperties);
        } else {
            return new S3FileStorageService(amazonS3(storageProperties), storageProperties);
        }
    }

}
