package bg.sofia.uni.fmi.mjt.cocktail.server.storage.exceptions;

public class CocktailAlreadyExistsException extends RuntimeException {
    public CocktailAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }
}
