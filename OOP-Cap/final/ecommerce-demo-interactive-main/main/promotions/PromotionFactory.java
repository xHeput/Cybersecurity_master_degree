package main.promotions;

import java.util.HashMap;
import java.util.Map;

public class PromotionFactory {
    private static final Map<String, Promotion> promotions = new HashMap<>();

    static {
        promotions.put("PROMO10", new TenPercentOffPromotion());
        promotions.put("3ZA2", new ThreeForTwoPromotion());
        promotions.put("2POL", new SecondHalfPricePromotion());
    }

    public static Promotion getPromotionByCode(String code) {
        return promotions.get(code);
    }

    public static boolean isValidCode(String code) {
        return promotions.containsKey(code);
    }
}

