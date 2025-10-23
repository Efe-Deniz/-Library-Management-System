package com.aliefedeniz.entity;



import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "authorName", length = 100, nullable = false)
    private String authorName;

    /**
     * Bir kitap (Book), birden fazla kullanıcı (User) tarafından ödünç alınabilir;
     * aynı şekilde bir kullanıcı da birden fazla kitabı ödünç alabilir.
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "books_users", joinColumns = { @JoinColumn(name = "book_id") }, inverseJoinColumns = {
            @JoinColumn(name = "user_id") })
    private User user;

    /**
     * bir kitap birden fazla yayıevin tarafından basılabilir
     * bir yayın evi birden fazla kitap basabilir
     */
    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "books_publishers", joinColumns = { @JoinColumn(name = "book_id") }, inverseJoinColumns = {
            @JoinColumn(name = "publisher_id") })
    private Set<Publisher> publishers = new HashSet<Publisher>();

    public Book(String name, String authorName) {
        this.name = name;
        this.authorName = authorName;
    }

   
}
