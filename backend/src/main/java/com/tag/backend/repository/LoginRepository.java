package com.tag.backend.repository;

import com.tag.backend.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<Login,Long> {
    @Query("SELECT l FROM Login l WHERE l.email = :email AND l.phone = :phone")
    Optional<Login>  findByEmailAndPhone(String email, String phone);
    Optional<Login> findByEmail(String email);

}
