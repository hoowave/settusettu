package com.project.settusettu.repository;

import com.project.settusettu.model.Email_auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EmailAuthRepository extends JpaRepository<Email_auth, Integer> {
    @Query(value = "SELECT * FROM tbl_email_auth WHERE email = :email ORDER BY date DESC LIMIT 1", nativeQuery = true)
    Optional<Email_auth> findByEmail(String email);
}
