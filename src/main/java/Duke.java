import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
public class Duke {
    private static final String logo = "   _____  _  _                      \n" +
            "  / ____|| |(_)                     \n" +
            " | |     | | _  _ __   _ __   _   _ \n" +
            " | |     | || || '_ \\ | '_ \\ | | | |\n" +
            " | |____ | || || |_) || |_) || |_| |\n" +
            "  \\_____||_||_|| .__/ | .__/  \\__, |\n" +
            "               | |    | |      __/ |\n" +
            "               |_|    |_|     |___/ ";
    private Ui ui;
    private Storage storage;
    private TaskList taskList;

    public Duke() {
        this.ui = new Ui(">>>", "###");
        this.storage = new Storage(this.ui);
        this.taskList = new TaskList(storage.loadState());
    }

    public void run() {
        boolean shouldContinue = true;
        ui.prettyPrint("Hello! I'm Clippy, your lightweight personal assistant.");
        ui.prettyPrint("What can I do for you today?");

        while (shouldContinue) {
            try {
                String command = ui.readCommand();
                Command c = Parser.parse(command);
                c.execute(ui, taskList, storage);
                shouldContinue = c.shouldContinue();
            } catch (ClippyException e) {
                ui.systemPrint(e.toString());
            } catch (Exception e) {
                ui.systemPrint("System error: " + e.toString());
                shouldContinue = false;
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Hello from\n" + logo);
        new Duke().run();
    }
}