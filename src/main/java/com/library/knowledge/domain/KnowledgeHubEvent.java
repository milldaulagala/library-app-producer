package com.library.knowledge.domain;

public record KnowledgeHubEvent(
        Integer knowledgeHubEventId,
        KnowledgeHubEventType knowledgeHubEventType,
        Book book
) {
}
