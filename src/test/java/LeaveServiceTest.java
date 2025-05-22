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

    // ---------------------- POSITIVE TEST CASES ----------------------

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
        Employee e1 = new Employee("Alice", 10);
        Employee e2 = new Employee("Bob", 10);
        Employee e3 = new Employee("Charlie", 10);

        // Apply 7 leaves to e2 to make balance = 3 (low)
        for (int i = 0; i < 7; i++) {
            e2.applyLeave(LocalDate.now().plusDays(i + 1));
        }

        List<Employee> employees = Arrays.asList(e1, e2, e3);
        List<Employee> lowLeaveEmployees = leaveService.getEmployeesWithLowLeaveBalance(employees);

        assertEquals(1, lowLeaveEmployees.size());
        assertEquals("Bob", lowLeaveEmployees.get(0).getName());
    }

    // ---------------------- NEGATIVE TEST CASES ----------------------

    @Test
    public void testApplyLeaveWithPastDateThrowsException() {
        LeavePolicy policy = (emp, date) -> true;

        LocalDate pastDate = LocalDate.now().minusDays(1);

        InvalidLeaveDateException ex = assertThrows(InvalidLeaveDateException.class, () -> {
            leaveService.applyLeave(employee, pastDate, policy);
        });

        assertEquals("Leave date cannot be in the past.", ex.getMessage());
    }

    @Test
    public void testApplyLeaveExceedingLimitThrowsException() {
        // Policy only allows leave if remaining > 0
        LeavePolicy strictPolicy = (emp, date) -> emp.getRemainingLeaves() > 0;

        // Apply all 10 leaves
        for (int i = 0; i < 10; i++) {
            employee.applyLeave(LocalDate.now().plusDays(i + 1));
        }

        LeaveLimitExceededException ex = assertThrows(LeaveLimitExceededException.class, () -> {
            leaveService.applyLeave(employee, LocalDate.now().plusDays(11), strictPolicy);
        });

        assertEquals("Leave limit exceeded or not allowed by policy.", ex.getMessage());
    }
}
