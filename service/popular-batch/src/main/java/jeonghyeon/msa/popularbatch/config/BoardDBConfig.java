package jeonghyeon.msa.popularbatch.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;


@EnableJpaRepositories(
        basePackages = {"jeonghyeon.msa.popularbatch.repository.board"},
        entityManagerFactoryRef = "boardEntityManagerFactory",
        transactionManagerRef = "boardTransactionManager"
)
public class BoardDBConfig {
    @Bean(name = "boardDataSource")
    @ConfigurationProperties("spring.datasource-data-board")
    public DataSource boardDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "boardEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean boardEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("boardDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("jeonghyeon.msa.popularbatch.entity.board")
                .persistenceUnit("board")
                .build();
    }

    @Bean(name = "boardTransactionManager")
    public PlatformTransactionManager boardTransactionManager(
            @Qualifier("boardEntityManagerFactory") EntityManagerFactory entityManagerFactory
    ) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
