package com.project.settusettu.service;

import com.project.settusettu.config.auth.PrincipalDetails;
import com.project.settusettu.model.Email_auth;
import com.project.settusettu.model.Member;
import com.project.settusettu.repository.EmailAuthRepository;
import com.project.settusettu.repository.MemberRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthApiService implements UserDetailsService {

    private MemberRepository memberRepository;
    private JavaMailSender mailSender;
    private PasswordEncoder passwordEncoder;
    private EmailAuthRepository emailAuthRepository;

    public AuthApiService(MemberRepository memberRepository, JavaMailSender mailSender, PasswordEncoder passwordEncoder, EmailAuthRepository emailAuthRepository) {
        this.memberRepository = memberRepository;
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;
        this.emailAuthRepository = emailAuthRepository;
    }

    public boolean validateEmail(String email_01, String email_02) {
        if (email_01.equals("") || email_02.equals("")) {
            return false;
        }
        return true;
    }

    public String translateEmail(String email_01, String email_02) {
        return email_01 + "@" + email_02;
    }

    public boolean duplicationCheck(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public void sendMail(String email) {
        Email_auth email_auth = new Email_auth();
        // 이메일 index 0~4까지 자른 값을 인코딩, 인코딩 한 값을 index 7~15까지 자름
        String auth_code = passwordEncoder.encode(email.substring(0, 4)).substring(7, 15);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom("i_n_t_e_r_n@naver.com");
        message.setSubject("[우린 셋뚜셋뚜] 인증요청");
        message.setText("인증코드 : " + auth_code);
        mailSender.send(message);
        email_auth.setEmail(email);
        email_auth.setCode(auth_code);
        emailAuthRepository.save(email_auth);
    }

    public boolean authCode(String email, String code) {
        Optional<Email_auth> tbl_auth = emailAuthRepository.findByEmail(email);
        if (tbl_auth.isEmpty()) {
            return false;
        }
        // 이메일 기준 최신 로그값 가져옴
        Email_auth db_auth = tbl_auth.get();
        // 로그(5분)만료 및 로그 불일치시
        if (!LocalDateTime.now().isBefore(db_auth.getDate().plusMinutes(5)) || !db_auth.getCode().equals(code)) {
            return false;
        }
        return true;
    }

    public boolean idchk(String loginid) {
        Optional<Member> member = memberRepository.findByLoginId(loginid);
        if (member.isEmpty() || loginid.length() >= 6 && loginid.length() <= 20) {
            return true;
        }
        return false;
    }

    public boolean pwchk1(String loginpw01) {
        if (loginpw01.length() >= 6 && loginpw01.length() <= 20) {
            return true;
        }
        return false;
    }

    public boolean pwchk2(String loginpw01, String loginpw02) {
        if (loginpw01.equals(loginpw02)) {
            return true;
        }
        return false;
    }

    public boolean birthchk(String birth) {
        if (birth.equals("")) {
            return false;
        }
        return true;
    }

    public String makeMatchCode(String email) {
        String match_code = passwordEncoder.encode(email.substring(0, 4)).substring(16, 22);
        return match_code;
    }

    public String pwEnc(String loginpw) {
        return passwordEncoder.encode(loginpw);
    }

    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username = " + username);
        Optional<Member> member = memberRepository.findByLoginId(username);
        if (member.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username);
        }
        return new PrincipalDetails(member.get());
    }
}
