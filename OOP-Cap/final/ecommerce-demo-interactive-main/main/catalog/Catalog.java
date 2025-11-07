package main.catalog;

import main.model.Product;
import main.model.Category;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Catalog {
    private List<Product> products;

    public Catalog() {
        this.products = new ArrayList<>();
        preloadProducts();
    }

    private void preloadProducts() {
        products.add(new Product("Waterproof Jacket", 499.99, Category.CLOTHING, true));
        products.add(new Product("Trekking Boots", 629.00, Category.FOOTWEAR, true));
        products.add(new Product("Backpack 40L", 349.00, Category.BACKPACKS, true));
        products.add(new Product("Backpack 20L", 199.00, Category.BACKPACKS, true));
        products.add(new Product("Backpack 60L", 499.00, Category.BACKPACKS, true));
        products.add(new Product("2-Person Tent", 899.99, Category.TENTS, false));
        products.add(new Product("LED Headlamp", 149.99, Category.LIGHTING, true));
        products.add(new Product("Gas Stove", 99.90, Category.CAMP_KITCHEN, true));
        products.add(new Product("Titanium Pot", 149.50, Category.CAMP_KITCHEN, true));
        products.add(new Product("Camping Cutlery Set", 39.90, Category.CAMP_KITCHEN, true));
        products.add(new Product("Windshield for Stove", 59.00, Category.CAMP_KITCHEN, true));
        products.add(new Product("Mini Stove - discontinued", 79.99, Category.CAMP_KITCHEN, false));
        products.add(new Product("Climbing Quickdraw Set", 299.99, Category.CLIMBING_GEAR, true));
        products.add(new Product("GPS Watch", 1199.00, Category.ELECTRONICS, false));

    }

    public List<Product> getAllProductsSortedByName() {
        return products.stream()
                .sorted(Comparator.comparing(Product::getName))
                .collect(Collectors.toList());
    }

    public List<String> getAvailableProductsByCategorySortedByPrice(Category category) {
        return products.stream()
                .filter(p -> p.getCategory() == category && p.isAvailable())
                .sorted(Comparator.comparingDouble(Product::getPrice))
                .map(Product::toString)
                .collect(Collectors.toList());
    }
}
