package com.library.knowledge.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.knowledge.domain.Book;
import com.library.knowledge.domain.KnowledgeHubEvent;
import com.library.knowledge.domain.KnowledgeHubEventType;

public class TestUtil {
    public static Book bookRecord(){

        return new Book(123, "Share knowledge and win together","Mill" );
    }

    public static Book bookRecordWithInvalidValues(){

        return new Book(null, "","Mill" );
    }

    public static KnowledgeHubEvent knowledgeHubEventRecord(){

        return
                new KnowledgeHubEvent(null,
                        KnowledgeHubEventType.NEW,
                        bookRecord());
    }

    public static KnowledgeHubEvent newKnowledgeHubEventRecordWithKnowledgeHubEventId(){

        return
                new KnowledgeHubEvent(123,
                        KnowledgeHubEventType.NEW,
                        bookRecord());
    }

    public static KnowledgeHubEvent knowledgeHubEventRecordUpdate(){

        return
                new KnowledgeHubEvent(123,
                        KnowledgeHubEventType.UPDATE,
                        bookRecord());
    }

    public static KnowledgeHubEvent knowledgeHubEventRecordUpdateWithNullKnowledgeHubEventId(){

        return
                new KnowledgeHubEvent(null,
                        KnowledgeHubEventType.UPDATE,
                        bookRecord());
    }

    public static KnowledgeHubEvent knowledgeHubEventRecordWithInvalidBook(){

        return
                new KnowledgeHubEvent(null,
                        KnowledgeHubEventType.NEW,
                        bookRecordWithInvalidValues());
    }

    public static KnowledgeHubEvent parseKnowledgeHubEventRecord(ObjectMapper objectMapper , String json){

        try {
            return  objectMapper.readValue(json, KnowledgeHubEvent.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

}
