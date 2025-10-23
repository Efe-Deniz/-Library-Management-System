package com.aliefedeniz.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "publishers")
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",length = 100,nullable = false)
    private String name;


    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "publishers")
    private Set<Book> bookSet = new HashSet<>();

    public Publisher(String name){
        this.name=name;
    }
}
