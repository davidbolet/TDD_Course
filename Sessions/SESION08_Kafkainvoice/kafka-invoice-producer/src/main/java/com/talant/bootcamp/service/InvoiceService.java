package com.talant.bootcamp.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.talant.bootcamp.model.Invoice;

@Service
public class InvoiceService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public InvoiceService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendInvoice(Invoice invoice) {
        kafkaTemplate.send("received-invoices", invoice.getId(), invoice);
    }
}
