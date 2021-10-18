package t13.modbook.logic.parser;

import t13.modbook.logic.commands.Command;
import t13.modbook.logic.commands.GuiState;
import t13.modbook.logic.parser.exceptions.ParseException;

/**
 * Represents a Parser that is able to parse user input into a {@code Command} of type {@code T}.
 */
public interface Parser<T extends Command> {

    /**
     * Parses {@code userInput} into a command and returns it.
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    T parse(String userInput, GuiState guiState) throws ParseException;
}