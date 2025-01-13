package bg.sofia.uni.fmi.mjt.myfitnesspal.diary;

import bg.sofia.uni.fmi.mjt.myfitnesspal.nutrition.NutritionInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FoodEntryProteinContentComparatorTest {

    @Test
    void testFoodEntryProteinContentComparatorEqualValues() {
        NutritionInfo nutritionInfo1 = new NutritionInfo(50.0, 30.0, 20.0);
        FoodEntry foodEntry1 = new FoodEntry("food1", 2, nutritionInfo1);

        NutritionInfo nutritionInfo2 = new NutritionInfo(50.0, 30.0, 20.0);
        FoodEntry foodEntry2 = new FoodEntry("food2", 2, nutritionInfo2);

        FoodEntryProteinContentComparator contentComparator = new FoodEntryProteinContentComparator();

        assertEquals(0, contentComparator.compare(foodEntry1, foodEntry2),
            "The value of protein in both food entries should be equal but is not");
    }

    @Test
    void testFoodEntryProteinContentComparatorEqualProtein() {
        NutritionInfo nutritionInfo1 = new NutritionInfo(50.0, 30.0, 20.0);
        FoodEntry foodEntry1 = new FoodEntry("food1", 3, nutritionInfo1);

        NutritionInfo nutritionInfo2 = new NutritionInfo(50.0, 20.0, 30.0);
        FoodEntry foodEntry2 = new FoodEntry("food2", 2, nutritionInfo2);

        FoodEntryProteinContentComparator contentComparator = new FoodEntryProteinContentComparator();

        assertEquals(0, contentComparator.compare(foodEntry1, foodEntry2),
            "The value of protein in both food entries should be equal but is not");
    }

    @Test
    void testFoodEntryProteinContentComparatorFirstMoreProtein() {
        NutritionInfo nutritionInfo1 = new NutritionInfo(60.0, 25.0, 15.0);
        FoodEntry foodEntry1 = new FoodEntry("food1", 2, nutritionInfo1);

        NutritionInfo nutritionInfo2 = new NutritionInfo(50.0, 30.0, 20.0);
        FoodEntry foodEntry2 = new FoodEntry("food2", 1, nutritionInfo2);

        FoodEntryProteinContentComparator contentComparator = new FoodEntryProteinContentComparator();

        assertTrue(0 < contentComparator.compare(foodEntry1, foodEntry2),
            "The value of protein in first food entry should be greater but is not");
    }

    @Test
    void testFoodEntryProteinContentComparatorSecondMoreProtein() {
        NutritionInfo nutritionInfo1 = new NutritionInfo(50.0, 30.0, 20.0);
        FoodEntry foodEntry1 = new FoodEntry("food1", 1, nutritionInfo1);

        NutritionInfo nutritionInfo2 = new NutritionInfo(50.0, 30.0, 20.0);
        FoodEntry foodEntry2 = new FoodEntry("food2", 2, nutritionInfo2);

        FoodEntryProteinContentComparator contentComparator = new FoodEntryProteinContentComparator();

        assertTrue(0 > contentComparator.compare(foodEntry1, foodEntry2),
            "The value of protein in second food entry should be greater but it is not");
    }
}