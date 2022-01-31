package com.algaworks.algafood.core.storage;

import com.algaworks.algafood.core.storage.StorageProperties.S3;
import com.algaworks.algafood.core.storage.StorageProperties.TypeStorage;
import com.algaworks.algafood.domain.service.FileStorageService;
import com.algaworks.algafood.infrastructure.service.LocalFileStorageService;
import com.algaworks.algafood.infrastructure.service.S3FileStorageService;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {

    @Bean
    public AmazonS3 amazonS3(StorageProperties storageProperties) {
        S3 s3Properties = storageProperties.getS3();
        AWSCredentials credentials = new BasicAWSCredentials(s3Properties.getAccessKey(), s3Properties.getSecretKey());

        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(s3Properties.getRegion())
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
