package jeonghyeon.msa.popularbatch.repository.popularposts;

import jeonghyeon.msa.popularbatch.entity.popularposts.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostsRepository extends JpaRepository<Posts,Long> {
}
