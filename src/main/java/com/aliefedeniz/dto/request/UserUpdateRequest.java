package com.aliefedeniz.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserUpdateRequest {

    @NotNull(message = "Kullanıcı id boş olamaz")
    @Positive(message = "Kullanıcı id negatif olmaz")
    private Long id;

    @NotNull(message = "Kullanıcı ismi boş olamaz")
    private String name;




}
