package com.project.settusettu.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_email_auth")
@Data
public class Email_auth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int eno;

    private String email;

    private String code;

    @CreationTimestamp
    private LocalDateTime date;

}
