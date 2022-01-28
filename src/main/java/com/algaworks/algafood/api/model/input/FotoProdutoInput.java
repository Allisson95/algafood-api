package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.algaworks.algafood.core.validation.FileContentType;
import com.algaworks.algafood.core.validation.FileSize;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FotoProdutoInput {

    @FileSize(max = "2MB")
    @FileContentType(allowed = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
    @NotNull
    private MultipartFile arquivo;

    @NotBlank
    private String descricao;

}
