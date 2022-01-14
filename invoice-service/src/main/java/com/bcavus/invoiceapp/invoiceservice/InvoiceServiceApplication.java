package com.bcavus.invoiceapp.invoiceservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class InvoiceServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(InvoiceServiceApplication.class, args);
    }
}