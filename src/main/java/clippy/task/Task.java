package clippy.task;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public abstract class Task {
    private String description;
    private boolean done;

    public Task(String description) {
        this.description = description;
        // assumed to be not complete upon initialisation
        // would not make sense to add a finished task to the list
        this.done = false;
    }

    public void complete() {
        this.done = true;
    }

    public void uncomplete() {
        this.done = false;
    }

    @Override
    public String toString() {
        return (this.done ? "[X] " : "[ ] ") + this.description;
    }

    public String getCsvString() {
        return String.format("%s,%b", this.description, this.done);
    }

    public static Task parseCsvString(String csvString) {
        String[] arguments = csvString.split(",");
        Task result = null;
        try {
            switch (arguments[0]) {
                case "D":
                    result = new Deadline(arguments[1], LocalDate.parse(arguments[3]));
                    break;
                case "T":
                    result = new ToDo(arguments[1]);
                    break;
                case "E":
                    result = new Event(
                            arguments[1],
                            LocalDate.parse(arguments[3]),
                            LocalDate.parse(arguments[4]));
                    break;
                default:
                    return null;
            }
        } catch (DateTimeParseException e) {
            System.out.println(">>> Unable to parse date: " + e.toString());
            return null;
        }
        result.done = Boolean.parseBoolean(arguments[2]);
        return result;
    }

}
