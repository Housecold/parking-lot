package com.parkinglot.management.model;

import java.util.ArrayList;
import java.util.List;

import com.parkinglot.management.exceptions.InvalidTicketException;
import com.parkinglot.management.exceptions.SlotNotAvailableException;
import com.parkinglot.management.exceptions.VehicleTypeNotAllowedException;

public class ParkingLot {
    String parkingLotId;
    List<List<Slot>> slots;

    public ParkingLot(String parkingLotId, int nfloors, int noOfSlotsPerFlr) {
        this.parkingLotId = parkingLotId;

        slots = new ArrayList<>();
        for (int i = 0; i < nfloors; i++) {
            slots.add(new ArrayList<>());
            List<Slot> floorSlots = slots.get(i);
            floorSlots.add(new Slot("truck"));
            floorSlots.add(new Slot("bike"));
            floorSlots.add(new Slot("bike"));

            for (int j = 3; j < noOfSlotsPerFlr; j++) {
                slots.get(i).add(new Slot("car"));
            }
        }
    }

    public String parkVehicle(String type, String regNo, String color)
            throws SlotNotAvailableException, VehicleTypeNotAllowedException {
        if (!isVehicleTypeAllowed(type)) {
            throw new VehicleTypeNotAllowedException("Vehicle type " + type + " is not allowed.");
        }

        Vehicle vehicle = new Vehicle(type, regNo, color);

        for (int i = 0; i < slots.size(); i++) {
            for (int j = 0; j < slots.get(i).size(); j++) {
                Slot slot = slots.get(i).get(j);
                if (slot.getType().equals(type) && slot.getVehicle() == null) {
                    slot.setVehicle(vehicle);
                    String ticketId = generateTicketId(i + 1, j + 1);
                    slot.setTicketId(ticketId);
                    return ticketId;
                }
            }
        }
        throw new SlotNotAvailableException("No available slots for " + type);
    }

    public void unPark(String ticketId) throws InvalidTicketException {
        String[] extract = ticketId.split("_");
        int flr_idx = Integer.parseInt(extract[1]) - 1;
        int slot_idx = Integer.parseInt(extract[2]) - 1;

        if (flr_idx < 0 || flr_idx >= slots.size() || slot_idx < 0 || slot_idx >= slots.get(flr_idx).size()) {
            throw new InvalidTicketException("Invalid ticket format or out-of-bounds indices.");
        }

        Slot slot = slots.get(flr_idx).get(slot_idx);

        if (ticketId.equals(slot.getTicketId())) {
            slot.setVehicle(null);
            slot.setTicketId(null);
            System.out.println("Unparked vehicle with ticket " + ticketId);
        } else {
            boolean found = false;

            for (int i = 0; i < slots.size(); i++) {
                for (int j = 0; j < slots.get(i).size(); j++) {
                    slot = slots.get(i).get(j);
                    if (ticketId.equals(slot.getTicketId())) {
                        slot.setVehicle(null);
                        slot.setTicketId(null);
                        System.out.println("Unparked vehicle with ticket " + ticketId);
                        found = true;
                        break;
                    }
                }
                if (found) {
                    break;
                }
            }

            if (!found) {
                throw new InvalidTicketException("Invalid ticket ID: " + ticketId);
            }
        }
    }

    public int getNoOfOpenSlots(String type) {
        int count = 0;
        for (List<Slot> floor : slots) {
            for (Slot slot : floor) {
                if (slot.getVehicle() == null && slot.getType().equals(type)) {
                    count++;
                }
            }
        }

        System.out.println("Number of open slots for " + type + ": " + count);
        return count;

    }

    public void displayOpenSlots(String type) {
        System.out.println("Available slots for " + type + ":");

        for (int i = 0; i < slots.size(); i++) {
            for (int j = 0; j < slots.get(i).size(); j++) {
                Slot slot = slots.get(i).get(j);
                if (slot.getVehicle() == null && slot.getType().equals(type))
                    System.out.println("Floor " + (i + 1) + " slot " + (j + 1));

            }
        }

    }

    public void displayOccupiedSlots(String type) {
        System.out.println("Occupied slots for " + type + ":");
        boolean foundOccupiedSlot = false;

        for (int i = 0; i < slots.size(); i++) {
            for (int j = 0; j < slots.get(i).size(); j++) {
                Slot slot = slots.get(i).get(j);
                if (slot.getVehicle() != null && slot.getType().equals(type)) {
                    System.out.println("Floor " + (i + 1) + " slot " + (j + 1));
                    foundOccupiedSlot = true;
                }

            }

        }

        if (!foundOccupiedSlot) {
            System.out.println("No occupied slots");
        }
    }

    private String generateTicketId(int flr, int slno) {
        return parkingLotId + "_" + flr + "_" + slno;
    }

    private boolean isVehicleTypeAllowed(String type) {
        return type.equals("car") || type.equals("bike") || type.equals("truck");
    }

}