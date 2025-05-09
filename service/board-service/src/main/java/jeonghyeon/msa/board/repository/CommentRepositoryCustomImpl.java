package jeonghyeon.msa.board.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jeonghyeon.msa.board.dto.response.CommentResponse;
import jeonghyeon.msa.board.dto.response.QCommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
                .where(comment.board.boardId.eq(boardId).and(comment.parent.isNull()))
                .orderBy(comment.commentId.desc())
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

    @Override
    public Map<Long, Long> findCountByBoardIds(List<Long> boardIds) {

        List<Tuple> fetch = jpaQueryFactory.select(comment.board.boardId, comment.board.boardId.count())
                .from(comment)
                .where(comment.board.boardId.in(boardIds))
                .groupBy(comment.board.boardId)
                .fetch();
        return fetch.stream().collect(Collectors.toMap(tuple -> tuple.get(0, Long.class), tuple -> tuple.get(1, Long.class)));
    }

    @Override
    public List<CommentResponse> findRepliesByParentIds(List<Long> parentsId) {
        return jpaQueryFactory.select(new QCommentResponse(comment.commentId, comment.parent.commentId, users.usersId, users.username, comment.content, comment.createdDate))
                .from(comment)
                .innerJoin(comment.users, users)
                .where(comment.parent.commentId.in(parentsId))
                .orderBy(comment.commentId.asc()).fetch();
    }


}
