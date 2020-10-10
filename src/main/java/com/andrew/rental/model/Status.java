package com.andrew.rental.model;

public enum Status {
    AVAILABLE,
    RENTED;
    public static Status fromString(String statusString) {
        if (statusString.equalsIgnoreCase("available")) {
            return AVAILABLE;
        } else if (statusString.equalsIgnoreCase("rented")) {
            return RENTED;
        }
        throw new IllegalArgumentException("Bad status");
    }
}
