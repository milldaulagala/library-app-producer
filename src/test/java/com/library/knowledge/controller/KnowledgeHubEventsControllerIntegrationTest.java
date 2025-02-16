package com.library.knowledge.controller;

import com.library.knowledge.domain.KnowledgeHubEvent;
import com.library.knowledge.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class KnowledgeHubEventsControllerIntegrationTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void postKnowledgeHubEvent() {
        //given
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("content-type", MediaType.APPLICATION_JSON.toString());
        var httpEntity = new HttpEntity<>(TestUtil.knowledgeHubEventRecord(), httpHeaders);

        //when
        var responseEntity = restTemplate
                .exchange("/v1/knowledgehubevent", HttpMethod.POST,
                        httpEntity, KnowledgeHubEvent.class);


        //then
        assertEquals( HttpStatus.CREATED, responseEntity.getStatusCode());

    }
}
