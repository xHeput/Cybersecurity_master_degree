package main.promotions;

import main.model.Product;
import java.util.Map;

public class TenPercentOffPromotion implements Promotion {
    @Override
    public double apply(Map<Product, Integer> items) {
        double total = items.entrySet().stream()
                .mapToDouble(e -> e.getKey().getPrice() * e.getValue())
                .sum();
        return total * 0.9;
    }
}
