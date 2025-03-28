package jeonghyeon.msa.board.repository;

import jeonghyeon.msa.board.domain.BoardLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {

    @Query("SELECT l FROM BoardLike l WHERE l.users.id = :usersId AND l.board.id = :boardId ")
    BoardLike findByBoardIdAndUsersId(@Param("boardId") Long boardId, @Param("usersId") Long usersId);
}
