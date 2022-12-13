package com.api.parkingcontrol.exception;

public class NotFoundUserException extends BusinessException {
    public NotFoundUserException() {
        super("User not found.");
    }
}
