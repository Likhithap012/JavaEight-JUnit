package com.gevernova.StudentGradingSystem;

import java.util.OptionalDouble;

public class GradeCalculator {

    public double calculateAverage(Student student) throws EmptyMarkListException {
        if (student.getMarks().isEmpty()) {
            throw new EmptyMarkListException("Mark list is empty.");
        }
        OptionalDouble avg = student.getMarks().stream()
                .mapToInt(Integer::intValue)
                .average();
        return avg.orElse(0.0);
    }

    public String assignGrade(Student student, GradingStrategy strategy) throws EmptyMarkListException {
        double avg = calculateAverage(student);
        return strategy.assignGrade(avg);
    }
}
