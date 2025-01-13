package bg.sofia.uni.fmi.mjt.myfitnesspal.diary;

import bg.sofia.uni.fmi.mjt.myfitnesspal.exception.UnknownFoodException;
import bg.sofia.uni.fmi.mjt.myfitnesspal.nutrition.MacroNutrient;
import bg.sofia.uni.fmi.mjt.myfitnesspal.nutrition.NutritionInfo;
import bg.sofia.uni.fmi.mjt.myfitnesspal.nutrition.NutritionInfoAPI;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DailyFoodDiaryTest {

    @Mock
    private NutritionInfoAPI nutritionInfoApiMock;

    @InjectMocks
    private DailyFoodDiary dailyFoodDiary;

    @Test
    void testDailyFoodDiaryAddFoodMealNullValue() {
        assertThrows(IllegalArgumentException.class,
            () -> dailyFoodDiary.addFood(null, "food", 1),
            "Meal cannot be null");
    }

    @Test
    void testDailyFoodDiaryAddFoodFoodNullValue() {
        assertThrows(IllegalArgumentException.class,
            () -> dailyFoodDiary.addFood(Meal.LUNCH, null, 1),
            "Food cannot be null");
    }

    @Test
    void testDailyFoodDiaryAddFoodFoodBlank() {
        assertThrows(IllegalArgumentException.class,
            () -> dailyFoodDiary.addFood(Meal.LUNCH, " ", 1),
            "Food cannot be blank");
    }

    @Test
    void testDailyFoodDiaryAddFoodNegativeServingSize() {
        assertThrows(IllegalArgumentException.class,
            () -> dailyFoodDiary.addFood(Meal.LUNCH, "food", -1),
            "Serving size cannot be negative");
    }

    @Test
    void testDailyFoodDiaryAddFoodNoNutritionInfo() throws UnknownFoodException {
        when(nutritionInfoApiMock.getNutritionInfo("noFood"))
            .thenThrow(new UnknownFoodException("Food has no nutrition info"));

        assertThrows(UnknownFoodException.class,
            () -> dailyFoodDiary.addFood(Meal.LUNCH, "noFood", 1),
            "UnknownFoodException should be thrown");
    }

    @Test
    void testDailyFoodDiaryAddFoodCorrectData() throws UnknownFoodException {
        when(nutritionInfoApiMock.getNutritionInfo("food"))
            .thenReturn(new NutritionInfo(70.0, 10.0, 20.0));

        NutritionInfo nutritionInfo = new NutritionInfo(70.0, 10.0, 20.0);
        FoodEntry foodEntry = new FoodEntry("food", 2.0, nutritionInfo);

        FoodEntry foodEntryData = dailyFoodDiary.addFood(Meal.BREAKFAST, "food", 2);

        assertEquals(foodEntry, foodEntryData);
    }

    @Test
    void testDailyFoodDiaryGetAllFoodEntriesUnmodifiableCollection() {
        NutritionInfo nutritionInfo = new NutritionInfo(70.0, 10.0, 20.0);
        assertThrows(UnsupportedOperationException.class,
            () -> dailyFoodDiary.getAllFoodEntries().add(new FoodEntry("food", 1, nutritionInfo)),
            "Should return unmodifiable collection");
    }

    @Test
    void testDailyFoodDiaryGetAllFoodEntriesByProteinContentUnmodifiableCollection() {
        NutritionInfo nutritionInfo = new NutritionInfo(70.0, 10.0, 20.0);
        assertThrows(UnsupportedOperationException.class,
            () -> dailyFoodDiary.getAllFoodEntriesByProteinContent().add(new FoodEntry("food", 1, nutritionInfo)),
            "Should return unmodifiable collection");
    }

    @Test
    void testGetDailyCaloriesIntakeCorrectData() throws UnknownFoodException {
        when(nutritionInfoApiMock.getNutritionInfo("sandwich"))
            .thenReturn(new NutritionInfo(80.0, 15.0, 5.0));
        when(nutritionInfoApiMock.getNutritionInfo("pasta"))
            .thenReturn(new NutritionInfo(75.0, 10.0, 15.0));
        when(nutritionInfoApiMock.getNutritionInfo("pizza"))
            .thenReturn(new NutritionInfo(70.0, 10.0, 20.0));
        when(nutritionInfoApiMock.getNutritionInfo("salad"))
            .thenReturn(new NutritionInfo(95.0, 0.0, 5.0));

        dailyFoodDiary.addFood(Meal.BREAKFAST, "sandwich" , 2);
        dailyFoodDiary.addFood(Meal.LUNCH, "pizza", 1);
        dailyFoodDiary.addFood(Meal.LUNCH, "pasta", 1);
        dailyFoodDiary.addFood(Meal.DINNER, "salad", 1);

        assertEquals(2250.0, dailyFoodDiary.getDailyCaloriesIntake());
    }

    @Test
    void testDailyFoodDiaryCaloriesPerMealNullValue() {
        assertThrows(IllegalArgumentException.class,
            () -> dailyFoodDiary.getDailyCaloriesIntakePerMeal(null),
            "Meal cannot be null");
    }

    @Test
    void testDailyFoodDiaryCaloriesPerMealCorrectData() throws UnknownFoodException {
        when(nutritionInfoApiMock.getNutritionInfo("pasta"))
            .thenReturn(new NutritionInfo(75.0, 10.0, 15.0));
        when(nutritionInfoApiMock.getNutritionInfo("pizza"))
            .thenReturn(new NutritionInfo(70.0, 10.0, 20.0));
        when(nutritionInfoApiMock.getNutritionInfo("food"))
            .thenReturn(new NutritionInfo(65.0, 10.0, 25.0));

        dailyFoodDiary.addFood(Meal.LUNCH, "pizza", 1);
        dailyFoodDiary.addFood(Meal.LUNCH, "pasta", 2);
        dailyFoodDiary.addFood(Meal.DINNER, "food", 1);

        assertEquals(1350.0, dailyFoodDiary.getDailyCaloriesIntakePerMeal(Meal.LUNCH));
    }
}