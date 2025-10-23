package com.aliefedeniz.repository;

import com.aliefedeniz.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    //Aynı emaile sahip kullanıcı var mı
    boolean existByEmail(String email);

    //isme göre kullanıcı bulma
    List<User> findByNameContainingIgnoreCase(String name);

    //emaile göre kullanıcı bulma
    List<User> findByEmailContainingIgnoreCase(String email);

    //telefon numarasına göre kullanıcı bulma
    List<User> findByPhoneContainingIgnoreCase(String phone);
}
