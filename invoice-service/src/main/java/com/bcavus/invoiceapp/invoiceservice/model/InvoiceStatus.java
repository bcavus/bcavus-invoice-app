package com.bcavus.invoiceapp.invoiceservice.model;

import java.util.Arrays;

public enum InvoiceStatus {

    ACCEPTED("Accepted"), REJECTED("Rejected"), CREATED("Created");

    private final String value;

    private InvoiceStatus(String value) {
        this.value = value;
    }

    public static InvoiceStatus fromValue (String value) {

        for (InvoiceStatus invoiceStatus : values()) {
            if (invoiceStatus.value.equalsIgnoreCase(value)) {
                return invoiceStatus;
            }
        }

        throw new IllegalArgumentException("Unknown enum type " + value + ", Allowed values are " + Arrays.toString(values()));
    }
}