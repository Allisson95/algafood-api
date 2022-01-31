package com.algaworks.algafood.infrastructure.service;

import java.net.URL;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FileStorageService;
import com.algaworks.algafood.infrastructure.exception.StorageException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class S3FileStorageService implements FileStorageService {

    private final AmazonS3 s3;
    private final StorageProperties storageProperties;

    @Override
    public RecoveredFile get(String fileName) {
        try {
            String key = resolveKey(fileName);

            URL url = s3.getUrl(storageProperties.getS3().getBucket(), key);

            return RecoveredFile.builder()
                    .url(url)
                    .build();
        } catch (Exception e) {
            throw new StorageException("Não foi possível recuperar o arquivo", e);
        }
    }

    @Override
    public void store(File file) {
        AccessControlList acl = new AccessControlList();
        acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);

        try {
            String key = resolveKey(file.getName());

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());

            PutObjectRequest putObjectRequest = new PutObjectRequest(
                    storageProperties.getS3().getBucket(),
                    key,
                    file.getContent(),
                    metadata)
                            .withAccessControlList(acl);

            s3.putObject(putObjectRequest);
        } catch (Exception e) {
            throw new StorageException("Não foi possível armazenar o arquivo", e);
        }
    }

    @Override
    public void remove(String fileName) {
        try {
            String key = resolveKey(fileName);

            DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(storageProperties.getS3().getBucket(),
                    key);

            s3.deleteObject(deleteObjectRequest);
        } catch (Exception e) {
            throw new StorageException("Não foi possível remover o arquivo", e);
        }
    }

    private String resolveKey(String fileName) {
        return String.format("%s/%s", storageProperties.getS3().getDir(), fileName);
    }

}
