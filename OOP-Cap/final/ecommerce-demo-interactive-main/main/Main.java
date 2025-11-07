package main;

import main.catalog.Catalog;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import main.cart.Cart;
import main.model.Product;

public class Main {
    public static void main(String[] args) {
        Catalog catalog = new Catalog();
        Cart cart = new Cart();
        Scanner scanner = new Scanner(System.in);

        List<Product> productList = catalog.getAllProductsSortedByName();

        boolean running = true;
        while (running) {
            System.out.println("\n=== MENU ===");
            System.out.println("1. Show all products");
            System.out.println("2. Add product to cart");
            System.out.println("3. Remove product from cart");
            System.out.println("4. Apply promotion");
            System.out.println("5. Show cart contents");
            System.out.println("6. Exit");
            System.out.print("Choose an option (1-6): ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("=== All Products ===");
                    for (int i = 0; i < productList.size(); i++) {
                        System.out.printf("%d) %s%n", i + 1, productList.get(i));
                    }
                    break;

                case "2":
                    System.out.println("Enter product number to add to cart:");
                    for (int i = 0; i < productList.size(); i++) {
                        System.out.printf("%d) %s%n", i + 1, productList.get(i));
                    }
                    System.out.print("Product number: ");
                    try {
                        int indexAdd = Integer.parseInt(scanner.nextLine()) - 1;
                        if (indexAdd >= 0 && indexAdd < productList.size()) {
                            Product prodToAdd = productList.get(indexAdd);
                            if (prodToAdd.isAvailable()) {
                                cart.addProduct(prodToAdd);
                                System.out.println("Added to cart: " + prodToAdd.getName());
                            } else {
                                System.out.println("Cannot add: Product is unavailable.");
                            }
                        } else {
                            System.out.println("Invalid product number.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter a valid number.");
                    }
                    break;

                case "3":
                    System.out.println("Enter product number to remove from cart:");
                    List<Product> productsInCart = new ArrayList<>(cart.getProducts().keySet());
                                
                    if (productsInCart.isEmpty()) {
                        System.out.println("Cart is empty.");
                        break;
                    }
                
                    for (int i = 0; i < productsInCart.size(); i++) {
                        Product p = productsInCart.get(i);
                        int quantity = cart.getProducts().get(p);
                        System.out.printf("%d) %s - %d pcs.%n", i + 1, p.getName(), quantity);
                    }
                
                    System.out.print("Product number: ");
                    try {
                        int indexRemove = Integer.parseInt(scanner.nextLine()) - 1;
                        if (indexRemove >= 0 && indexRemove < productsInCart.size()) {
                            Product prodToRemove = productsInCart.get(indexRemove);
                            cart.removeProduct(prodToRemove);
                            System.out.println("Removed from cart: " + prodToRemove.getName());
                        } else {
                            System.out.println("Invalid product number.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter a valid number.");
                    }
                    break;

                case "4":
                    System.out.print("Enter promotion code (PROMO10, 3ZA2, 2POL): ");
                    String code = scanner.nextLine();
                    cart.setPromotionByCode(code);
                    System.out.printf("Promotion '%s' applied. Total: %.2f PLN%n", code, cart.getTotalPrice());
                    break;

                case "5":
                    cart.printContents();
                    System.out.printf("Total price: %.2f PLN%n", cart.getTotalPrice());
                    break;

                case "6":
                    running = false;
                    System.out.println("Exiting the program...");
                    break;

                default:
                    System.out.println("Unknown option. Please try again.");
            }
        }

        scanner.close();
    }
}
