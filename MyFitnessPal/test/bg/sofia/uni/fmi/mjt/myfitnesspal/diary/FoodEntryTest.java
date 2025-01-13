package bg.sofia.uni.fmi.mjt.myfitnesspal.diary;

import bg.sofia.uni.fmi.mjt.myfitnesspal.nutrition.NutritionInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FoodEntryTest {

    @Test
    void testFoodEntryFoodNullValue() {
        NutritionInfo nutritionInfo = new NutritionInfo(50.0, 30.0, 20.0);
        assertThrows(IllegalArgumentException.class,
            () -> new FoodEntry(null, 1, nutritionInfo),
            "Food cannot be null");
    }

    @Test
    void testFoodEntryFoodBlank() {
        NutritionInfo nutritionInfo = new NutritionInfo(50.0, 30.0, 20.0);
        assertThrows(IllegalArgumentException.class,
            () -> new FoodEntry(" ", 1, nutritionInfo),
            "Food cannot be blank");
    }

    @Test
    void testFoodEntryNegativeServingSize() {
        NutritionInfo nutritionInfo = new NutritionInfo(50.0, 30.0, 20.0);
        assertThrows(IllegalArgumentException.class,
            () -> new FoodEntry("food", -1, nutritionInfo),
            "Serving size cannot be negative");
    }

    @Test
    void testFoodEntryNutritionInfoNullValue() {
        assertThrows(IllegalArgumentException.class,
            () -> new FoodEntry("food", 1, null),
            "Nutrition info cannot be null");
    }
}