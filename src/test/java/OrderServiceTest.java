import com.gevernova.OnlineOrderProcessing.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderServiceTest {

    private OrderService service;
    private User user;
    private Item item;
    private List<Item> items;

    @BeforeEach
    void setUp() {
        service = new OrderService();
        user = new User("Likhitha", "likhitha@example.com");
        item = new Item("Book", 150.0);
        items = List.of(item);
    }

    // Positive Test
    @Test
    void testValidOrder() {
        Order order = new Order(user, items, "CreditCard", "123 Main Street", "PROMO10");
        assertDoesNotThrow(() -> service.validateOrder(order));
    }

    // Negative: Null payment method
    @Test
    void testInvalidPaymentMethod() {
        Order order = new Order(user, items, null, "123 Main Street", null);
        Exception exception = assertThrows(InvalidPaymentException.class, () -> service.validateOrder(order));
        assertEquals("Invalid payment method", exception.getMessage());
    }

    // Negative: Invalid short address
    @Test
    void testInvalidAddress() {
        Order order = new Order(user, items, "UPI", "123", null);
        Exception exception = assertThrows(InvalidAddressException.class, () -> service.validateOrder(order));
        assertEquals("Invalid delivery address", exception.getMessage());
    }

    //  Promo code present
    @Test
    void testOptionalPromoCodePresent() {
        Order order = new Order(user, items, "CreditCard", "123 Main Street", "DISCOUNT5");
        assertTrue(order.getPromoCode().isPresent());
    }

    //  Promo code empty
    @Test
    void testOptionalPromoCodeEmpty() {
        Order order = new Order(user, items, "UPI", "456 Avenue", null);
        assertTrue(order.getPromoCode().isEmpty());
    }

    // Null payment method
    @Test
    void testNullPaymentMethod() {
        Order order = new Order(user, items, null, "123 Main Street", null);
        Exception exception = assertThrows(InvalidPaymentException.class, () -> service.validateOrder(order));
        assertEquals("Invalid payment method", exception.getMessage());
    }

    //  Empty payment method
    @Test
    void testBlankPaymentMethod() {
        Order order = new Order(user, items, "   ", "123 Main Street", null);
        Exception exception = assertThrows(InvalidPaymentException.class, () -> service.validateOrder(order));
        assertEquals("Invalid payment method", exception.getMessage());
    }

    //  Invalid address (too short)
    @Test
    void testInvalidShortAddress() {
        Order order = new Order(user, items, "UPI", "123", null);
        Exception exception = assertThrows(InvalidAddressException.class, () -> service.validateOrder(order));
        assertEquals("Invalid delivery address", exception.getMessage());
    }

    //  Null address
    @Test
    void testNullAddress() {
        Order order = new Order(user, items, "UPI", null, null);
        Exception exception = assertThrows(InvalidAddressException.class, () -> service.validateOrder(order));
        assertEquals("Invalid delivery address", exception.getMessage());
    }


}
