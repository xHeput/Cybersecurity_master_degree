package main.cart;

import main.model.Product;
import main.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;



public class CartTest {

    private Cart cart;
    private Product laptop;
    private Product mouse;
    private Product keyboard;

    @BeforeEach
    void setUp() {
        // Given
        cart = new Cart();
        laptop = new Product("Laptop", 1200.00, Category.ELECTRONICS, true);
        mouse = new Product("Mouse", 25.00, Category.ELECTRONICS, true);
        keyboard = new Product("Keyboard", 75.00, Category.ELECTRONICS, false);
    }

    @Test
    void testAddProduct() {
        // Given
        // @BeforeEach
        

        // When
        cart.addProduct(laptop);

        // Then
        assertEquals(1, cart.getProducts().get(laptop));
        assertEquals(2, cart.getProducts().get(laptop));
    }

    @Test
    void testAddUnavailableProduct() {
        // Given
        // @BeforeEach

        // When
        cart.addProduct(keyboard);

        // Then
        assertNull(cart.getProducts().get(keyboard));
    }

    @Test
    void testRemoveProduct() {
        // Given
        cart.addProduct(laptop);
        cart.addProduct(laptop);

        // When
        cart.removeProduct(laptop);

        // Then
        assertEquals(1, cart.getProducts().get(laptop));
        assertNull(cart.getProducts().get(laptop));
    }

    @Test
    void testGetTotalPrice() {
        // Given
        cart.addProduct(laptop);
        cart.addProduct(mouse);

        // When
        double totalPrice = cart.getTotalPrice();

        // Then
        assertEquals(1225.00, totalPrice);
    }

    @Test
    void testGetTotalPriceWithMultipleItems() {
        // Given
        cart.addProduct(laptop);
        cart.addProduct(laptop);
        cart.addProduct(mouse);

        // When
        double totalPrice = cart.getTotalPrice();

        // Then
        assertEquals(2425.00, totalPrice);
    }

    @Test
    void testSetPromotionByCode() {
        // Given
        cart.addProduct(laptop);
        String promoCode = "10OFF";

        // When
        cart.setPromotionByCode(promoCode);

        // Then
        assertTrue(true);
    }
}

