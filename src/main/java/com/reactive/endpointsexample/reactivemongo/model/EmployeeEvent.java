package com.reactive.endpointsexample.reactivemongo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeEvent {

    private Employee employee;
    private Date date;
}
