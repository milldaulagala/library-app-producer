package com.library.knowledge.domain;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record KnowledgeHubEvent(
        Integer knowledgeHubEventId,
        KnowledgeHubEventType knowledgeHubEventType,
        @NotNull
        @Valid
        Book book
) {
}
