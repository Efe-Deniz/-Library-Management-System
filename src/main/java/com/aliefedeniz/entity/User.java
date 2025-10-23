package com.aliefedeniz.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
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

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<BorrowRecord> borrowRecords = new HashSet<>();





}
