package jeonghyeon.msa.popularbatch.repository.board;

import jeonghyeon.msa.popularbatch.entity.board.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
}
