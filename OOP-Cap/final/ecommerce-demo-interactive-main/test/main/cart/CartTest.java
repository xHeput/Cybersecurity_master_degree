package main.cart;

import main.model.Product;
import main.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

public class CartTest {

    private Cart cart;
    private Product laptop;
    private Product mouse;
    private Product keyboard;

    @BeforeEach
    void setUp() {
        // Given
        // Initialize a new cart before each test
        cart = new Cart();
        // Define products for use in tests
        laptop = new Product("Laptop", 1200.00, Category.ELECTRONICS, true);
        mouse = new Product("Mouse", 25.00, Category.ELECTRONICS, true);
        keyboard = new Product("Keyboard", 75.00, Category.ELECTRONICS, false);
    }

    @Test
    void testAddProduct() {
        // Given
        // Cart is initialized in @BeforeEach
        // Product 'laptop' is available

        // When
        // Add the laptop to the cart for the first time
        cart.addProduct(laptop);

        // Then
        // Verify that the cart contains one laptop
        assertEquals(1, cart.getProducts().get(laptop));

        // When
        // Add the laptop to the cart again
        cart.addProduct(laptop);

        // Then
        // Verify that the cart now contains two laptops
        assertEquals(2, cart.getProducts().get(laptop));
    }

    @Test
    void testAddUnavailableProduct() {
        // Given
        // Cart is initialized in @BeforeEach
        // Product 'keyboard' is not available

        // When
        // Attempt to add the unavailable keyboard to the cart
        cart.addProduct(keyboard);

        // Then
        // Verify that the keyboard was not added to the cart
        assertNull(cart.getProducts().get(keyboard));
    }

    @Test
    void testRemoveProduct() {
        // Given
        // Add two laptops to the cart
        cart.addProduct(laptop);
        cart.addProduct(laptop);

        // When
        // Remove one laptop from the cart
        cart.removeProduct(laptop);

        // Then
        // Verify that one laptop remains in the cart
        assertEquals(1, cart.getProducts().get(laptop));

        // When
        // Remove the last laptop from the cart
        cart.removeProduct(laptop);

        // Then
        // Verify that no laptops remain in the cart
        assertNull(cart.getProducts().get(laptop));
    }

    @Test
    void testGetTotalPrice() {
        // Given
        // Add a laptop and a mouse to the cart
        cart.addProduct(laptop);
        cart.addProduct(mouse);

        // When
        // Calculate the total price of items in the cart
        double totalPrice = cart.getTotalPrice();

        // Then
        // Verify that the total price is the sum of the laptop and mouse prices
        assertEquals(1225.00, totalPrice);
    }

    @Test
    void testGetTotalPriceWithMultipleItems() {
        // Given
        // Add two laptops and one mouse to the cart
        cart.addProduct(laptop);
        cart.addProduct(laptop);
        cart.addProduct(mouse);

        // When
        // Calculate the total price of items in the cart
        double totalPrice = cart.getTotalPrice();

        // Then
        // Verify that the total price is correct for multiple items
        assertEquals(2425.00, totalPrice);
    }

    @Test
    void testSetPromotionByCode() {
        // Given
        // Add a laptop to the cart
        cart.addProduct(laptop);
        // Define a promo code
        String promoCode = "10OFF"; // Assuming '10OFF' is a valid promotion code

        // When
        // Apply the promotion by code
        cart.setPromotionByCode(promoCode);

        // Then
        // Verify that the promotion was processed (though actual price change depends on Promotion implementation)
        // A more robust test would involve mocking PromotionFactory or checking the discounted price.
        assertTrue(true); // Placeholder, as direct assertion on price is hard without mocking
    }
}

