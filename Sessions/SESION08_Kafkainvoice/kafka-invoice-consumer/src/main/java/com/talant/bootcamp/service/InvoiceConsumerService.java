package com.talant.bootcamp.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.talant.bootcamp.model.Invoice;

@Service
public class InvoiceConsumerService {

    @KafkaListener(topics = "received-invoices", groupId = "invoice-consumer-group")
    public void listen(Invoice invoice) {
        System.out.println("📥 Received Invoice from Kafka:");
        System.out.println("   ➤ ID: " + invoice.getId());
        System.out.println("   ➤ Amount: " + invoice.getAmount());
        System.out.println("   ➤ Customer: " + invoice.getCustomer());
    }
}
