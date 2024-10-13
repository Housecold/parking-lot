package com.parkinglot.management.exceptions;

public class VehicleTypeNotAllowedException extends Exception {
    public VehicleTypeNotAllowedException(String message) {
        super(message);
    }
}