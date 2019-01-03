package com.sgi.springintegrationapp.si;

import com.sgi.springintegrationapp.entity.Person;
import com.sgi.springintegrationapp.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class PersonEndpoint {

    private static final String STATUSCODE_HEADER = "http_statusCode";

    @Autowired
    private PersonService service;

    public Message<?> get(Message<String> msg) {
        long id = Long.valueOf(msg.getPayload());
        Person person = service.getPerson(id);

        if (person == null) {
            return MessageBuilder.fromMessage(msg)
                    .copyHeadersIfAbsent(msg.getHeaders())
                    .setHeader(STATUSCODE_HEADER, HttpStatus.NOT_FOUND)
                    .build();
        }

        return MessageBuilder.withPayload(person)
                .copyHeadersIfAbsent(msg.getHeaders())
                .setHeader(STATUSCODE_HEADER, HttpStatus.OK)
                .build();
    }
}
