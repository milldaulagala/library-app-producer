package com.library.knowledge.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.library.knowledge.domain.KnowledgeHubEvent;
import com.library.knowledge.domain.KnowledgeHubEventType;
import com.library.knowledge.producer.KnowledgeHubEventsProducer;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@RestController
@Slf4j
public class KnowledgeHubEventsController {

    private final KnowledgeHubEventsProducer knowledgeHubEventsProducer;

    public KnowledgeHubEventsController(KnowledgeHubEventsProducer knowledgeHubEventsProducer) {
        this.knowledgeHubEventsProducer = knowledgeHubEventsProducer;
    }

    @PostMapping("/v1/knowledgehubevent")
    public ResponseEntity<KnowledgeHubEvent> postKnowledgeHubEvent(
            @RequestBody @Valid KnowledgeHubEvent knowledgeHubEvent
    ) throws JsonProcessingException, ExecutionException, InterruptedException, TimeoutException {
        log.info("knowledgeHubEvent : {} ", knowledgeHubEvent);

        //invoke the kafka producer
        knowledgeHubEventsProducer.sendKnowledgeHubEvent(knowledgeHubEvent);

        return ResponseEntity.status(HttpStatus.CREATED).body(knowledgeHubEvent);
    }

    @PutMapping("/v1/knowledgehubevent")
    public ResponseEntity<?> updateKnowledgeHubEvent(
            @RequestBody @Valid KnowledgeHubEvent knowledgeHubEvent
    ) throws JsonProcessingException, ExecutionException, InterruptedException, TimeoutException {
        log.info("knowledgeHubEvent : {} ", knowledgeHubEvent);

        ResponseEntity<String> BAD_REQUEST = validateKnowledgeHubEvent(knowledgeHubEvent);
        if (BAD_REQUEST != null) return BAD_REQUEST;

        //invoke the kafka producer
        knowledgeHubEventsProducer.sendKnowledgeHubEvent(knowledgeHubEvent);

        return ResponseEntity.status(HttpStatus.OK).body(knowledgeHubEvent);
    }

    private static ResponseEntity<String> validateKnowledgeHubEvent(KnowledgeHubEvent knowledgeHubEvent) {

        if (knowledgeHubEvent.knowledgeHubEventId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please pass the KnowledgeHubEventId" + "\n");
        }

        if (!knowledgeHubEvent.knowledgeHubEventType().equals(KnowledgeHubEventType.UPDATE)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Only UPDATE event type is supported" + "\n");
        }
        return null;
    }

}
