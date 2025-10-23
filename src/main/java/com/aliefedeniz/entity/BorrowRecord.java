package com.aliefedeniz.entity;


import jakarta.persistence.*;
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
@Table(name = "borrow_records")
public class BorrowRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long borrowRecordId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(name = "books_borrowrecords",
            joinColumns = {@JoinColumn(name = "borrow_record_id")},
            inverseJoinColumns = {@JoinColumn(name = "book_id")})
    private Set<Book> books = new HashSet<>();

    @Column(name = "borrowDate",nullable = false)
    private LocalDate borrowDate;

    @Column(name = "returnDate",nullable = false)
    private LocalDate returnDate;

    @Column(name = "isReturned",nullable = false)
    private boolean isReturned;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "penalty_id")
    private Penalty penalty;

    @PrePersist
    public void prePersist() {
        this.borrowDate = LocalDate.now();
    }





}
