package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.GuiState;
import seedu.address.logic.parser.exceptions.GuiStateException;

public class ClearCommandParserTest {

    private ClearCommandParser parser = new ClearCommandParser();

    @Test
    public void parser_validGuiState_returnsClearCommand() {
        ClearCommand expectedClearCommand = new ClearCommand();

        assertParseSuccess(parser, "", GuiState.SUMMARY, expectedClearCommand);
        assertParseSuccess(parser, "", GuiState.LESSONS, expectedClearCommand);
        assertParseSuccess(parser, "", GuiState.EXAMS, expectedClearCommand);
    }

    @Test
    public void parser_invalidGuiState_throwsGuiStateException() {
        assertParseFailure(parser, "", GuiState.DETAILS, GuiStateException.ERROR_MESSAGE);
    }
}
