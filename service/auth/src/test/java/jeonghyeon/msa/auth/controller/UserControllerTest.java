package jeonghyeon.msa.auth.controller;

import jeonghyeon.msa.auth.dto.request.RegisterDto;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;


class UserControllerTest {
    RestClient restClient = RestClient.create("http://localhost:9090/api/auth");

    @Test
    void register() {
        RegisterDto dto = new RegisterDto("givejeong", "1234", "nickname");

        String body = restClient.post()
                .uri("/user")
                .body(dto)
                .retrieve()
                .body(String.class);

        System.out.println("usersId : " + body);

    }
}