package com.reactive.endpointsexample.reactivemongo;

import com.reactive.endpointsexample.reactivemongo.model.Employee;
import com.reactive.endpointsexample.reactivemongo.model.EmployeeEvent;
import com.reactive.endpointsexample.reactivemongo.repository.EmployeeRepo;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Date;
import java.util.stream.Stream;

@Component
public class RouterHandlers {

    private EmployeeRepo employeeRepo;

    public RouterHandlers(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    public Mono<ServerResponse> getAll(ServerRequest serverRequest) {
        return ServerResponse
                .ok()
                .body(
                        employeeRepo.findAll(), Employee.class
                );
    }

    public Mono<ServerResponse> getEmployeeByID(ServerRequest serverRequest) {
        return ServerResponse
                .ok()
                .body(
                        employeeRepo.findById(serverRequest.pathVariable("id")), Employee.class
                );
    }

    public Mono<ServerResponse> getEmployeeEvents(ServerRequest serverRequest) {
        return ServerResponse
                .ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(
                        employeeRepo.findById(serverRequest.pathVariable("id")).flatMapMany(
                                employee -> {
                                    Flux<Long> interval = Flux.interval(Duration.ofSeconds(3));
                                    Flux<EmployeeEvent> employeeEventFlux =
                                            Flux.fromStream(
                                                    Stream.generate(() ->
                                                            new EmployeeEvent(employee, new Date())
                                                    )
                                            );
                                    return Flux.zip(interval, employeeEventFlux).map(
                                            objects -> objects.getT2());
                                }), EmployeeEvent.class
                );
    }
}
