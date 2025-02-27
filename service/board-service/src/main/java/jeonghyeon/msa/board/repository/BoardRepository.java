package jeonghyeon.msa.board.repository;

import jeonghyeon.msa.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>, CustomBoardRepository {

    @Query("UPDATE Board b SET b.viewCount = :count WHERE b.boardId = :boardId")
    @Modifying
    int updateViewCount(@Param("boardId") Long boardId, @Param("count") Long count);
}
