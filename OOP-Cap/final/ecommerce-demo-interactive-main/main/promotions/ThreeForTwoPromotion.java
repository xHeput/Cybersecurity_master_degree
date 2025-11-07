package main.promotions;

import main.model.Product;

import java.util.*;

public class ThreeForTwoPromotion implements main.promotions.Promotion {
    @Override
    public double apply(Map<Product, Integer> items) {
        List<Double> allPrices = new ArrayList<>();

        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                allPrices.add(entry.getKey().getPrice());
            }
        }

        allPrices.sort(Double::compare);

        int discountCount = allPrices.size() / 3;

        for (int i = 0; i < discountCount; i++) {
            allPrices.set(i, 1.00);
        }

        double total = allPrices.stream().mapToDouble(Double::doubleValue).sum();

        return total;
    }
}
