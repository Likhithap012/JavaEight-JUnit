import com.gevernova.StudentGradingSystem.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class StudentGradingSystemTest {

    GradeCalculator calculator = new GradeCalculator();

    GradingStrategy strategy = avg -> {
        if (avg >= 90) return "A";
        else if (avg >= 75) return "B";
        else if (avg >= 50) return "C";
        else return "Fail";
    };

    // Positive: Grade A
    @Test
    void testAssignGrade_A() throws Exception {
        Student s = new Student("John", "001", Arrays.asList(95, 90, 92));
        String grade = calculator.assignGrade(s, strategy);
        assertEquals("A", grade);
    }

    // Positive: Grade C
    @Test
    void testAssignGrade_C() throws Exception {
        Student s = new Student("Jane", "002", Arrays.asList(60, 55, 50));
        String grade = calculator.assignGrade(s, strategy);
        assertEquals("C", grade);
    }

    // Negative: Empty mark list
    @Test
    void testEmptyMarkListException() {
        assertThrows(EmptyMarkListException.class, () -> {
            Student s = new Student("Empty", "003", Collections.emptyList());
            calculator.assignGrade(s, strategy);
        });
    }

    // Negative: Invalid mark in constructor
    @Test
    void testInvalidMarkException() {
        assertThrows(InvalidMarkException.class, () -> {
            new Student("Invalid", "004", Arrays.asList(90, -5, 80));
        });
    }

    // Positive: Average calculation
    @Test
    void testAverageCalculation() throws Exception {
        Student s = new Student("Tester", "005", Arrays.asList(70, 80, 90));
        double avg = calculator.calculateAverage(s);
        assertEquals(80.0, avg, 0.001);
    }
}

