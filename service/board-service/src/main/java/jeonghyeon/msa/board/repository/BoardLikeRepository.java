package jeonghyeon.msa.board.repository;

import jeonghyeon.msa.board.domain.BoardLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {

    @Query("DELETE from BoardLike l WHERE l.users.id = :usersId AND l.board.id = :boardId ")
    @Modifying
    void deleteByBoardIdAndUsersId(@Param("boardId") Long boardId, @Param("usersId") Long usersId);
}
