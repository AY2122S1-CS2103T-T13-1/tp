package t13.modbook.logic.commands.list;

import static java.util.Objects.requireNonNull;

import t13.modbook.logic.commands.CommandResult;
import t13.modbook.logic.commands.GuiState;
import t13.modbook.model.Model;

/**
 * Lists all exams in the ModBook to the user.
 */
public class ListExamCommand extends ListCommand {

    public static final String MESSAGE_SUCCESS = "Listed all exams.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.updateFilteredModuleList(Model.PREDICATE_SHOW_ALL_MODULES);
        return new CommandResult(MESSAGE_SUCCESS, false, GuiState.EXAMS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListExamCommand); // state check
    }
}