package bg.sofia.uni.fmi.mjt.cocktail.server.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandCreatorTest {
    @Test
    public void testCommandCreationWithNoArguments() {
        String command = "test";
        Command cmd = CommandCreator.newCommand(command);

        assertEquals(command, cmd.command(), "unexpected command returned for command 'test'");
        assertNotNull(cmd.arguments(), "command arguments should not be null");
        assertEquals(0, cmd.arguments().size(), "unexpected command arguments count");
    }

    @Test
    public void testCommandCreationWithOneArgument() {
        String command = "test abcd";
        Command cmd = CommandCreator.newCommand(command);

        assertEquals(command.split(" ")[0], cmd.command(), "unexpected command returned for command 'test abcd'");
        assertNotNull(cmd.arguments(), "command arguments should not be null");
        assertEquals(1, cmd.arguments().size(), "unexpected command arguments count");
        assertEquals(command.split(" ")[1], cmd.arguments().get(0), "unexpected argument returned for command 'test abcd'");
    }

    @Test
    public void testCommandCreationWithOneArgumentInQuotes() {
        String command = "test \"abcd 1234\"";
        Command cmd = CommandCreator.newCommand(command);

        assertEquals(command.split(" ")[0], cmd.command(), "unexpected command returned for command 'test \"abcd 1234\"'");
        assertNotNull(cmd.arguments(), "command arguments should not be null");
        assertEquals(1, cmd.arguments().size(), "unexpected command arguments count");
        assertEquals("abcd 1234", cmd.arguments().get(0), "multi-word argument is not respected");
    }
}