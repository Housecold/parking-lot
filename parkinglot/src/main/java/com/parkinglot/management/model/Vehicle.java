package com.parkinglot.management.model;

public class Vehicle {
    private String type;
    private String registration;
    private String color;

    public Vehicle(String type, String registration, String color) {
        this.type = type;
        this.registration = registration;
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public String getRegistration() {
        return registration;
    }

    public String getColor() {
        return color;
    }
}