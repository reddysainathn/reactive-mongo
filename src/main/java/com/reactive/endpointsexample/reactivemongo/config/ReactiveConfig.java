package com.reactive.endpointsexample.reactivemongo.config;

import com.reactive.endpointsexample.reactivemongo.RouterHandlers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

@Configuration
public class ReactiveConfig {

    @Bean
    public RouterFunction<?> routerFunction(RouterHandlers routerHandlers) {
        return RouterFunctions
                .route(
                        RequestPredicates.GET("/rest/router/employees/all"),
                        //routerHandlers::getAll
                        serverRequest -> {
                            return routerHandlers.getAll(serverRequest);
                        }
                )
                .andRoute(
                        RequestPredicates.GET("/rest/router/employees/{id}"),
                        serverRequest -> {
                            return routerHandlers.getEmployeeByID(serverRequest);
                        }
                ).andRoute(
                        RequestPredicates.GET("/rest/router/employees/{id}/events"), routerHandlers::getEmployeeEvents
                );
    }
}
