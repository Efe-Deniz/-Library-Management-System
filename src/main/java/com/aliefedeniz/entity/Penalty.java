package com.aliefedeniz.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Table(name = "penaltys")
public class Penalty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount",nullable = false)
    private BigDecimal amount;

    @Column(name = "createdAt",nullable = false)
    private LocalDate createdAt;

    @Column(name = "isPaid",nullable = false)
    private boolean isPaid;

    @OneToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private BorrowRecord borrowRecord;

    @OneToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private Payment payment;
}
