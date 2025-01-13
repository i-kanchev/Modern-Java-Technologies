package bg.sofia.uni.fmi.mjt.cocktail.server.storage.exceptions;

public class CocktailNotFoundException extends RuntimeException {
    public CocktailNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
