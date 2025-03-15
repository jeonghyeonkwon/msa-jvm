package jeonghyeon.msa.board.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jeonghyeon.msa.board.dto.response.CommentResponse;
import jeonghyeon.msa.board.dto.response.QCommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static jeonghyeon.msa.board.domain.QBoard.board;
import static jeonghyeon.msa.board.domain.QComment.comment;
import static jeonghyeon.msa.board.domain.QUsers.users;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryCustomImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<CommentResponse> findList(Long boardId, Long offset, Long limit) {

        List<Long> ids = jpaQueryFactory.select(comment.commentId)
                .from(comment)
                .where(comment.board.boardId.eq(boardId))
                .orderBy(board.boardId.desc())
                .offset(offset)
                .limit(limit)
                .fetch();

        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }

        List<CommentResponse> fetch = jpaQueryFactory
                .select(new QCommentResponse(comment.commentId, users.usersId, users.username, comment.content, comment.createdDate))
                .from(comment)
                .join(comment.users, users)
                .where(comment.commentId.in(ids))
                .orderBy(comment.commentId.desc())
                .fetch();
        return fetch;
    }
}
