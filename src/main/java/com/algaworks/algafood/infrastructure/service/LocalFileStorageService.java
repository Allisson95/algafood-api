package com.algaworks.algafood.infrastructure.service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FileStorageService;
import com.algaworks.algafood.infrastructure.exception.StorageException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LocalFileStorageService implements FileStorageService {

    private final StorageProperties storageProperties;

    @Override
    public RecoveredFile get(String fileName) {
        try {
            Path target = resolveFilePath(fileName);
            return RecoveredFile.builder()
                    .content(Files.newInputStream(target))
                    .build();
        } catch (Exception e) {
            throw new StorageException("Não foi possível recuperar o arquivo", e);
        }
    }

    @Override
    public void store(File file) {
        try {
            String fileName = file.getName();
            InputStream fileStream = file.getContent();

            Path target = resolveFilePath(fileName);

            Files.copy(fileStream, target);
        } catch (Exception e) {
            throw new StorageException("Não foi possível armazenar o arquivo", e);
        }
    }

    @Override
    public void remove(String fileName) {
        try {
            Path target = resolveFilePath(fileName);
            Files.deleteIfExists(target);
        } catch (Exception e) {
            throw new StorageException("Não foi possível remover o arquivo", e);
        }
    }

    private Path resolveFilePath(String fileName) {
        return storageProperties.getLocal().getDir().resolve(Path.of(fileName));
    }

}
