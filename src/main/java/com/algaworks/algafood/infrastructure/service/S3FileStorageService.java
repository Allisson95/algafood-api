package com.algaworks.algafood.infrastructure.service;

import java.net.URL;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FileStorageService;
import com.algaworks.algafood.infrastructure.exception.StorageException;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@RequiredArgsConstructor
public class S3FileStorageService implements FileStorageService {

    private final S3Client s3;
    private final StorageProperties storageProperties;

    @Override
    public RecoveredFile get(String fileName) {
        try {
            String key = resolveKey(fileName);

            GetUrlRequest getUrlRequest = GetUrlRequest.builder()
                    .bucket(storageProperties.getS3().getBucket())
                    .key(key)
                    .build();
            URL url = s3.utilities().getUrl(getUrlRequest);

            return RecoveredFile.builder()
                    .url(url)
                    .build();
        } catch (Exception e) {
            throw new StorageException("Não foi possível recuperar o arquivo", e);
        }
    }

    @Override
    public void store(File file) {
        try {
            String key = resolveKey(file.getName());

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(storageProperties.getS3().getBucket())
                    .key(key)
                    .contentType(file.getContentType())
                    .contentLength(file.getSize())
                    .acl(ObjectCannedACL.PUBLIC_READ)
                    .build();

            s3.putObject(putObjectRequest, RequestBody.fromInputStream(file.getContent(), file.getSize()));
        } catch (Exception e) {
            throw new StorageException("Não foi possível armazenar o arquivo", e);
        }
    }

    @Override
    public void remove(String fileName) {
        try {
            String key = resolveKey(fileName);

            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(storageProperties.getS3().getBucket())
                    .key(key)
                    .build();

            s3.deleteObject(deleteObjectRequest);
        } catch (Exception e) {
            throw new StorageException("Não foi possível remover o arquivo", e);
        }
    }

    private String resolveKey(String fileName) {
        return String.format("%s/%s", storageProperties.getS3().getDir(), fileName);
    }

}
