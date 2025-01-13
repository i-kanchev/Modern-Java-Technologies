package bg.sofia.uni.fmi.mjt.myfitnesspal.nutrition;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NutritionInfoTest {

    @Test
    void testNutritionInfoNegativeNutrient() {
        assertThrows(IllegalArgumentException.class, () ->
            new NutritionInfo(-1.0, -1.0, -1.0),
            "Any nutrient in the nutrition info should be non-negative");
    }

    @Test
    void testNutritionInfoDifferentSumThan100() {
        assertThrows(IllegalArgumentException.class, () ->
                new NutritionInfo(30, 30, 30),
            "The sum of all nutrients should be 100");
    }

    @Test
    void testNutritionInfoCaloriesRightCalculation() {
        NutritionInfo nutritionInfo = new NutritionInfo(75.0, 10.0, 15.0);
        assertEquals(450, 0.001, nutritionInfo.calories());
    }
}