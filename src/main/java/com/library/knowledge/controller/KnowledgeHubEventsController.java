package com.library.knowledge.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.library.knowledge.domain.KnowledgeHubEvent;
import com.library.knowledge.producer.KnowledgeHubEventsProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class KnowledgeHubEventsController {

    private final KnowledgeHubEventsProducer knowledgeHubEventsProducer;

    public KnowledgeHubEventsController(KnowledgeHubEventsProducer knowledgeHubEventsProducer) {
        this.knowledgeHubEventsProducer = knowledgeHubEventsProducer;
    }

    @PostMapping("/v1/knowledgehubevent")
    public ResponseEntity<KnowledgeHubEvent> postKnowledgeHubEvent(
            @RequestBody KnowledgeHubEvent knowledgeHubEvent
    ) throws JsonProcessingException {
        log.info("knowledgeHubEvent : {} ", knowledgeHubEvent);

        //invoke the kafka producer
        knowledgeHubEventsProducer.sendKnowledgeHubEvent(knowledgeHubEvent);

        return ResponseEntity.status(HttpStatus.CREATED).body(knowledgeHubEvent);
    }
}
