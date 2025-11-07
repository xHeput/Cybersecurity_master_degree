package main.promotions;

import main.model.Product;

import java.util.Map;

public class SecondHalfPricePromotion implements Promotion {
    @Override
    public double apply(Map<Product, Integer> items) {
        double total = 0.0;

        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product product = entry.getKey();
            int count = entry.getValue();
            int pairs = count / 2;
            int rest = count % 2;

            total += pairs * (product.getPrice() + product.getPrice() / 2.0);
            total += rest * product.getPrice();
        }

        return total;
    }
}
