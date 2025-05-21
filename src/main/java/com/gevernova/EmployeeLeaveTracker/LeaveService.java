package com.gevernova.EmployeeLeaveTracker;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class LeaveService {

    public void applyLeave(Employee employee, LocalDate date, LeavePolicy policy)
            throws LeaveLimitExceededException, InvalidLeaveDateException {

        if (date.isBefore(LocalDate.now())) {
            throw new InvalidLeaveDateException("Leave date cannot be in the past.");
        }

        if (!policy.isLeaveAllowed(employee, date)) {
            throw new LeaveLimitExceededException("Leave limit exceeded or not allowed by policy.");
        }

        employee.applyLeave(date);
    }

    public List<Employee> getEmployeesWithLowLeaveBalance(List<Employee> employees) {
        Predicate<Employee> hasLowBalance = e -> e.getRemainingLeaves() < 5;
        return employees.stream()
                .filter(hasLowBalance)
                .collect(Collectors.toList());
    }
}

