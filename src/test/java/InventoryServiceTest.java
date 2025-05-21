import com.gevernova.InventoryManagementSystem.InvalidProductException;
import com.gevernova.InventoryManagementSystem.InventoryService;
import com.gevernova.InventoryManagementSystem.Product;
import com.gevernova.InventoryManagementSystem.ProductNotFoundException;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class InventoryServiceTest {

    private InventoryService service;

    @BeforeEach
    void setUp() {
        service = new InventoryService();
    }

    @Test
    void testAddValidProduct() {
        Product p = new Product("Milk", "Grocery", 30.0, 10);
        service.addProduct(p);
        assertEquals(1, service.getAllProducts().size());
    }

    @Test
    void testInvalidProductConstructor() throws InvalidProductException {
        assertThrows(InvalidProductException.class, () -> {
            new Product("Soap", "Toiletries", -10.0, 5);
        });
    }

    @Test
    void testRemoveExistingProduct() {
        Product p = new Product("Pen", "Stationery", 10.0, 20);
        service.addProduct(p);
        service.removeProduct("Pen");
        assertEquals(0, service.getAllProducts().size());
    }

    @Test
    void testRemoveNonExistingProduct() {
        assertThrows(ProductNotFoundException.class, () -> {
            service.removeProduct("Laptop");
        });
    }

    @Test
    void testSearchByNameFound() {
        Product p = new Product("Chips", "Snacks", 15.0, 12);
        service.addProduct(p);
        List<Product> found = service.searchByName("Chips");
        assertFalse(found.isEmpty());
        assertEquals("Chips", found.get(0).getName());
    }

    @Test
    void testSearchByNameNotFound() {
        List<Product> found = service.searchByName("Keyboard");
        assertTrue(found.isEmpty());
    }

    @Test
    void testLowStockItems() {
        service.addProduct(new Product("Sugar", "Grocery", 40.0, 3));
        service.addProduct(new Product("Tea", "Grocery", 50.0, 10));
        List<Product> lowStock = service.getLowStockItems();
        assertEquals(1, lowStock.size());
        assertEquals("Sugar", lowStock.get(0).getName());
    }

    @Test
    void testSortingByCategoryAndPrice() {
        service.addProduct(new Product("Notebook", "Stationery", 50.0, 15));
        service.addProduct(new Product("Pen", "Stationery", 10.0, 30));
        service.addProduct(new Product("Milk", "Grocery", 25.0, 5));

        List<Product> sorted = service.getSortedProducts();
        assertEquals("Milk", sorted.get(0).getName()); // Grocery comes before Stationery
        assertEquals("Pen", sorted.get(1).getName());  // Stationery, price 10
        assertEquals("Notebook", sorted.get(2).getName()); // Stationery, price 50
    }
}

