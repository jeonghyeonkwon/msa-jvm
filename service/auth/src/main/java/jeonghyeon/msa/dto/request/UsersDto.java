package jeonghyeon.msa.dto.request;

import lombok.Data;

@Data
public class UsersDto {
    private String username;
    private String password;
    private String nickName;

    public UsersDto(String username, String password, String nickName) {
        this.username = username;
        this.password = password;
        this.nickName = nickName;
    }
}
