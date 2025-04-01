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
public class BoardLikeDeleteEventPayload implements EventPayload {
    private Long boardId;
    private static final String type = "delete";
}
