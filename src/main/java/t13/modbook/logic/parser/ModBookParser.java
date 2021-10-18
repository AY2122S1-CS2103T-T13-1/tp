package t13.modbook.logic.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import t13.modbook.logic.commands.ClearCommand;
import t13.modbook.logic.commands.Command;
import t13.modbook.logic.commands.DetailCommand;
import t13.modbook.logic.commands.ExitCommand;
import t13.modbook.logic.commands.GuiState;
import t13.modbook.logic.commands.HelpCommand;
import t13.modbook.logic.commands.add.AddCommand;
import t13.modbook.logic.commands.delete.DeleteCommand;
import t13.modbook.logic.commands.list.ListCommand;
import t13.modbook.logic.parser.exceptions.ParseException;
import t13.modbook.commons.core.Messages;


/**
 * Parses user input.
 */
public class ModBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput, GuiState guiState) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments, guiState);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments, guiState);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case ListCommand.COMMAND_WORD:
            return new ListCommandParser().parse(arguments, guiState);

        case DetailCommand.COMMAND_WORD:
            return new DetailCommandParser().parse(arguments, guiState);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            throw new ParseException(Messages.MESSAGE_UNKNOWN_COMMAND);
        }
    }

}