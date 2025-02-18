package jeonghyeon.msa.controller;

import jeonghyeon.msa.dto.request.UsersDto;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

import static org.junit.jupiter.api.Assertions.*;


class UserControllerTest {
    RestClient restClient = RestClient.create("http://localhost:9090");

    @Test
    void register(){
        UsersDto dto = new UsersDto("givejeong","1234","nickname");

        String body = restClient.post()
                .uri("/user")
                .body(dto)
                .retrieve()
                .body(String.class);

        System.out.println("usersId : " + body);

    }
}