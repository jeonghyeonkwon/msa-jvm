package jeonghyeon.msa.board.dto.request;

import lombok.Data;

@Data
public class UsersRequest {
    private Long usersId;
    private String username;

    public UsersRequest(Long usersId, String username) {
        this.usersId = usersId;
        this.username = username;
    }
}
