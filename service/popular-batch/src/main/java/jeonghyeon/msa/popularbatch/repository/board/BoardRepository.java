package jeonghyeon.msa.popularbatch.repository.board;

import jeonghyeon.msa.popularbatch.dto.BoardDto;
import jeonghyeon.msa.popularbatch.entity.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("SELECT new jeonghyeon.msa.popularbatch.dto.BoardDto(b.boardId, COUNT(c.board), b.viewCount, b.likeCount)" +
            "   FROM Board b" +
            "   LEFT JOIN b.comments c" +
            "   WHERE b.popularCheck = false" +
            "   GROUP BY c.board, b"
    )
    List<BoardDto> getBoardList();

    @Query("UPDATE Board b SET b.popularCheck = true WHERE b.boardId IN (:boardIds)")
    @Modifying
    void updatePopularCheck(@Param("boardIds") List<Long> boardIds);
}
