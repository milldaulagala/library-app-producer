package com.library.knowledge.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.knowledge.domain.KnowledgeHubEvent;
import com.library.knowledge.producer.KnowledgeHubEventsProducer;
import com.library.knowledge.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.isA;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(KnowledgeHubEventsController.class)
public class KnowledgeHubEventsControllerUnitTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    KnowledgeHubEventsProducer knowledgeHubEventsProducer;

    @Test
    void postKnowledgeHubEvent() throws Exception {
        //given
        var json = objectMapper.writeValueAsString(TestUtil.knowledgeHubEventRecord());
        when(knowledgeHubEventsProducer.sendKnowledgeHubEvent(isA(KnowledgeHubEvent.class)))
                .thenReturn(null);


        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/knowledgehubevent")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());


    }

    @Test
    void postKnowledgeHubEventWithInvalidValues() throws Exception {
        //given
        var json = objectMapper.writeValueAsString(TestUtil.knowledgeHubEventRecordWithInvalidBook());
        when(knowledgeHubEventsProducer.sendKnowledgeHubEvent(isA(KnowledgeHubEvent.class)))
                .thenReturn(null);

        var expectedErrorMessage = "book.bookId - must not be null, book.bookName - must not be blank";

        //when
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/knowledgehubevent")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(expectedErrorMessage));

    }

}
