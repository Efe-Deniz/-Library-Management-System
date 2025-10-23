package com.aliefedeniz.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class BookUpdateRequest {

    @NotNull(message = "Kitap id boş olamaz")
    @Positive(message = "Kitap id negatif olamaz")
    private Long id;
    @NotNull(message = "Kitap adı boş olamaz")
    private String name;
    @NotNull(message = "Yazar ismi boş olamaz")
    private String author;
    @NotNull(message = "Yayıncı boş olamaz")
    private String publisher;
}
