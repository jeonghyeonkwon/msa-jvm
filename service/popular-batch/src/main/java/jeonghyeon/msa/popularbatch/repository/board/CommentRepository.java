package jeonghyeon.msa.popularbatch.repository.board;

import jeonghyeon.msa.popularbatch.entity.board.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
