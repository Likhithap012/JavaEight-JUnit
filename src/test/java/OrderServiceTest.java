
import com.gevernova.OnlineOrderProcessing.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderServiceTest {

    OrderService service = new OrderService();
    User user = new User("Likhitha", "likhitha@example.com");
    Item item = new Item("Book", 150.0);
    List<Item> items = List.of(item);

    @Test
    void testValidOrder() {
        Order order = new Order(user, items, "CreditCard", "123 Main Street", "PROMO10");
        assertDoesNotThrow(() -> service.validateOrder(order));
    }

    @Test
    void testInvalidPaymentMethod() {
        Order order = new Order(user, items, null, "123 Main Street", null);
        Exception ex = assertThrows(InvalidPaymentException.class, () -> service.validateOrder(order));
        assertEquals("Invalid payment method", ex.getMessage());
    }

    @Test
    void testInvalidAddress() {
        Order order = new Order(user, items, "UPI", "123", null);
        Exception ex = assertThrows(InvalidAddressException.class, () -> service.validateOrder(order));
        assertEquals("Invalid delivery address", ex.getMessage());
    }

    @Test
    void testOptionalPromoCodePresent() {
        Order order = new Order(user, items, "CreditCard", "123 Main Street", "DISCOUNT5");
        assertTrue(order.getPromoCode().isPresent());
    }

    @Test
    void testOptionalPromoCodeEmpty() {
        Order order = new Order(user, items, "UPI", "456 Avenue", null);
        assertTrue(order.getPromoCode().isEmpty());
    }
}

