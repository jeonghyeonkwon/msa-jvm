package jeonghyeon.msa.popularbatch.batch.writer;

import jeonghyeon.msa.popularbatch.entity.popularposts.Posts;
import jeonghyeon.msa.popularbatch.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PopularPostsWriter implements ItemWriter<Posts> {
    private final BoardRepository boardRepository;


    @Override
    @Transactional("boardTransactionManager")
    public void write(Chunk<? extends Posts> chunk) throws Exception {
        List<? extends Posts> items = chunk.getItems();

        System.out.println(items);
        List<Long> collect = items.stream().map(item -> item.getId()).collect(Collectors.toList());
        boardRepository.updatePopularCheck(collect);


    }
}
