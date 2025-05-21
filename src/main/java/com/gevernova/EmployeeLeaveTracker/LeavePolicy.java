package com.gevernova.EmployeeLeaveTracker;

import java.time.LocalDate;

@FunctionalInterface
public interface LeavePolicy {
    boolean isLeaveAllowed(Employee employee, LocalDate date);
}
