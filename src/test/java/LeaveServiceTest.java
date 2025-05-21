import com.gevernova.EmployeeLeaveTracker.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LeaveServiceTest {

    private LeaveService leaveService;
    private Employee employee;

    @BeforeEach
    public void setup() {
        leaveService = new LeaveService();
        employee = new Employee("John", 10);
    }

    // POSITIVE TEST CASES

    @Test
    public void testApplyLeaveSuccessfully() throws Exception {
        LeavePolicy simplePolicy = (emp, date) -> emp.getRemainingLeaves() > 0;

        LocalDate leaveDate = LocalDate.now().plusDays(1); // Future date
        leaveService.applyLeave(employee, leaveDate, simplePolicy);

        assertEquals(1, employee.getAppliedLeaves().size());
        assertTrue(employee.getAppliedLeaves().contains(leaveDate));
    }

    @Test
    public void testGetEmployeesWithLowLeaveBalance() {
        Employee employeeOne = new Employee("Alice", 10);
        Employee employeeTwo = new Employee("Bob", 10);
        Employee employeeThree = new Employee("Charlie", 10);

        // Apply 7 leaves to e2 to make balance = 3 (low)
        for (int i = 0; i < 7; i++) {
            employeeTwo.applyLeave(LocalDate.now().plusDays(i + 1));
        }

        List<Employee> employees = Arrays.asList(employeeOne, employeeTwo, employeeThree);
        List<Employee> lowLeaveEmployees = leaveService.getEmployeesWithLowLeaveBalance(employees);

        assertEquals(1, lowLeaveEmployees.size());
        assertEquals("Bob", lowLeaveEmployees.get(0).getName());
    }

    // NEGATIVE TEST CASES

    @Test
    public void testApplyLeaveWithPastDateThrowsException() {
        LeavePolicy policy = (emp, date) -> true;

        LocalDate pastDate = LocalDate.now().minusDays(1);

        InvalidLeaveDateException exception = assertThrows(InvalidLeaveDateException.class, () -> {
            leaveService.applyLeave(employee, pastDate, policy);
        });

        assertEquals("Leave date cannot be in the past.", exception.getMessage());
    }

    @Test
    public void testApplyLeaveExceedingLimitThrowsException() {
        // Policy only allows leave if remaining > 0
        LeavePolicy strictPolicy = (emp, date) -> emp.getRemainingLeaves() > 0;

        // Apply all 10 leaves
        for (int i = 0; i < 10; i++) {
            employee.applyLeave(LocalDate.now().plusDays(i + 1));
        }

        LeaveLimitExceededException exception = assertThrows(LeaveLimitExceededException.class, () -> {
            leaveService.applyLeave(employee, LocalDate.now().plusDays(11), strictPolicy);
        });

        assertEquals("Leave limit exceeded or not allowed by policy.", exception.getMessage());
    }
}
