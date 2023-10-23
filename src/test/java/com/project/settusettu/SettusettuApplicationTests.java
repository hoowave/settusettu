package com.project.settusettu;

import com.project.settusettu.service.AuthApiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SettusettuApplicationTests {

    AuthApiService authApiService;

    @Autowired
    public SettusettuApplicationTests(AuthApiService authApiService) {
        this.authApiService = authApiService;
    }

    @Test
    void contextLoads() {
    }

    @Test
    void step2() {
        String id = "test";
        authApiService.idchk(id);
    }

}
