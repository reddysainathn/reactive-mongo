package com.reactive.endpointsexample.reactivemongo;

import com.mongodb.annotations.Beta;
import com.reactive.endpointsexample.reactivemongo.model.Employee;
import com.reactive.endpointsexample.reactivemongo.repository.EmployeeRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class ReactiveMongoApplication {

    @Bean
    CommandLineRunner addEmployees(EmployeeRepo employeeRepo) {
        return args -> {
            employeeRepo.deleteAll()
                    .subscribe(null, null, () -> {

                        Stream.of(new Employee(UUID.randomUUID().toString(),
                                "Peter", 23000L), new Employee(UUID.randomUUID().toString(),
                                "Sam", 13000L), new Employee(UUID.randomUUID().toString(),
                                "Ryan", 20000L), new Employee(UUID.randomUUID().toString(),
                                "Chris", 53000L)
                        ).forEach(
                                employee -> {
                                    employeeRepo.save(employee)
                                            .subscribe(System.out::println);
                                }
                        );
                    });
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(ReactiveMongoApplication.class, args);
    }
}
