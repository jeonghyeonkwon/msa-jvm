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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static jeonghyeon.msa.board.domain.QBoard.board;
import static jeonghyeon.msa.board.domain.QUsers.users;

@Repository
@RequiredArgsConstructor
public class CustomBoardRepositoryImpl implements CustomBoardRepository {
    @PersistenceContext
    private EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;

    public CustomBoardRepositoryImpl() {
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

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

        ;
        return null;
    }

    @Override
    public Long count(int limit) {
        StringBuilder sb = new StringBuilder();

        sb.append("SELECT COUNT(sub) ");
        sb.append("FROM ( ");
        sb.append("     SELECT b.boardId bId");
        sb.append("     FROM Board b ");
        sb.append("     ORDER BY b.boardId");
        sb.append("     LIMIT :limit ");
        sb.append(") sub");
        TypedQuery<Long> query = entityManager.createQuery(sb.toString(), Long.class);
        query.setParameter("limit", limit);
        Long singleResult = query.getSingleResult();
        return singleResult;
    }
}


