package jeonghyeon.msa.common.event.payload;

import jeonghyeon.msa.common.event.EventPayload;
import lombok.Getter;



@Getter
public class AuthCreateEventPayload implements EventPayload {
    private Long usersId;
    private String username;

    public AuthCreateEventPayload(Long usersId, String username) {
        this.usersId = usersId;
        this.username = username;
    }
}
