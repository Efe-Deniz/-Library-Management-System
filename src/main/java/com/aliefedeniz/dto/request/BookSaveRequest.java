package com.aliefedeniz.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookSaveRequest {

    @NotNull(message = "Kitap adı boş olamaz")
    private String name;
    @NotNull(message = "Yazar ismi boş olamaz")
    private String author;
    @NotNull(message = "Yayıncı boş olamaz")
    private String publisher;
}
