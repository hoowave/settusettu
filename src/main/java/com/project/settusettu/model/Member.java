package com.project.settusettu.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tbl_member")
@Data
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mno;
    private String loginId;
    private String loginPw;
    private String gender;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;
    private String email;
    private String matchCode;
    private String permit;
}
