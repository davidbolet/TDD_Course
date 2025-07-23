package com.talant.bootcamp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.talant.bootcamp.model.Invoice;
import com.talant.bootcamp.service.InvoiceService;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping
    public ResponseEntity<String> createInvoice(@RequestBody Invoice invoice) {
        invoiceService.sendInvoice(invoice);
        return ResponseEntity.status(HttpStatus.CREATED).body("Invoice sent to Kafka");
    }
}
