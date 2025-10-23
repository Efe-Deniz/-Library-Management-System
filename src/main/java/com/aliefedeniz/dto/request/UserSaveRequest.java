package com.aliefedeniz.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserSaveRequest {

    @NotNull(message = "Kullanıcı adı boş olamaz")
    private String name;
    @NotNull(message = "Email adresi boş olmaz")
    @Email(message = "Lütfen geçerli bir e-posta giriniz")
    private String email;
    @NotNull(message = "Telefon numarası boş olamaz")
    private String phone;
}
