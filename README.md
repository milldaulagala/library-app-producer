# library-knowledge-hub

### POST WITH-NULL-Knowledge-Hub-Event-ID

```
curl -i \
-d '{"knowledgeHubEventId":null,"knowledgeHubEventType": "NEW","book":{"bookId":4567,"bookName":"Share knowledge and win together","bookAuthor":"Mill"}}' \
-H "Content-Type: application/json" \
-X POST http://localhost:8080/v1/knowledgehubevent
```

![Screenshot from 2025-02-03 11-19-58.png](../../Pictures/Screenshots/Screenshot%20from%202025-02-03%2011-19-58.png)

### KnowledgeHubEvent

![Screenshot from 2025-02-03 11-25-15.png](../../Pictures/Screenshots/Screenshot%20from%202025-02-03%2011-25-15.png)

### List the topics in a cluster

```
docker exec --interactive --tty kafka1  \
kafka-topics --bootstrap-server kafka1:19092 --list
```

![Screenshot from 2025-02-03 15-43-16.png](../../Pictures/Screenshots/Screenshot%20from%202025-02-03%2015-43-16.png)

### Command to describe a specific Kafka topic

```
docker exec --interactive --tty kafka1  \
kafka-topics --bootstrap-server kafka1:19092 --describe \
--topic knowledge-hub-events
```

![Screenshot from 2025-02-03 15-49-33.png](../../Pictures/Screenshots/Screenshot%20from%202025-02-03%2015-49-33.png)
