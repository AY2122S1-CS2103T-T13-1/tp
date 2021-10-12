package seedu.address.logic.parser;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.GuiState;
import seedu.address.logic.parser.exceptions.GuiStateException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Not technically a true Parser because it doesn't parse userInput, only guiState.
 * This is to prevent ClearCommand from throwing a ParseException and increase cohesion.
 */
public class ClearCommandParser implements Parser<ClearCommand> {

    @Override
    public ClearCommand parse(String userInput, GuiState guiState) throws ParseException {
        if (guiState == GuiState.DETAILS) {
            throw new GuiStateException();
        }
        return new ClearCommand();
    }
}
