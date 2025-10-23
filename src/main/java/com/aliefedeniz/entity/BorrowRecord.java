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

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private User user;

    @ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(name = "books_borrowrecords",
            joinColumns = {@JoinColumn(name = "barrow_record_id")},
            inverseJoinColumns = {@JoinColumn(name = "book_id")})
    private Set<Book> bookSet = new HashSet<>();

    @Column(name = "borrowDate",nullable = false)
    private LocalDate borrowDate;

    @Column(name = "returnDate",nullable = false)
    private LocalDate returnDate;

    @Column(name = "isReturned",nullable = false)
    private boolean isReturned;


}
