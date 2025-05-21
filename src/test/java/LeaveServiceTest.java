

import com.gevernova.EmployeeLeaveTracker.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LeaveServiceTest {

    LeaveService service = new LeaveService();
    Employee emp = new Employee("Likhitha", 10);

    LeavePolicy casualLeavePolicy = (employee, date) -> employee.getRemainingLeaves() > 0;
    LeavePolicy strictPolicy = (employee, date) -> employee.getRemainingLeaves() >= 3;

    @Test
    void testApplyLeaveValid() {
        assertDoesNotThrow(() ->
                service.applyLeave(emp, LocalDate.now().plusDays(1), casualLeavePolicy));
    }

    @Test
    void testApplyLeavePastDate() {
        Exception ex = assertThrows(InvalidLeaveDateException.class, () ->
                service.applyLeave(emp, LocalDate.now().minusDays(1), casualLeavePolicy));
        assertEquals("Leave date cannot be in the past.", ex.getMessage());
    }

    @Test
    void testApplyLeaveExceedLimit() {
        Employee limitedEmp = new Employee("John", 2);
        try {
            service.applyLeave(limitedEmp, LocalDate.now().plusDays(1), casualLeavePolicy);
            service.applyLeave(limitedEmp, LocalDate.now().plusDays(2), casualLeavePolicy);
        } catch (Exception e) {
            fail("Should not throw here");
        }

        Exception ex = assertThrows(LeaveLimitExceededException.class, () ->
                service.applyLeave(limitedEmp, LocalDate.now().plusDays(3), casualLeavePolicy));
        assertEquals("Leave limit exceeded or not allowed by policy.", ex.getMessage());
    }

    @Test
    void testLowLeaveBalanceFilter() {
        Employee e1 = new Employee("A", 10);
        Employee e2 = new Employee("B", 5);
        Employee e3 = new Employee("C", 7);

        // Apply leaves to make e2 and e3 low on balance
        for (int i = 0; i < 6; i++) e2.applyLeave(LocalDate.now().plusDays(i));
        for (int i = 0; i < 5; i++) e3.applyLeave(LocalDate.now().plusDays(i));

        List<Employee> lowBalance = service.getEmployeesWithLowLeaveBalance(List.of(e1, e2, e3));
        assertEquals(2, lowBalance.size());
        assertTrue(lowBalance.stream().anyMatch(e -> e.getName().equals("B")));
        assertTrue(lowBalance.stream().anyMatch(e -> e.getName().equals("C")));
    }
}

