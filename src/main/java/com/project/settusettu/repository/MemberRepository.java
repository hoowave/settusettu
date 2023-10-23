package com.project.settusettu.repository;

import com.project.settusettu.model.Email_auth;
import com.project.settusettu.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {

    Optional<Member> findByEmail(String email);

    Optional<Member> findByLoginId(String loginid);

    @Query(value = "SELECT * FROM tbl_member WHERE loginId = :loginid AND loginPw = :loginpw", nativeQuery = true)
    Optional<Member> login(String loginid, String loginpw);

}
