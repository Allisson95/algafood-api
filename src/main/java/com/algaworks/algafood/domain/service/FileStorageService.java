package com.algaworks.algafood.domain.service;

import java.io.InputStream;
import java.net.URL;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

public interface FileStorageService {

    RecoveredFile get(String fileName);

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
        private String contentType;
        private InputStream content;
        private Long size;

    }

    @Builder
    @Getter
    class RecoveredFile {

        private InputStream content;
        private URL url;

        public boolean hasUrl() {
            return url != null;
        }

        public boolean hasContent() {
            return content != null;
        }

    }

}
