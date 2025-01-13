package bg.sofia.uni.fmi.mjt.cocktail.server.storage;

import bg.sofia.uni.fmi.mjt.cocktail.server.Cocktail;
import bg.sofia.uni.fmi.mjt.cocktail.server.storage.exceptions.CocktailAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.cocktail.server.storage.exceptions.CocktailNotFoundException;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DefaultCocktailStorage implements CocktailStorage {
    private final Set<Cocktail> cocktails;

    public DefaultCocktailStorage() {
        cocktails = new HashSet<>();
    }
    @Override
    public void createCocktail(Cocktail cocktail) throws CocktailAlreadyExistsException {
        if (cocktails.contains(cocktail)) {
            throw new CocktailAlreadyExistsException("The cocktail already exists.");
        }

        cocktails.add(cocktail);
    }

    @Override
    public Collection<Cocktail> getCocktails() {
        return Collections.unmodifiableCollection(cocktails);
    }

    @Override
    public Collection<Cocktail> getCocktailsWithIngredient(String ingredientName) {
        return cocktails.stream()
            .filter(cocktail -> cocktail.contains(ingredientName))
            .toList();
    }

    @Override
    public Cocktail getCocktail(String name) throws CocktailNotFoundException {
        List<Cocktail> result = cocktails.stream().filter(c -> c.name().equals(name))
            .toList();

        if (result.size() == 0) {
            throw new CocktailNotFoundException("No cocktail with such name");
        }

        return result.get(0);
    }
}
