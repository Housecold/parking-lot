package com.parkinglot.management.exceptions;

public class InvalidTicketException extends Exception {
    public InvalidTicketException(String message) {
        super(message);
    }
}