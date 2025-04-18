package jeonghyeon.msa.popularbatch.batch;

import jakarta.persistence.EntityManagerFactory;
import jeonghyeon.msa.popularbatch.batch.processor.PopularPostsProcessor;
import jeonghyeon.msa.popularbatch.batch.reader.BoardReader;
import jeonghyeon.msa.popularbatch.batch.writer.PopularPostsWriter;
import jeonghyeon.msa.popularbatch.dto.BoardDto;
import jeonghyeon.msa.popularbatch.entity.popularposts.Posts;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class PopularBatch {
    private final JobRepository jobRepository;


    private final BoardReader boardReader;
    private final PopularPostsProcessor popularPostsProcessor;
    private final PopularPostsWriter popularPostsWriter;

    private final PlatformTransactionManager platformTransactionManager;


    private final EntityManagerFactory entityManagerFactory;


    @Bean
    public Job processPopularJob() {
        return new JobBuilder("processPopularJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(popularPostsStep())
                .end().build();
    }

    @Bean
    public Step popularPostsStep() {
        return new StepBuilder("popularPostsStep", jobRepository)
                .<BoardDto, Posts>chunk(10, platformTransactionManager)
                .reader(boardReader.reader(entityManagerFactory))
                .processor(popularPostsProcessor)
                .writer(popularPostsWriter)
                .build();
    }

//
//    @Bean
//    public JpaPagingItemReader<BoardDto> boardReader() {
//
//        StringBuilder builder = new StringBuilder();
//        builder.append("SELECT new jeonghyeon.msa.popularbatch.dto.BoardDto(b.boardId, COUNT(c.board), b.viewCount, b.likeCount)");
//        builder.append("   FROM Board b");
//        builder.append("   LEFT JOIN b.comments c");
//        builder.append("   WHERE b.popularCheck = false");
//        builder.append("   GROUP BY c.board, b");
//
//
//        JpaPagingItemReader<BoardDto> reader = new JpaPagingItemReader<>();
//        reader.setEntityManagerFactory(entityManagerFactory);
//        reader.setQueryString(builder.toString());
//        reader.setPageSize(10);
//
//        return reader;
//    }

}
