package jeonghyeon.msa.board.client;

import jakarta.annotation.PostConstruct;
import jeonghyeon.msa.board.dto.response.UsersResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthClient {

    private RestClient restClient;

    @Value("${endpoints.auth-service.url}")
    private String authServiceUrl;

    @PostConstruct
    void initRestClient() {
        restClient = RestClient.create(authServiceUrl);
    }

    public UsersResponse getUserInfo(Long userId) {
        try {
            return restClient.get()
                    .uri("/api/auth/users/{usersId}", userId)
                    .retrieve()
                    .body(UsersResponse.class);

        } catch (Exception e) {
            log.error("[AuthClient.getUserInfo]", e);
        }
        return null;
    }
}
