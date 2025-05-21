package com.gevernova.EmployeeLeaveTracker;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Employee {
    private String name;
    private int totalLeaves;
    private List<LocalDate> appliedLeaves;

    public Employee(String name, int totalLeaves) {
        this.name = name;
        this.totalLeaves = totalLeaves;
        this.appliedLeaves = new ArrayList<>();
    }

    public String getName() { return name; }
    public int getTotalLeaves() { return totalLeaves; }
    public List<LocalDate> getAppliedLeaves() { return appliedLeaves; }

    public int getRemainingLeaves() {
        return totalLeaves - appliedLeaves.size();
    }

    public void applyLeave(LocalDate date) {
        appliedLeaves.add(date);
    }
}

