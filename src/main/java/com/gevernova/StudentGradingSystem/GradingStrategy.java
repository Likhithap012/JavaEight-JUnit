package com.gevernova.StudentGradingSystem;

@FunctionalInterface
public interface GradingStrategy {
    String assignGrade(double average);
}
