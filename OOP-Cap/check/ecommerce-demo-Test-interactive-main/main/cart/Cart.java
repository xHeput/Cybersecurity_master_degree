package main.cart;

import main.model.Product;
import main.promotions.Promotion;
import main.promotions.PromotionFactory;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<Product, Integer> items;
    private Promotion activePromotion;

    public Cart() {
        this.items = new HashMap<>();
        this.activePromotion = null;
    }

    public void addProduct(Product product) {
        if (!product.isAvailable()) {
            System.out.println("Cannot add product: \"" + product.getName() + "\" is not available.");
            return;
        }
        items.put(product, items.getOrDefault(product, 0) + 1);
    }

    public void removeProduct(Product product) {
        if (items.containsKey(product)) {
            int count = items.get(product);
            if (count > 1) {
                items.put(product, count - 1);
            } else {
                items.remove(product);
            }
        }
    }

    public Map<Product, Integer> getProducts() {
        return items;
    }

    public void printContents() {
        if (items.isEmpty()) {
            System.out.println("Shopping cart is empty.");
            return;
        }
        System.out.println("=== Shopping Cart Contents ===");
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            System.out.println(entry.getKey().getName() + ", " + entry.getValue() + " pcs.");
        }
    }

    public void setPromotionByCode(String code) {
        if (PromotionFactory.isValidCode(code)) {
            activePromotion = PromotionFactory.getPromotionByCode(code);
            System.out.println("Promotion \"" + code + "\" applied.");
        } else {
            activePromotion = null;
            System.out.println("Unknown promo code. No promotion applied.");
        }
    }

    public double getTotalPrice() {
        if (activePromotion != null) {
            return activePromotion.apply(items);
        }
        return items.entrySet().stream()
                .mapToDouble(e -> e.getKey().getPrice() * e.getValue())
                .sum();
    }
}
