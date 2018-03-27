package com.reactive.endpointsexample.reactivemongo.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Employee {

    @Id
    private String id;
    private String name;
    private Long salary;
}
