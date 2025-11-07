package main.promotions;

import java.util.Map;
import main.model.Product;

public interface Promotion {
    double apply(Map<Product, Integer> items);
}