package com.aliefedeniz.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

import lombok.NoArgsConstructor;


import java.time.LocalDate;


@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",length = 100, nullable = false)
    private String name;

    @Column(name = "email",length = 50,nullable = false,unique = true)
    @Email(message = "Lütfen e-posto formatına uygun giriniz")
    private String email;

    @Column(name = "phone",length = 11,nullable = false)
    private String phone;

    @Column(name = "isMembershipActive", length = 5, nullable = false)
    private Boolean isMembershipActive;

    @Column(name = "createdAt", nullable = false)
    private LocalDate createdAt;




}
