package jeonghyeon.msa.board.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsersResponse {
    private Long usersId;
    private String username;

    public UsersResponse(String usersId, String username) {
        this.usersId = Long.valueOf(usersId);
        this.username = username;
    }
}
