package jeonghyeon.msa.popularbatch.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@EnableJpaRepositories(
        basePackages = {"jeonghyeon.msa.popularbatch.repository.popularposts"},
        entityManagerFactoryRef = "popularEntityManagerFactory",
        transactionManagerRef = "popularTransactionManager"

)
@Configuration
public class PopularDBConfig {

    // ==== popular-posts-service DB config
    @Bean(name = "popularPostsDataSource")
    @ConfigurationProperties("spring.datasource-data-popularposts")
    public DataSource popularPostsDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "popularEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean popularEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("popularPostsDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("jeonghyeon.msa.popularbatch.entity.popularposts")
                .persistenceUnit("popular")
                .build();
    }

    @Bean(name = "popularTransactionManager")
    public PlatformTransactionManager popularTransactionManager(
            @Qualifier("popularEntityManagerFactory") EntityManagerFactory entityManagerFactory
    ) {
        return new JpaTransactionManager(entityManagerFactory);
    }


}
