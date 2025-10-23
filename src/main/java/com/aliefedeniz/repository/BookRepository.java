package com.aliefedeniz.repository;

import com.aliefedeniz.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {

    //Aynı kitap kayıtlı mı kotrol et
    boolean existsByName(String name);


    //isme göre kitap bulma->kismi aramalarda
    List<Book> findByNameContainingIgnoreCase(String name);

    //yazara göre kitap arama
    List<Book> findByAuthorNameContainingIgnoreCase(String author);

    /**
     *
     * List<Book> findByPublishersNameContainingIgnoreCase(String publisherName);
     * * Bu da doğru ama burada ManyToMany ilişki olduğu için,
     *      * Spring Data bu sorguyu JOIN’larla çalıştıracak.
     *      * Yani veritabanında bir kitap birden fazla yayınevine bağlıysa,
     *      * aynı kitabı birden fazla kez döndürebilir.
     */
    @Query("SELECT DISTINCT b FROM Book b JOIN b.publishers p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :publisherName, '%'))")
    List<Book> findDistinctByPublishersName(@Param("publisherName") String publisherName);

    //Sayfalama
    Page<Book> findByNameContainingIgnoreCase(String name, Pageable pageable);






}
