package com.parkinglot.management.controller;

import com.parkinglot.management.model.ParkingLot;
import com.parkinglot.management.exceptions.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/parking-lot")
public class ParkingLotController {

    private ParkingLot parkingLot = new ParkingLot("PR1234", 4, 6); // Example configuration

    @GetMapping("/open-slots/{type}")
    public int getOpenSlots(@PathVariable String type) {
        return parkingLot.getNoOfOpenSlots(type);
    }

    @PostMapping("/park")
    public String parkVehicle(
            @RequestParam String type,
            @RequestParam String registration,
            @RequestParam String color) throws SlotNotAvailableException, VehicleTypeNotAllowedException {
        return parkingLot.parkVehicle(type, registration, color);
    }

    @PostMapping("/unpark/{ticketId}")
    public String unParkVehicle(@PathVariable String ticketId) throws InvalidTicketException {
        parkingLot.unPark(ticketId);
        return "Vehicle with ticket ID " + ticketId + " has been unparked.";
    }

    @GetMapping("/occupied-slots/{type}")
    public void getOccupiedSlots(@PathVariable String type) {
        parkingLot.displayOccupiedSlots(type);
    }
}