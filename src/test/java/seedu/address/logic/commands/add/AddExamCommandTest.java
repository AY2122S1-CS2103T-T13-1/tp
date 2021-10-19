package seedu.address.logic.commands.add;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyModBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.exam.Exam;
import seedu.address.model.module.lesson.Lesson;
import seedu.address.testutil.builders.ExamBuilder;
import seedu.address.testutil.builders.ModuleBuilder;

public class AddExamCommandTest {

    @Test
    public void constructor_nullLesson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddExamCommand(null, null));
    }

    @Test
    public void execute_examAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingLessonAdded modelStub = new ModelStubAcceptingLessonAdded();
        Exam validExam = new ExamBuilder().build();
        Module validModule = new ModuleBuilder().build();
        ModuleCode validModuleCode = validModule.getCode();

        CommandResult commandResult = new AddExamCommand(validModuleCode, validExam).execute(modelStub);

        assertEquals(String.format(AddExamCommand.MESSAGE_SUCCESS, validExam), commandResult.getFeedbackToUser());
        assertEquals(List.of(validExam), modelStub.lessonsAdded);
    }

    @Test
    public void execute_duplicateModule_throwsCommandException() {
        Exam validExam = new ExamBuilder().build();
        Module validModule = new ModuleBuilder().build();
        ModuleCode validModuleCode = validModule.getCode();

        AddExamCommand addCommand = new AddExamCommand(validModuleCode, validExam);
        ModelStub modelStub = new ModelStubWithExam(validExam);

        assertThrows(CommandException.class, AddExamCommand.MESSAGE_DUPLICATE_EXAM, () ->
                addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Exam alice = new ExamBuilder().withName("Alice").build();
        Exam bob = new ExamBuilder().withName("Bob").build();
        Module validModule = new ModuleBuilder().build();
        ModuleCode validModuleCode = validModule.getCode();
        AddExamCommand addAliceCommand = new AddExamCommand(validModuleCode, alice);
        AddExamCommand addBobCommand = new AddExamCommand(validModuleCode, bob);

        // same object -> returns true
        assertEquals(addAliceCommand, addAliceCommand);

        // same values -> returns true
        AddExamCommand addAliceCommandCopy = new AddExamCommand(validModuleCode, alice);
        assertEquals(addAliceCommand, addAliceCommandCopy);

        // different types -> returns false
        assertNotEquals(1, addAliceCommand);

        // null -> returns false
        assertNotEquals(null, addAliceCommand);

        // different Lesson -> returns false
        assertNotEquals(addAliceCommand, addBobCommand);
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addModule(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Module getModule(ModuleCode modCode) {
            return null;
        }

        @Override
        public boolean moduleHasLesson(Module module, Lesson lesson) {
            return false;
        }

        @Override
        public void addLessonToModule(Module module, Lesson lesson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean moduleHasExam(Module module, Exam exam) {
            return false;
        }

        @Override
        public void addExamToModule(Module module, Exam exam) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasModule(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteModule(Module target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setModule(Module target, Module editedModule) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Module> getFilteredModuleList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredModuleList(Predicate<Module> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getModBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setModBookFilePath(Path modBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setModBook(ReadOnlyModBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyModBook getModBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteExam(Module module, Exam target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteLesson(Module module, Lesson target) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single Module.
     */
    private class ModelStubWithExam extends ModelStub {
        private final Exam lesson;

        ModelStubWithExam(Exam lesson) {
            requireNonNull(lesson);
            this.lesson = lesson;
        }

        @Override
        public boolean moduleHasExam(Module module, Exam exam) {
            requireNonNull(exam);
            return this.lesson.isSameExam(exam);
        }
    }

    /**
     * A Model stub that always accept the Module being added.
     */
    private class ModelStubAcceptingLessonAdded extends ModelStub {
        final ArrayList<Exam> lessonsAdded = new ArrayList<>();

        @Override
        public boolean moduleHasExam(Module module, Exam exam) {
            requireNonNull(exam);
            return lessonsAdded.stream().anyMatch(exam::isSameExam);
        }

        @Override
        public void addExamToModule(Module module, Exam exam) {
            requireNonNull(exam);
            lessonsAdded.add(exam);
        }

        @Override
        public ReadOnlyModBook getModBook() {
            return new ModBook();
        }
    }
}
