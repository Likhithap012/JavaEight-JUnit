import com.gevernova.InventoryManagementSystem.InvalidProductException;
import com.gevernova.InventoryManagementSystem.InventoryService;
import com.gevernova.InventoryManagementSystem.Product;
import com.gevernova.InventoryManagementSystem.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryServiceTest {

    private InventoryService inventory;

    @BeforeEach
    public void setUp() {
        inventory = new InventoryService();
    }

    @Test
    public void testAddProductWithNegativePrice_ThrowsException() {
        assertThrows(InvalidProductException.class, () -> {
            Product product = new Product("Mouse", "Electronics", -50.0, 10);
            inventory.addProduct(product);
        });
    }

    @Test
    public void testAddProductWithNegativeQuantity_ThrowsException() {
        assertThrows(InvalidProductException.class, () -> {
            Product product = new Product("Keyboard", "Electronics", 500.0, -5);
            inventory.addProduct(product);
        });
    }

    @Test
    public void testRemoveNonExistentProduct_ThrowsException() {
        assertThrows(ProductNotFoundException.class, () -> {
            inventory.removeProduct("NonExistentProduct");
        });
    }

    @Test
    public void testSearchByName_ReturnsEmptyList_WhenNoMatch() {
        var results = inventory.searchByName("Ghost");
        assertTrue(results.isEmpty());
    }

    @Test
    public void testSearchByCategory_ReturnsEmptyList_WhenNoMatch() {
        var results = inventory.searchByCategory("ImaginaryCategory");
        assertTrue(results.isEmpty());
    }

    @Test
    public void testLowStockItems_WithEmptyInventory_ReturnsEmptyList() {
        var results = inventory.getLowStockItems();
        assertTrue(results.isEmpty());
    }

    @Test
    public void testGetSortedProducts_WithEmptyInventory_ReturnsEmptyList() {
        var results = inventory.getSortedProducts();
        assertTrue(results.isEmpty());
    }

    @Test
    public void testAddNullProduct_ThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            inventory.addProduct(null);
        });
    }
}
