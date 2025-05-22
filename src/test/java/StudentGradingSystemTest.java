import com.gevernova.StudentGradingSystem.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class StudentGradingSystemTest {

    private GradeCalculator calculator;
    private GradingStrategy strategy;

    @BeforeEach
    void setUp() {
        calculator = new GradeCalculator();
        strategy = avg -> {
            if (avg >= 90) return "A";
            else if (avg >= 75) return "B";
            else if (avg >= 50) return "C";
            else return "Fail";
        };
    }

    // Positive: Grade A
    @Test
    void testAssignGrade_A() throws Exception {
        Student student = new Student("John", "001", Arrays.asList(95, 90, 92));
        String grade = calculator.assignGrade(student, strategy);
        assertEquals("A", grade);
    }

    // Positive: Grade C
    @Test
    void testAssignGrade_C() throws Exception {
        Student student = new Student("Jane", "002", Arrays.asList(60, 55, 50));
        String grade = calculator.assignGrade(student, strategy);
        assertEquals("C", grade);
    }

    // Negative: Empty mark list
    @Test
    void testEmptyMarkListException() {
        assertThrows(EmptyMarkListException.class, () -> {
            Student student = new Student("Empty", "003", Collections.emptyList());
            calculator.assignGrade(student, strategy);
        });
    }

    // Negative: Invalid mark (negative)
    @Test
    void testInvalidMarkException_NegativeMark() {
        assertThrows(InvalidMarkException.class, () -> {
            new Student("Invalid", "004", Arrays.asList(90, -5, 80));
        });
    }

    // Negative: Invalid mark (above 100)
    @Test
    void testInvalidMarkException_Above100() {
        assertThrows(InvalidMarkException.class, () -> {
            new Student("Overflow", "005", Arrays.asList(100, 105, 98));
        });
    }

    // Positive: Average calculation
    @Test
    void testAverageCalculation() throws Exception {
        Student student = new Student("Tester", "006", Arrays.asList(70, 80, 90));
        double avg = calculator.calculateAverage(student);
        assertEquals(80.0, avg, 0.001);
    }


    // Negative: Null student passed to assignGrade
    @Test
    void testAssignGrade_NullStudent() {
        assertThrows(NullPointerException.class, () -> {
            calculator.assignGrade(null, strategy);
        });
    }

    // Negative: Null strategy passed to assignGrade
    @Test
    void testAssignGrade_NullStrategy() throws Exception {
        Student student = new Student("Strategist", "008", Arrays.asList(60, 70, 80));
        assertThrows(NullPointerException.class, () -> {
            calculator.assignGrade(student, null);
        });
    }
}
