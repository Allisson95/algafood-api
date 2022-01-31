package com.algaworks.algafood.core.storage;

import java.nio.file.Path;

import com.amazonaws.regions.Regions;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties("algafood.storage")
public class StorageProperties {

    private Local local = new Local();
    private S3 s3 = new S3();
    private TypeStorage type = TypeStorage.LOCAL;

    public enum TypeStorage {
        LOCAL, S3;
    }

    @Getter
    @Setter
    public static class Local {
        private Path dir;
    }

    @Getter
    @Setter
    public static class S3 {
        private String accessKey;
        private String secretKey;
        private String bucket;
        private Regions region;
        private String dir;
    }

}
