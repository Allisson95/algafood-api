package com.algaworks.algafood.domain.service;

import java.io.InputStream;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

public interface FileStorageService {

    void store(File file);

    void remove(String fileName);

    default String generateRandomName(String originalFileName) {
        return UUID.randomUUID().toString() + "_" + originalFileName;
    }

    default void replace(File newFile, String oldFileName) {
        store(newFile);

        if (oldFileName != null) {
            remove(oldFileName);
        }
    }

    @Builder
    @Getter
    class File {

        private String name;
        private InputStream content;

    }

}
