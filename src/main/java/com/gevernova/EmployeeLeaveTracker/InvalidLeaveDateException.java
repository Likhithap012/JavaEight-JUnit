package com.gevernova.EmployeeLeaveTracker;

public class InvalidLeaveDateException extends Exception {
    public InvalidLeaveDateException(String message) {
        super(message);
    }
}
