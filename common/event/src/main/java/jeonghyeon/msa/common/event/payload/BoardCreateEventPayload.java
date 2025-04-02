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
public class BoardCreateEventPayload implements EventPayload {
    private Long boardId;
    private String title;
    private String username;
    private static final String TYPE = "create";
    private String createdAt;
}
