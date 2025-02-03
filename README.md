# library-knowledge-hub

### POST WITH-NULL-Knowledge-Hub-Event-ID

```
curl -i \
-d '{"knowledgeHubEventId":null,"knowledgeHubEventType": "NEW","book":{"bookId":4567,"bookName":"Share knowledge and win together","bookAuthor":"Mill"}}' \
-H "Content-Type: application/json" \
-X POST http://localhost:8080/v1/knowledgehubevent
```

![Screenshot_1](src/main/resources/screenshots/Screenshot_1.png)

### KnowledgeHubEvent

![Screenshot_2](src/main/resources/screenshots/Screenshot_2.png)

### List the topics in a cluster

```
docker exec --interactive --tty kafka1  \
kafka-topics --bootstrap-server kafka1:19092 --list
```

![Screenshot_3](src/main/resources/screenshots/Screenshot_3.png)

### Command to describe a specific Kafka topic

```
docker exec --interactive --tty kafka1  \
kafka-topics --bootstrap-server kafka1:19092 --describe \
--topic knowledge-hub-events
```

![Screenshot_4](src/main/resources/screenshots/Screenshot_4.png)
