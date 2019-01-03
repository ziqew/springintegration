package com.sgi.springintegrationapp.service;

import com.sgi.springintegrationapp.entity.Person;
import org.springframework.stereotype.Component;

@Component
public class PersonService {
    public Person getPerson(long id) {
        return new Person();
    }
}
