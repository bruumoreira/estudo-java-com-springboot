package com.api.parkingcontrol.exception;

public class NotFoundParkingSpotException extends BusinessException {

    public NotFoundParkingSpotException() {
        super("Parking Spot not found.");
    }
}
