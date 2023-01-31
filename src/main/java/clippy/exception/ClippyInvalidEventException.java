package clippy.exception;

/**
 * A complaint that the Event start/end times cannot be recognised.
 *
 * @author chunzkok
 */
public class ClippyInvalidEventException extends ClippyException {
    public ClippyInvalidEventException() {
        super("cannot recognise event start and end time - please specify it as such: \n" +
                "event <description> /from <start> /to <end>");
    }
}
