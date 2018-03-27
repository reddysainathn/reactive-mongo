package com.reactive.endpointsexample.reactivemongo.repository;

import com.reactive.endpointsexample.reactivemongo.model.Employee;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends ReactiveMongoRepository<Employee, String> {
}
