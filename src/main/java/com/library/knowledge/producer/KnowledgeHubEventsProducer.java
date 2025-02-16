package com.library.knowledge.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.knowledge.domain.KnowledgeHubEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class KnowledgeHubEventsProducer {

    @Value("${spring.kafka.topic}")
    public String topic;

    private final KafkaTemplate<Integer, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public KnowledgeHubEventsProducer(KafkaTemplate<Integer, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public CompletableFuture<SendResult<Integer, String>> sendKnowledgeHubEvent(KnowledgeHubEvent knowledgeHubEvent)
            throws JsonProcessingException {

        var key = knowledgeHubEvent.knowledgeHubEventId();
        var value = objectMapper.writeValueAsString(knowledgeHubEvent);

        var producerRecord = buildProducerRecord(key,value);

        // 1. blocking call - get metadata about the kafka cluster
        // 2. Send message happens - Returns a CompletableFuture
        var completableFuture = kafkaTemplate.send(producerRecord);

        return completableFuture
                .whenComplete((sendResult, throwable) -> {
                    if(throwable!=null){
                        handleFailure(key, value, throwable);
                    }else{
                        handleSuccess(key, value, sendResult);
                    }
                });

    }

    private ProducerRecord<Integer, String> buildProducerRecord(Integer key, String value) {

        List<Header> recordHeaders = List.of(new RecordHeader("event-source","scanner".getBytes()));

        return new ProducerRecord<>(topic,null, key, value, recordHeaders);
    }

    private void handleSuccess(Integer key, String value, SendResult<Integer, String> sendResult) {
        log.info("Message Sent Successfully for the key : {} and the value : {} , partition is {} ",
                key, value, sendResult.getRecordMetadata().partition());
    }

    private void handleFailure(Integer key, String value, Throwable ex) {
        log.error("Error sending the message and the exception is {} ", ex.getMessage(), ex );
    }
}
