package t13.modbook.logic.parser;

import static t13.modbook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static t13.modbook.logic.parser.CliSyntax.PREFIX_CODE;
import static t13.modbook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static t13.modbook.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static t13.modbook.logic.parser.DeleteCommandParser.MESSAGE_WRONG_VIEW_DETAILS;
import static t13.modbook.logic.parser.DeleteCommandParser.MESSAGE_WRONG_VIEW_SUMMARY;
import static t13.modbook.logic.parser.ParserUtil.MESSAGE_NO_INDEXES_FOUND;

import org.junit.jupiter.api.Test;

import t13.modbook.logic.commands.GuiState;
import t13.modbook.logic.commands.delete.DeleteCommand;
import t13.modbook.logic.commands.delete.DeleteExamCommand;
import t13.modbook.logic.commands.delete.DeleteLessonCommand;
import t13.modbook.logic.commands.delete.DeleteModCommand;
import t13.modbook.model.module.Module;
import t13.modbook.testutil.builders.ModuleBuilder;
import t13.modbook.testutil.TypicalIndexes;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {
    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "    ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingKeyword_throwsParseException() {
        assertParseFailure(parser, "venue",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingIndex_throwsParseException() {
        assertParseFailure(parser, "mod lesson", MESSAGE_NO_INDEXES_FOUND);
    }

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        Module module = new ModuleBuilder().build();
        // no leading and trailing whitespaces
        DeleteCommand expectedDeleteModCommand = new DeleteModCommand(TypicalIndexes.INDEX_FIRST_MODULE);
        assertParseSuccess(parser, " mod " + TypicalIndexes.INDEX_FIRST_MODULE.getOneBased(), expectedDeleteModCommand);

        DeleteCommand expectedDeleteLessonCommand = new DeleteLessonCommand(TypicalIndexes.INDEX_FIRST_LESSON, module.getCode());
        assertParseSuccess(parser, " lesson " + TypicalIndexes.INDEX_FIRST_LESSON.getOneBased()
                + " " + PREFIX_CODE + module.getCode(), GuiState.DETAILS, expectedDeleteLessonCommand);

        DeleteCommand expectedDeleteExamCommand = new DeleteExamCommand(TypicalIndexes.INDEX_FIRST_EXAM, module.getCode());
        assertParseSuccess(parser, " exam " + TypicalIndexes.INDEX_FIRST_EXAM.getOneBased()
                + " " + PREFIX_CODE + module.getCode(), GuiState.DETAILS, expectedDeleteExamCommand);

        // leading and trailing whitespaces
        assertParseSuccess(parser, " mod " + TypicalIndexes.INDEX_FIRST_MODULE.getOneBased()
                + " \n \t ", expectedDeleteModCommand);

        // delete mod command must only work in the summary gui state
        assertParseSuccess(parser, " mod " + TypicalIndexes.INDEX_FIRST_MODULE.getOneBased(),
                GuiState.SUMMARY, expectedDeleteModCommand);
        assertParseFailure(parser, " mod " + TypicalIndexes.INDEX_FIRST_MODULE.getOneBased(),
                GuiState.LESSONS, MESSAGE_WRONG_VIEW_SUMMARY);
        assertParseFailure(parser, " mod " + TypicalIndexes.INDEX_FIRST_MODULE.getOneBased(),
                GuiState.EXAMS, MESSAGE_WRONG_VIEW_SUMMARY);
        assertParseFailure(parser, " mod " + TypicalIndexes.INDEX_FIRST_MODULE.getOneBased(),
                GuiState.DETAILS, MESSAGE_WRONG_VIEW_SUMMARY);

        // delete exam command must only work in the details gui state
        assertParseSuccess(parser, " exam " + TypicalIndexes.INDEX_FIRST_EXAM.getOneBased()
                + " " + PREFIX_CODE + module.getCode(), GuiState.DETAILS, expectedDeleteExamCommand);
        assertParseFailure(parser, " exam " + TypicalIndexes.INDEX_FIRST_EXAM.getOneBased()
                + " " + PREFIX_CODE + module.getCode(), GuiState.SUMMARY, MESSAGE_WRONG_VIEW_DETAILS);
        assertParseFailure(parser, " exam " + TypicalIndexes.INDEX_FIRST_EXAM.getOneBased()
                + " " + PREFIX_CODE + module.getCode(), GuiState.LESSONS, MESSAGE_WRONG_VIEW_DETAILS);
        assertParseFailure(parser, " exam " + TypicalIndexes.INDEX_FIRST_EXAM.getOneBased()
                + " " + PREFIX_CODE + module.getCode(), GuiState.EXAMS, MESSAGE_WRONG_VIEW_DETAILS);

        assertParseSuccess(parser, " lesson " + TypicalIndexes.INDEX_FIRST_MODULE.getOneBased()
                + " " + PREFIX_CODE + module.getCode(), GuiState.DETAILS, expectedDeleteLessonCommand);
        assertParseFailure(parser, " lesson " + TypicalIndexes.INDEX_FIRST_LESSON.getOneBased()
                + " " + PREFIX_CODE + module.getCode(), GuiState.SUMMARY, MESSAGE_WRONG_VIEW_DETAILS);
        assertParseFailure(parser, " lesson " + TypicalIndexes.INDEX_FIRST_LESSON.getOneBased()
                + " " + PREFIX_CODE + module.getCode(), GuiState.LESSONS, MESSAGE_WRONG_VIEW_DETAILS);
        assertParseFailure(parser, " lesson " + TypicalIndexes.INDEX_FIRST_LESSON.getOneBased()
                + " " + PREFIX_CODE + module.getCode(), GuiState.EXAMS, MESSAGE_WRONG_VIEW_DETAILS);
    }
}