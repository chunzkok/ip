import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Parser {
    public static Command parse(String command) throws ClippyException {
        String[] args = command.split(" ");
        final String[] EMPTY_ARG_LIST = new String[]{};
        switch (args[0]) {
        case "bye":
            return dispatch(CommandType.BYE, EMPTY_ARG_LIST);
        case "mark":
            // todo: check if second argument is a valid number
            return dispatch(CommandType.MARK, new String[] { args[1] });
        case "unmark":
            // todo: check if second argument is a valid number
            return dispatch(CommandType.UNMARK, new String[] { args[1] });
        case "todo":
            if (args.length < 2) {
                throw new ClippyTodoEmptyDescriptionException();
            }
            return dispatch(CommandType.TODO, new String[] { args[1] });
        case "deadline":
        {
            int byIndex = command.indexOf("/by ");
            if (byIndex == -1 || command.length() < byIndex + 4) {
                throw new ClippyMissingDeadlineException();
            }
            String description = command.substring(9, byIndex).trim();
            String byDateString = command.substring(byIndex + 4).trim();
            try {
                LocalDate byDate = LocalDate.parse(byDateString);
                return dispatch(CommandType.DEADLINE, new String[] { description }, byDate);
            } catch (DateTimeParseException e) {
                throw new ClippyInvalidDateException();
            }
        }
        case "event": {

            int fromIndex = command.indexOf("/from ");
            int toIndex = command.indexOf("/to ");
            if (fromIndex == -1 || toIndex == -1 || toIndex - fromIndex < 5 ||
                    command.length() - toIndex < 4) {
                throw new ClippyInvalidEventException();
            }

            String description = command.substring(6, fromIndex).trim();
            String fromDateString = command.substring(fromIndex + 6, toIndex).trim();
            String toDateString = command.substring(toIndex + 4).trim();
            try {
                LocalDate fromDate = LocalDate.parse(fromDateString);
                LocalDate toDate = LocalDate.parse(toDateString);
                return dispatch(CommandType.EVENT, new String[] { description }, fromDate, toDate);
            } catch (DateTimeParseException e) {
                throw new ClippyInvalidDateException();
            }

        }
        case "list":
            return dispatch(CommandType.LIST, EMPTY_ARG_LIST);
        case "delete":
            // todo: check for valid list index
            return dispatch(CommandType.DELETE, new String[]{ args[1] });
        default:
            throw new ClippyUnknownCommandException();
        }
    }

    private static Command dispatch(CommandType commandType, String[] args, LocalDate... dates) {
        switch (commandType) {
        case BYE:
            return new ByeCommand();
        case MARK:
            return new MarkCommand(Integer.parseInt(args[0]));
        case UNMARK:
            return new UnmarkCommand(Integer.parseInt(args[0]));
        case TODO:
            return new AddToDoCommand(args[0]);
        case DEADLINE:
            return new AddDeadlineCommand(args[0], dates[0]);
        case EVENT:
            return new AddEventCommand(args[0], dates[0], dates[1]);
        case LIST:
            return new ListCommand();
        case DELETE:
            return new DeleteCommand(Integer.parseInt(args[0]));
        default:
            return null;
        }
    }
}
