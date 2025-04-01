package jeonghyeon.msa.common.event;

import jeonghyeon.msa.common.event.payload.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@RequiredArgsConstructor
public enum EventType {
    AUTH_CREATE(AuthCreateEventPayload.class, Topic.MSA_AUTH),

    BOARD_CREATE(BoardCreateEventPayload.class, Topic.MSA_BOARD),
    BOARD_VIEW(BoardViewEventPayload.class, Topic.MSA_BOARD_VIEW),
    BOARD_LIKE_CREATE(BoardLikeCreateEventPayload.class, Topic.MSA_BOARD_LIKE),
    BOARD_LIKE_REMOVE(BoardLikeDeleteEventPayload.class, Topic.MSA_BOARD_LIKE),
    BOARD_COMMENT_CREATE(CommentCreateEventPayload.class, Topic.MSA_BOARD_COMMENT);


    private final Class<? extends EventPayload> payloadClass;
    private final String topic;

    public static EventType from(String type) {
        try {
            return valueOf(type);
        } catch (Exception e) {
            log.error("[EventType.from] type = {}", type, e);
            return null;
        }
    }

    public static class Topic {
        public static final String MSA_AUTH = "auth-users";
        public static final String MSA_BOARD = "board";
        public static final String MSA_BOARD_VIEW = "board-view";
        public static final String MSA_BOARD_COMMENT = "board-comment";
        public static final String MSA_BOARD_LIKE = "board-like";


    }
}
