package bg.sofia.uni.fmi.mjt.cocktail.server.command;

import bg.sofia.uni.fmi.mjt.cocktail.server.Cocktail;
import bg.sofia.uni.fmi.mjt.cocktail.server.storage.CocktailStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CommandExecutorTest {
    private static final String INVALID_ARGS_COUNT_MESSAGE_FORMAT =
        "Invalid count of arguments: \"%s\" expects %s argument%s. Example: \"%s\"";

    private static final String CREATE = "create";
    private static final String GET = "get";
    private static final String ALL = "all";
    private static final String BY_NAME = "by-name";
    private static final String BY_INGREDIENT = "by-ingredient";
    private static final String DISCONNECT = "disconnect";

    private CocktailStorage cocktailStorage;
    private CommandExecutor cmdExecutor;

    private Command add = new Command("create", List.of("manhattan", "whisky=100ml"));

    @BeforeEach
    public void setUp() {
        cocktailStorage = mock(CocktailStorage.class);
        cmdExecutor = new CommandExecutor(cocktailStorage);
    }

    @Test
    public void testCreate() {
        String expected = String.format("{\"status\":\"CREATED\"}");
        String actual = cmdExecutor.execute(add);

        assertEquals(expected, actual, "unexpected output for create");
    }

    @Test
    public void testCreateReturnsErrorWhenLessArguments() {

    }

    @Test
    public void testCreateReturnsErrorWhenMoreArguments() {

    }

    @Test
    public void testGetAllInsufficient() {

    }

    @Test
    public void testGetAll() {

    }

    @Test
    public void testGetByNameInsufficient() {

    }

    @Test
    public void testGetByName() {

    }

    @Test
    public void testGetByIngredientInsufficient() {

    }

    @Test
    public void testGetByIngredient() {

    }

    @Test
    public void testGetCocktailsString() {

    }

    @Test
    public void testGetSingleCocktailString() {

    }

    @Test
    public void testDisconnect() {

    }

    @Test
    public void testUnknownCommand() {
        String expected = "Unknown command";
        String actual = cmdExecutor.execute(new Command("test", new ArrayList<>()));

        assertEquals(expected, actual, "unexpected output for unknown command");
    }
}