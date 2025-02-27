package jeonghyeon.msa.board.repository.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ViewCountRepository {
    private final StringRedisTemplate redisTemplate;

    private static final String KEY_FORMAT = "board::board::%s::view_count";

    public Long read(Long boardId) {
        String result = redisTemplate.opsForValue().get(generateKey(boardId));
        return result == null ? 0L : Long.valueOf(result);
    }

    public Long increase(Long boardId) {
        return redisTemplate.opsForValue().increment(generateKey(boardId));
    }

    private String generateKey(Long articleId) {
        return KEY_FORMAT.formatted(articleId);
    }

    public void setIncreaseViewCount(Long boardId, Long count) {
        redisTemplate.opsForValue().set(generateKey(boardId), String.valueOf(count));
    }
}
