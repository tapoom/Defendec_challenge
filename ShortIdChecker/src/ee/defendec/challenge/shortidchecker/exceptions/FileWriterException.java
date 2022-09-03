package ee.defendec.challenge.shortidchecker.exceptions;

public class FileWriterException extends RuntimeException {

    /**
     *
     * @param message
     * @param cause
     */
    public FileWriterException(String message, Throwable cause) {
        super(message, cause);
    }
}
