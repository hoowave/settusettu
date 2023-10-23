package com.project.settusettu.api;

import com.project.settusettu.model.Member;
import com.project.settusettu.service.AuthApiService;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Optional;

@RestController
public class AuthApi {

    private AuthApiService authApiService;

    public AuthApi(AuthApiService authApiService) {
        this.authApiService = authApiService;
    }

    @PostMapping("/auth/email")
    public String step1(String email_01, String email_02) {
        if (!authApiService.validateEmail(email_01, email_02)) {
            return "fail";
        }
        String email = authApiService.translateEmail(email_01, email_02);
        if (!authApiService.duplicationCheck(email)) {
            return "fail";
        }
        authApiService.sendMail(email);
        return "success";
    }

    @PostMapping("/auth/email_auth")
    public String step2(String email_01, String email_02, String code) {
        String email = authApiService.translateEmail(email_01, email_02);
        if (!authApiService.authCode(email, code)) {
            return "fail";
        }
        return email;
    }

    @PostMapping("/auth/idchk")
    public String idchk(String loginid) {
        if (!authApiService.idchk(loginid)) {
            return "fail";
        }
        return "success";
    }

    @PostMapping("/auth/pwchk1")
    public String pwchk1(String loginpw01) {
        if (!authApiService.pwchk1(loginpw01)) {
            return "fail";
        }
        return "success";
    }

    @PostMapping("/auth/pwchk2")
    public String pwchk2(String loginpw01, String loginpw02) {
        if (!authApiService.pwchk2(loginpw01, loginpw02)) {
            return "fail";
        }
        return "success";
    }

    @PostMapping("/auth/birthchk")
    public String birthchk(String birth) {
        if (!authApiService.birthchk(birth)) {
            return "fail";
        }
        return "success";
    }

    @PostMapping("/auth/join")
    public String join(Member member, String loginpw02) {
        System.out.println("loginpw02 = " + loginpw02);
        if (!authApiService.idchk(member.getLoginId())
                || !authApiService.pwchk1(member.getLoginPw())
                || !authApiService.pwchk2(member.getLoginPw(), loginpw02)
                || member.getBirth() == null) {
            return "fail";
        }
        member.setMatchCode(authApiService.makeMatchCode(member.getEmail()));
        member.setPermit("USER");
        member.setLoginPw(authApiService.pwEnc(member.getLoginPw()));
        System.out.println("member = " + member);
        authApiService.join(member);
        return "success";
    }

}
