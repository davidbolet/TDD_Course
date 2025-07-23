package com.talant.bootcamp.booksservice.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.talant.bootcamp.booksservice.dto.BookNotification;
import com.talant.bootcamp.booksservice.service.BookNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * Kafka listener for book notifications
 */
@Component
public class BookNotificationListener {
    
    private static final Logger logger = LoggerFactory.getLogger(BookNotificationListener.class);
    
    private final BookNotificationService bookNotificationService;
    private final ObjectMapper objectMapper;
    
    @Autowired
    public BookNotificationListener(BookNotificationService bookNotificationService, ObjectMapper objectMapper) {
        this.bookNotificationService = bookNotificationService;
        this.objectMapper = objectMapper;
    }
    
    /**
     * Listen for book notifications on the book-notifications topic
     */
    @KafkaListener(
        topics = "${kafka.topic.book-notifications:book-notifications}",
        groupId = "${kafka.consumer.group-id:book-service-group}",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void listenForBookNotifications(
            @Payload String message,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
            @Header(KafkaHeaders.OFFSET) Long offset) {
        
        logger.info("Received message from topic: {}, partition: {}, offset: {}", topic, partition, offset);
        logger.debug("Message content: {}", message);
        
        try {
            // Parse the JSON message to BookNotification
            BookNotification notification = objectMapper.readValue(message, BookNotification.class);
            
            // Validate the notification
            if (!bookNotificationService.isValidNotification(notification)) {
                logger.error("Invalid book notification received: {}", notification);
                return;
            }
            
            // Process the notification
            bookNotificationService.processBookNotification(notification);
            
            logger.info("Successfully processed book notification for ISBN: {}", notification.getIsbn());
            
        } catch (JsonProcessingException e) {
            logger.error("Error parsing book notification JSON: {}", message, e);
        } catch (Exception e) {
            logger.error("Error processing book notification: {}", message, e);
            // In a real application, you might want to send to a dead letter queue
            // or implement retry logic here
        }
    }
    
    /**
     * Listen for book stock updates specifically
     */
    @KafkaListener(
        topics = "${kafka.topic.stock-updates:book-stock-updates}",
        groupId = "${kafka.consumer.group-id:book-service-group}",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void listenForStockUpdates(
            @Payload String message,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
            @Header(KafkaHeaders.OFFSET) Long offset) {
        
        logger.info("Received stock update from topic: {}, partition: {}, offset: {}", topic, partition, offset);
        logger.debug("Stock update message: {}", message);
        
        try {
            // Parse the JSON message to BookNotification
            BookNotification notification = objectMapper.readValue(message, BookNotification.class);
            
            // Validate the notification
            if (!bookNotificationService.isValidNotification(notification)) {
                logger.error("Invalid stock update notification received: {}", notification);
                return;
            }
            
            // Force notification type to STOCK_UPDATE
            notification.setNotificationType(BookNotification.NotificationType.STOCK_UPDATE);
            
            // Process the notification
            bookNotificationService.processBookNotification(notification);
            
            logger.info("Successfully processed stock update for ISBN: {}", notification.getIsbn());
            
        } catch (JsonProcessingException e) {
            logger.error("Error parsing stock update JSON: {}", message, e);
        } catch (Exception e) {
            logger.error("Error processing stock update: {}", message, e);
        }
    }
} 