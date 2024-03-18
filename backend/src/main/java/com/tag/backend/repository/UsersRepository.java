package com.tag.backend.repository;
import com.tag.backend.entity.Login;
import com.tag.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Long>
{
    @Query("SELECT l FROM User l WHERE l.email = :email AND l.phone = :phone")
    Optional<User> findByEmailAndPhone(String email, String phone);
    User findByEmail(String email);
}
