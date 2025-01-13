package bg.sofia.uni.fmi.mjt.cocktail.server.storage;

import bg.sofia.uni.fmi.mjt.cocktail.server.Cocktail;
import bg.sofia.uni.fmi.mjt.cocktail.server.Ingredient;
import bg.sofia.uni.fmi.mjt.cocktail.server.storage.exceptions.CocktailAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.cocktail.server.storage.exceptions.CocktailNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DefaultCocktailStorageTest {

    private CocktailStorage storage;
    private Set<String> testIngredients;
    private final Cocktail cocktail1 = new Cocktail("manhattan",
        Set.of(new Ingredient("whisky", "100ml")));
    private final Cocktail cocktail2 = new Cocktail("godfather",
        Set.of(new Ingredient("whisky", "100ml")));
    private final Cocktail cocktail3 = new Cocktail("pirate",
        Set.of(new Ingredient("rum", "100ml")));

    @BeforeEach
    public void setUp() {
        storage = new DefaultCocktailStorage();

        storage.createCocktail(cocktail1);
        storage.createCocktail(cocktail2);
        storage.createCocktail(cocktail3);
    }

    @Test
    public void testCreateCocktailAlreadyExists() {
        assertThrows(CocktailAlreadyExistsException.class,
            () -> storage.createCocktail(cocktail1),
            "CocktailAlreadyExistsException should be thrown if cocktail already exists");
    }

    @Test
    public void testGetCocktailsCorrect() {
        Set<Cocktail> expected = Set.of(cocktail1, cocktail2, cocktail3);
        Collection<Cocktail> actual = storage.getCocktails();

        assertTrue(expected.containsAll(actual), "getCocktails does not work properly");
        assertTrue(actual.containsAll(expected), "getCocktails does not work properly");
    }

    @Test
    public void testGetCocktailsWithIngredientCorrect() {
        Set<Cocktail> expected = Set.of(cocktail1, cocktail2);
        Collection<Cocktail> actual = storage.getCocktailsWithIngredient("whisky");

        assertTrue(expected.containsAll(actual),
            "getCocktailsWithIngredientCorrect does not work properly");
        assertTrue(actual.containsAll(expected),
            "etCocktailsWithIngredientCorrect does not work properly");
    }

    @Test
    public void testGetCocktailNotFound() {
        assertThrows(CocktailNotFoundException.class,
            () -> storage.getCocktail("none"),
            "CocktailNotFoundException should be thrown if there is no cocktail with that name");
    }

    @Test
    public void testGetCocktailCorrect() {
        Cocktail expected = cocktail1;
        Cocktail actual = storage.getCocktail("manhattan");

        assertEquals(expected, actual, "getCocktail does not work properly");
    }
}