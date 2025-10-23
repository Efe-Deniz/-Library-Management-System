package com.aliefedeniz.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Table(name = "penalties")
public class Penalty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "createdAt",nullable = false)
    private LocalDate createdAt;

    @Column(name = "isPaid",nullable = false)
    private boolean isPaid;

    @OneToOne(mappedBy = "penalty", fetch = FetchType.LAZY)
    private BorrowRecord borrowRecord;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "payment_id")
    private Payment payment;


    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDate.now();
    }


}
