package jeonghyeon.msa.board.repository;

import jeonghyeon.msa.board.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {

    @Query(
            value = "SELECT COUNT(*) FROM (" +
                    "   SELECT comment_id FROM comment where comment.board_id = :boardId LIMIT :limit" +
                    ") T",
            nativeQuery = true)
    Long count(@Param("boardId") Long boardId, @Param("limit") Long limit);
}
