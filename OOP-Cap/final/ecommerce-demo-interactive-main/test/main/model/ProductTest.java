package main.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    @Test
    void testProductCreation() {
        // Given
        String name = "Laptop";
        double price = 1200.00;
        Category category = Category.ELECTRONICS;
        boolean available = true;

        // When
        Product product = new Product(name, price, category, available);

        // Then
        assertNotNull(product);
        assertEquals(name, product.getName());
        assertEquals(price, product.getPrice());
        assertEquals(category, product.getCategory());
        assertTrue(product.isAvailable());
    }

    @Test
    void testProductAvailability() {
        // Given
        String name = "Mouse";
        double price = 25.00;
        Category category = Category.ELECTRONICS;
        boolean available = false;

        // When
        Product product = new Product(name, price, category, available);

        // Then
        assertFalse(product.isAvailable());
    }

    @Test
    void testToString() {
        // Given
        String name = "Keyboard";
        double price = 75.00;
        Category category = Category.ELECTRONICS;
        boolean available = true;
        Product product = new Product(name, price, category, available);

        // When
        String productString = product.toString();

        // Then
        assertEquals("Keyboard - 75.0 PLN", productString);
    }

    @Test
    void testEqualsAndHashCode() {
        // Given
        Product product1 = new Product("Monitor", 300.00, Category.ELECTRONICS, true);
        Product product2 = new Product("Monitor", 300.00, Category.ELECTRONICS, false);
        Product product3 = new Product("Webcam", 50.00, Category.ELECTRONICS, true);

        // When & Then
        // The 'When' and 'Then' phases are combined here as the assertions directly follow the setup.
        assertEquals(product1, product2);
        assertEquals(product1.hashCode(), product2.hashCode());
        assertNotEquals(product1, product3);
    }
}

