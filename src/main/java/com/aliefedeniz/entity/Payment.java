package com.aliefedeniz.entity;

import com.aliefedeniz.entity.enums.PaymentMethod;
import com.aliefedeniz.entity.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount",nullable = false)
    private BigDecimal amount;

    @Column(name = "createdAt",nullable = false)
    private LocalDate createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "paymentStatus",nullable = false)
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "paymentMethod",nullable = false)
    private PaymentMethod paymentMethod;

    @OneToOne(fetch = FetchType.LAZY,mappedBy = "payment")
    private Penalty penalty;


}
