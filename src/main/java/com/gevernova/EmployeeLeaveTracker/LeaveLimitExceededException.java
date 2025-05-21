package com.gevernova.EmployeeLeaveTracker;

public class LeaveLimitExceededException extends Exception {
    public LeaveLimitExceededException(String message) {
        super(message);
    }
}
