package clippy.exception;

/**
 * A complaint that the input date cannot be recognised.
 *
 * @author chunzkok
 */
public class ClippyInvalidDateException extends ClippyException {
    /**
     * Creates a ClippyInvalidDateException that contains a message
     * on how to specify the date correctly.
     */
    public ClippyInvalidDateException() {
        super("Uh-oh, Clippy didn't quite understand the date provided.\n"
                + "clippy.task.Task not saved. Try again with dates in the following format: \n"
                + "===> yyyy-mm-dd <====");
    }
}
