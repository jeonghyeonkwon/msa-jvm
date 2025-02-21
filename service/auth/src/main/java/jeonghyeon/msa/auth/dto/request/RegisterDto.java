package jeonghyeon.msa.auth.dto.request;

import lombok.Data;

@Data
public class RegisterDto {
    private String username;
    private String password;
    private String nickName;

    public RegisterDto(String username, String password, String nickName) {
        this.username = username;
        this.password = password;
        this.nickName = nickName;
    }
}
