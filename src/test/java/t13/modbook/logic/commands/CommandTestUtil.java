package t13.modbook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static t13.modbook.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static t13.modbook.logic.parser.CliSyntax.PREFIX_CODE;
import static t13.modbook.logic.parser.CliSyntax.PREFIX_DAY;
import static t13.modbook.logic.parser.CliSyntax.PREFIX_EMAIL;
import static t13.modbook.logic.parser.CliSyntax.PREFIX_END;
import static t13.modbook.logic.parser.CliSyntax.PREFIX_LINK;
import static t13.modbook.logic.parser.CliSyntax.PREFIX_NAME;
import static t13.modbook.logic.parser.CliSyntax.PREFIX_PHONE;
import static t13.modbook.logic.parser.CliSyntax.PREFIX_START;
import static t13.modbook.logic.parser.CliSyntax.PREFIX_TAG;
import static t13.modbook.logic.parser.CliSyntax.PREFIX_VENUE;
import static t13.modbook.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import t13.modbook.commons.core.index.Index;
import t13.modbook.logic.commands.exceptions.CommandException;
import seedu.modbook.model.AddressBook;
import t13.modbook.model.Model;
import t13.modbook.model.module.Module;
import t13.modbook.model.module.predicates.HasModuleCodePredicate;
import seedu.modbook.model.person.NameContainsKeywordsPredicate;
import seedu.modbook.model.person.Person;
import t13.modbook.testutil.EditPersonDescriptorBuilder;
import t13.modbook.testutil.Assert;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String VALID_MODULE_CODE = "CS2103T";
    public static final String VALID_MODULE_NAME = "Software Engineering";
    public static final String VALID_LESSON_NAME = "CS2103T Lecture";
    public static final String VALID_EXAM_NAME = "Finals";
    public static final String VALID_DAY = "FRIDAY";
    public static final String VALID_DATE = "24/11/2021";
    public static final String VALID_START_TIME = "16:00";
    public static final String VALID_END_TIME = "18:00";
    public static final String VALID_LINK = "profDamith.com";
    public static final String VALID_VENUE = "COM1";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String VALID_MODULE_CODE_DESC = " " + PREFIX_CODE + VALID_MODULE_CODE;
    public static final String VALID_MODULE_NAME_DESC = " " + PREFIX_NAME + VALID_MODULE_NAME;
    public static final String VALID_LESSON_NAME_DESC = " " + PREFIX_NAME + VALID_LESSON_NAME;
    public static final String VALID_EXAM_NAME_DESC = " " + PREFIX_NAME + VALID_EXAM_NAME;
    public static final String VALID_DAY_DESC = " " + PREFIX_DAY + VALID_DAY;
    public static final String VALID_DATE_DESC = " " + PREFIX_DAY + VALID_DATE;
    public static final String VALID_START_TIME_DESC = " " + PREFIX_START + VALID_START_TIME;
    public static final String VALID_END_TIME_DESC = " " + PREFIX_END + VALID_END_TIME;
    public static final String VALID_LINK_DESC = " " + PREFIX_LINK + VALID_LINK;
    public static final String VALID_VENUE_DESC = " " + PREFIX_VENUE + VALID_VENUE;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_MODULE_CODE_DESC = " " + PREFIX_CODE + "C2030S";
    public static final String INVALID_MODULE_NAME_DESC = " " + PREFIX_NAME + "";
    public static final String INVALID_LESSON_NAME_DESC = " " + PREFIX_NAME + "";
    public static final String INVALID_EXAM_NAME_DESC = " " + PREFIX_NAME + "";
    public static final String INVALID_DAY_DESC = " " + PREFIX_DAY + "jupiter";
    public static final String INVALID_DATE_DESC = " " + PREFIX_DAY + "mon";
    public static final String INVALID_LINK_DESC = " " + PREFIX_LINK + "";
    public static final String INVALID_START_TIME_DESC = " " + PREFIX_START + "100";
    public static final String INVALID_END_TIME_DESC = " " + PREFIX_END + "100";
    public static final String INVALID_VENUE_DESC = " " + PREFIX_VENUE + "";

    public static final String RANDOM_TEXT = " " + "momo";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        Assert.assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the module at the given {@code targetIndex} in the
     * {@code model}'s ModBook.
     */
    public static void showModuleAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredModuleList().size());

        Module module = model.getFilteredModuleList().get(targetIndex.getZeroBased());
        model.updateFilteredModuleList(new HasModuleCodePredicate(module.getCode()));

        assertEquals(1, model.getFilteredModuleList().size());
    }

}