package jeonghyeon.msa.msagateway.config;


import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-service", p -> p.path("/api/auth/**","/login").uri("http://127.0.0.1:8081"))
                .route("memo-service", p -> p.path("\\/.*memo.*$").uri("http://127.0.0.1:8082"))
                .route("board-service", p -> p.path("\\/.*board.*$").uri("http://127.0.0.1:8083"))
                .build();
    }
}
