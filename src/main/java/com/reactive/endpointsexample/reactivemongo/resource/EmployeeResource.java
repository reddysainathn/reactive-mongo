package com.reactive.endpointsexample.reactivemongo.resource;

import com.reactive.endpointsexample.reactivemongo.model.Employee;
import com.reactive.endpointsexample.reactivemongo.model.EmployeeEvent;
import com.reactive.endpointsexample.reactivemongo.repository.EmployeeRepo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Date;
import java.util.stream.Stream;

@RestController
@RequestMapping("/rest/employee")
public class EmployeeResource {
    private EmployeeRepo employeeRepository;

    public EmployeeResource(EmployeeRepo employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    //Mono ->Will return one entry from Reactive Stream.
    //Flux ->Will return Multiple entries from Reactive Stream.

    @GetMapping("/all")
    public Flux<Employee> getAll() {
        Flux<Employee> employees = employeeRepository.findAll();
        return employees;
    }

    @GetMapping("/{id}")
    public Mono<Employee> getAll(@PathVariable final String id) {
        Mono<Employee> employee = employeeRepository.findById(id);
        return employee;
    }
    // Using Flux End-point(Flux Returns Multiple Stream of Events)
    @GetMapping(value = "/{id}/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<EmployeeEvent> getEvents(@PathVariable final String id) {
        //FlatMapMany as it returns multiple Streams.
        return employeeRepository.findById(id).flatMapMany(
                employee -> {
                    //Create Flux-1 interval for the time of interval the Stream Should generate
                    Flux<Long> interval = Flux.interval(Duration.ofSeconds(3));
                    //Create Flux-2 employeeEventFlux to get the Employee Events Stream.
                    Flux<EmployeeEvent> employeeEventFlux = Flux.fromStream(
                            Stream.generate(() -> new EmployeeEvent(employee, new Date()))
                    );
                    //Combine Flux-1 and Flux-2 with zip option.
                    // EmployeeEvent generates Tuple of Objects which we chose to getT2() as it is the EmployeeEvent.
                    return Flux.zip(interval, employeeEventFlux).
                            map(objects -> objects.getT2());
                }
        );
    }

}
