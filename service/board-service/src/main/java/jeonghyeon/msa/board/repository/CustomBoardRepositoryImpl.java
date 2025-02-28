package jeonghyeon.msa.board.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jeonghyeon.msa.board.domain.Board;
import jeonghyeon.msa.board.dto.response.BoardDetailResponse;
import jeonghyeon.msa.board.dto.response.BoardResponse;
import jeonghyeon.msa.board.dto.response.QBoardDetailResponse;
import jeonghyeon.msa.board.dto.response.QBoardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static jeonghyeon.msa.board.domain.QBoard.board;
import static jeonghyeon.msa.board.domain.QUsers.users;

@Repository
@RequiredArgsConstructor
public class CustomBoardRepositoryImpl implements CustomBoardRepository {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public BoardDetailResponse getBoardDetail(Long boardId) {


        return jpaQueryFactory
                .select(new QBoardDetailResponse(board.boardId, users.username, board.title, board.content))
                .from(board)
                .innerJoin(board.users, users).fetchJoin()
                .where(board.boardId.eq(boardId))
                .fetchOne();
    }


    @Override
    public BoardDetailResponse getBoardDetailAndViewCount(Long boardId) {
        return jpaQueryFactory
                .select(new QBoardDetailResponse(board.boardId, users.username, board.title, board.content, board.viewCount))
                .from(board)
                .innerJoin(board.users, users).fetchJoin()
                .where(board.boardId.eq(boardId))
                .fetchOne();
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
                .select(new QBoardResponse(board.boardId, board.title, board.content, users.username, board.createdDate))
                .from(board)
                .join(board.users,users)
                .where(board.boardId.in(ids))
                .orderBy(board.boardId.desc())
                .fetch();
        return fetch;
    }

}


