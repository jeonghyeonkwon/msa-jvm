package jeonghyeon.msa.popularbatch.batch.reader;

import jakarta.persistence.EntityManagerFactory;
import jeonghyeon.msa.popularbatch.dto.BoardDto;
import jeonghyeon.msa.popularbatch.repository.board.BoardRepository;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BoardReader {
    @Autowired
    private  BoardRepository boardRepository;


    public JpaPagingItemReader<BoardDto> reader(
             EntityManagerFactory entityManagerFactory
            ) {

        System.out.println(entityManagerFactory);
            StringBuilder builder = new StringBuilder();
            builder.append("SELECT new jeonghyeon.msa.popularbatch.dto.BoardDto(b.boardId, COUNT(c.board), b.viewCount, b.likeCount)");
            builder.append("   FROM Board b");
            builder.append("   LEFT JOIN b.comments c");
            builder.append("   WHERE b.popularCheck = false");
            builder.append("   GROUP BY c.board, b");


            JpaPagingItemReader<BoardDto> reader = new JpaPagingItemReader<>();
            reader.setEntityManagerFactory(entityManagerFactory);
            reader.setQueryString(builder.toString());
            reader.setPageSize(10);

            return reader;
    }


//    @Override
//    @Transactional("boardTransactionManager")
//    public JpaPagingItemReader<BoardDto> read() throws Exception {
//
//            StringBuilder builder = new StringBuilder();
//            builder.append("SELECT new jeonghyeon.msa.popularbatch.dto.BoardDto(b.boardId, COUNT(c.board), b.viewCount, b.likeCount)");
//            builder.append("   FROM Board b");
//            builder.append("   LEFT JOIN b.comments c");
//            builder.append("   WHERE b.popularCheck = false");
//            builder.append("   GROUP BY c.board, b");
//
//
//            JpaPagingItemReader<BoardDto> reader = new JpaPagingItemReader<>();
//
//            reader.setQueryString(builder.toString());
//            reader.setPageSize(10);
//
//            return reader;
//
//    }

}
