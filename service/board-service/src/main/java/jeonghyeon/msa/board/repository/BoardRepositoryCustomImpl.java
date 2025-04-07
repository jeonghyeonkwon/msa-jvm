package jeonghyeon.msa.board.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jeonghyeon.msa.board.context.UserContext;
import jeonghyeon.msa.board.domain.QComment;
import jeonghyeon.msa.board.domain.Users;
import jeonghyeon.msa.board.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static jeonghyeon.msa.board.domain.QBoard.board;
import static jeonghyeon.msa.board.domain.QBoardLike.boardLike;
import static jeonghyeon.msa.board.domain.QComment.comment;
import static jeonghyeon.msa.board.domain.QUsers.users;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryCustomImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public BoardDetailResponse getBoardDetail(Long boardId) {

        boolean isLikedByUser = false;
        if (UserContext.isExistUser()) {
            Users currentUser = UserContext.getCurrentUser();
            BooleanExpression isLiked = boardLike.users.username.eq(currentUser.getUsername()).and(boardLike.board.boardId.eq(boardId));

            Long likeCount = jpaQueryFactory.select(boardLike.count())
                    .from(boardLike)
                    .where(isLiked)
                    .fetchOne();

            isLikedByUser = likeCount > 0;

        }

        BoardDetailResponse dto = jpaQueryFactory
                .select(new QBoardDetailResponse(board.boardId, users.username, board.title, board.content, board.createdDate))
                .from(board)
                .innerJoin(board.users, users)
                .where(board.boardId.eq(boardId))
                .fetchOne();

        dto.setLiked(isLikedByUser);

        return dto;
    }


    @Override
    public BoardDetailResponse getBoardDetailAndViewCount(Long boardId) {
        boolean isLikedByUser = false;
        if (UserContext.isExistUser()) {
            Users currentUser = UserContext.getCurrentUser();
            BooleanExpression isLiked = boardLike.users.username.eq(currentUser.getUsername()).and(boardLike.board.boardId.eq(boardId));

            Long likeCount = jpaQueryFactory.select(boardLike.count())
                    .from(boardLike)
                    .where(isLiked)
                    .fetchOne();

            isLikedByUser = likeCount > 0;

        }
        BoardDetailResponse dto = jpaQueryFactory
                .select(new QBoardDetailResponse(board.boardId, users.username, board.title, board.content, board.createdDate, board.viewCount))
                .from(board)
                .innerJoin(board.users, users)
                .where(board.boardId.eq(boardId))
                .fetchOne();

        dto.setLiked(isLikedByUser);
        return dto;
    }


    @Override
    public List<BoardResponse> findList(Long offset, Long limit) {

        List<Long> ids = jpaQueryFactory
                .select(board.boardId)
                .from(board)
                .orderBy(board.boardId.desc())
                .offset(offset)
                .limit(limit)
                .fetch();

        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }

        List<BoardResponse> fetch = jpaQueryFactory
                .select(
                        new QBoardResponse(
                                board.boardId,
                                board.title,
                                board.content,
                                users.username,
                                board.createdDate
                        )
                )
                .from(board)
                .innerJoin(board.users, users)
                .where(board.boardId.in(ids))
                .orderBy(board.boardId.desc())
                .fetch();
        return fetch;
    }

    @Override
    public BoardPopularPostsResponse getPopularPosts(Long boardId) {
        BoardPopularPostsResponse response = jpaQueryFactory
                .select(
                        new QBoardPopularPostsResponse(
                                board.boardId, board.title, comment.count(), board.viewCount, board.likeCount,
                                users.usersId, board.createdDate)
                )
                .from(board)
                .innerJoin(board.users, users)
                .leftJoin(board.comments, comment)
                .where(board.boardId.eq(boardId))
                .groupBy(comment.board.boardId)
                .fetchOne();
        return response;
    }
}
