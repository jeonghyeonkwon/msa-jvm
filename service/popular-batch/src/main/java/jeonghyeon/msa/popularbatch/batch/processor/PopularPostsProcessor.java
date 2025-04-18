package jeonghyeon.msa.popularbatch.batch.processor;

import jeonghyeon.msa.popularbatch.dto.BoardDto;
import jeonghyeon.msa.popularbatch.entity.popularposts.Posts;
import jeonghyeon.msa.popularbatch.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PopularPostsProcessor implements ItemProcessor<BoardDto, Posts> {
    private final BoardRepository boardRepository;



    @Override
    public Posts process(BoardDto boardDtos) throws Exception {
        System.out.println(boardDtos);




        return new Posts(
                boardDtos.getBoardId(), boardDtos.getCommentCount(),
                boardDtos.getViewCount(), boardDtos.getLikeCount()
        );
    }
}
