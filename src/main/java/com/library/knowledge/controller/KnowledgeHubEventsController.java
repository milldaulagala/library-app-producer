package com.library.knowledge.controller;

import com.library.knowledge.domain.KnowledgeHubEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class KnowledgeHubEventsController {

    @PostMapping("/v1/knowledgehubevent")
    public ResponseEntity<KnowledgeHubEvent> postKnowledgeHubEvent(
            @RequestBody KnowledgeHubEvent knowledgeHubEvent
    ){
        log.info("knowledgeHubEvent : {} ", knowledgeHubEvent);
        //invoke the kafka producer

        return ResponseEntity.status(HttpStatus.CREATED).body(knowledgeHubEvent);
    }
}
