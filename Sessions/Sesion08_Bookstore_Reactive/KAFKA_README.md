# Kafka Messaging System for Bookstore Service

## General Description

This project implements an asynchronous messaging system using Apache Kafka to handle book notifications. The system allows:

- **Receiving notifications** for new books, stock updates, and book modifications
- **Automatically processing** these notifications to create or update books in the database
- **Sending notifications** when CRUD operations are performed in the book service

## System Architecture

### Main Components

1. **BookNotificationListener** - Listens to Kafka messages
2. **BookNotificationService** - Processes business logic
3. **BookKafkaProducerService** - Sends messages to Kafka
4. **KafkaConfig** - Kafka configuration
5. **BookNotification** - DTO for messages

### Data Flow

```
[External Producer] → [Kafka Topic] → [BookNotificationListener] → [BookNotificationService] → [Database]
                                                                    ↓
[BookService] → [BookKafkaProducerService] → [Kafka Topic] → [External Consumers]
```

## Configuration

### Kafka Properties

```properties
# Kafka Configuration
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.consumer.group-id=book-service-group
spring.kafka.consumer.auto-offset-reset=earliest
spring.kafka.consumer.enable-auto-commit=false
spring.kafka.consumer.max-poll-records=500

# Kafka Topics
kafka.topic.book-notifications=book-notifications
kafka.topic.stock-updates=book-stock-updates
kafka.topic.book-events=book-events

# Kafka Producer Configuration
kafka.producer.acks=all
kafka.producer.retries=3
kafka.producer.batch-size=16384
kafka.producer.linger-ms=1
kafka.producer.buffer-memory=33554432
```

### Kafka Topics

1. **book-notifications** - General book notifications
2. **book-stock-updates** - Specific stock updates
3. **book-events** - General system events

## System Usage

### 1. Receiving Book Notifications

The system automatically listens for notifications on configured topics. Messages must have the following JSON format:

```json
{
  "isbn": "9780123456789",
  "title": "The Lord of the Rings",
  "author": "J.R.R. Tolkien",
  "description": "Epic fantasy novel",
  "price": 29.99,
  "stock": 50,
  "category": "FANTASY",
  "notification_type": "NEW_BOOK",
  "timestamp": "2025-07-23 01:37:00"
}
```

### 2. Notification Types

- **NEW_BOOK** - New book
- **STOCK_UPDATE** - Stock update
- **BOOK_UPDATE** - Complete book update

### 3. Sending Notifications

The system automatically sends notifications when:

- A new book is created
- An existing book is updated
- Book stock is modified

### 4. Test Endpoints

#### Get sample notification
```bash
GET /api/kafka/sample-notification
```

#### Send custom notification
```bash
POST /api/kafka/send-notification
Content-Type: application/json

{
  "isbn": "9780123456789",
  "title": "Test Book",
  "author": "Test Author",
  "description": "Test Description",
  "price": 29.99,
  "stock": 10,
  "category": "FICTION",
  "notification_type": "NEW_BOOK",
  "timestamp": "2025-07-23 01:37:00"
}
```

#### Send new book notification
```bash
POST /api/kafka/send-new-book?bookId=1
```

#### Send stock update notification
```bash
POST /api/kafka/send-stock-update?bookId=1
```

#### Send custom event
```bash
POST /api/kafka/send-event?bookId=1&eventType=BOOK_DELETED&additionalData=User requested deletion
```

## Processing Logic

### For New Book Notifications

1. Verifies if the book already exists by ISBN
2. If it exists, updates the stock
3. If it doesn't exist, creates the new book

### For Stock Updates

1. Searches for the book by ISBN
2. If it exists, updates the stock
3. If it doesn't exist, creates the book as new

### For Book Updates

1. Searches for the book by ISBN
2. If it exists, updates all fields
3. If it doesn't exist, creates the book as new

## Data Validation

The system automatically validates:

- ISBN not null and not empty
- Title not null and not empty
- Author not null and not empty
- Valid price (> 0)
- Valid stock (≥ 0)
- Category not null
- Notification type not null

## Error Handling

### Validation Errors
- Invalid messages are logged and ignored
- Messages with missing or incorrect data are not processed

### Database Errors
- Detailed errors are logged
- Automatic retry is implemented (3 attempts)
- Dead letter queue can be configured for failed messages

### Kafka Errors
- acks=all configuration to guarantee delivery
- Automatic retry in case of network failures
- Detailed error logging

## Development Configuration

### For local development with Kafka

1. **Install Kafka** (using Docker):
```bash
docker run -p 2181:2181 -p 9092:9092 --name kafka -e KAFKA_ADVERTISED_HOST_NAME=localhost -e KAFKA_ZOOKEEPER_CONNECT=localhost:2181 confluentinc/cp-kafka:latest
```

2. **Create topics**:
```bash
kafka-topics --create --topic book-notifications --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1
kafka-topics --create --topic book-stock-updates --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1
kafka-topics --create --topic book-events --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1
```

3. **Verify topics**:
```bash
kafka-topics --list --bootstrap-server localhost:9092
```

### For testing

The project includes configuration for tests with embedded Kafka:

```java
@EmbeddedKafka(partitions = 1, topics = {"book-notifications", "book-stock-updates", "book-events"})
@SpringBootTest
class KafkaIntegrationTest {
    // Tests here
}
```

## Monitoring and Logging

### Important Logs

- **INFO**: Successful notification processing
- **WARN**: Invalid data or books not found
- **ERROR**: Processing or database errors

### Recommended Metrics

- Number of messages processed per minute
- Average processing time
- Error rate
- Kafka latency

## Use Cases

### 1. Integration with External Systems

```java
// External system sends notification
BookNotification notification = new BookNotification(
    "9780123456789", "New Book", "Author", "Description",
    new BigDecimal("29.99"), 100, BookCategory.FICTION,
    BookNotification.NotificationType.NEW_BOOK, LocalDateTime.now()
);

// The system automatically:
// 1. Receives the notification
// 2. Validates the data
// 3. Creates or updates the book
// 4. Sends confirmation
```

### 2. Stock Synchronization

```java
// Inventory system sends update
BookNotification stockUpdate = new BookNotification(
    "9780123456789", null, null, null,
    null, 50, null,
    BookNotification.NotificationType.STOCK_UPDATE, LocalDateTime.now()
);

// The system automatically updates the stock
```

### 3. Audit Events

```java
// The system automatically sends events
kafkaProducerService.sendBookEvent("BOOK_CREATED", bookResponse, "User: admin");
kafkaProducerService.sendBookEvent("STOCK_UPDATED", bookResponse, "Previous stock: 10");
```

## Production Considerations

### Scalability

- Configure multiple partitions for parallelism
- Use multiple consumers in the same group
- Configure auto-scaling based on load

### Security

- Configure SASL/SSL for authentication
- Use ACLs for topic access control
- Implement encryption for sensitive messages

### Resilience

- Configure topic replication
- Implement circuit breakers
- Configure dead letter queues
- Monitor consumer lag

## Troubleshooting

### Common Issues

1. **Unprocessed messages**
   - Verify Kafka connectivity
   - Review error logs
   - Verify topic configuration

2. **Validation errors**
   - Review JSON message format
   - Verify required fields
   - Check data types

3. **Database errors**
   - Verify database connectivity
   - Review table schema
   - Check user permissions

### Diagnostic Commands

```bash
# View messages in topic
kafka-console-consumer --topic book-notifications --bootstrap-server localhost:9092 --from-beginning

# View consumer configuration
kafka-consumer-groups --bootstrap-server localhost:9092 --describe --group book-service-group

# View consumer lag
kafka-consumer-groups --bootstrap-server localhost:9092 --describe --group book-service-group
```

## Testing

### Unit Tests

The project includes comprehensive unit tests for the notification service:

```bash
mvn test -Dtest=BookNotificationServiceTest
```

### Integration Tests

For integration testing with Kafka:

```java
@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = {"book-notifications"})
class KafkaIntegrationTest {
    
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    
    @Test
    void shouldProcessBookNotification() {
        // Test implementation
    }
}
```

## Performance Tuning

### Producer Configuration

```properties
# Optimize for throughput
kafka.producer.batch-size=32768
kafka.producer.linger-ms=5
kafka.producer.compression-type=snappy

# Optimize for reliability
kafka.producer.acks=all
kafka.producer.retries=3
kafka.producer.enable-idempotence=true
```

### Consumer Configuration

```properties
# Optimize for throughput
kafka.consumer.max-poll-records=1000
kafka.consumer.fetch-min-size=1024
kafka.consumer.fetch-max-wait-ms=500

# Optimize for reliability
kafka.consumer.enable-auto-commit=false
kafka.consumer.auto-offset-reset=earliest
```

## Deployment

### Docker Configuration

```dockerfile
FROM openjdk:17-jdk-slim
COPY target/booksservice-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

### Kubernetes Configuration

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: bookstore-service
spec:
  replicas: 3
  selector:
    matchLabels:
      app: bookstore-service
  template:
    metadata:
      labels:
        app: bookstore-service
    spec:
      containers:
      - name: bookstore-service
        image: bookstore-service:latest
        ports:
        - containerPort: 8080
        env:
        - name: SPRING_KAFKA_BOOTSTRAP_SERVERS
          value: "kafka-cluster:9092"
```

## Security Best Practices

### Network Security

- Use VPN or private networks for Kafka communication
- Implement firewall rules to restrict access
- Use SSL/TLS encryption for all communications

### Authentication & Authorization

- Implement SASL authentication
- Use ACLs to control topic access
- Implement role-based access control

### Data Protection

- Encrypt sensitive data in messages
- Implement data retention policies
- Regular security audits and monitoring 