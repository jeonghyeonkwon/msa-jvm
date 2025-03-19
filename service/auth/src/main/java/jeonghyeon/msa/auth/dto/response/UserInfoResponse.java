package jeonghyeon.msa.auth.dto.response;

import lombok.Data;

@Data
public class UserInfoResponse {
    private String usersId;
    private String username;

    public UserInfoResponse(Long usersId, String username){
        this.usersId = usersId.toString();
        this.username  = username;
    }
}
