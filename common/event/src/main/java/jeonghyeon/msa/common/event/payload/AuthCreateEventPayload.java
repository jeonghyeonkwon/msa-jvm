package jeonghyeon.msa.common.event.payload;

import jeonghyeon.msa.common.event.EventPayload;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthCreateEventPayload implements EventPayload {
    private String usersId;
    private String username;

    public AuthCreateEventPayload(Long usersId, String username){
        this.usersId = usersId.toString();
        this.username = username;
    }
}
