package ee.defendec.challenge.shortidchecker.exceptions;

public class FileReaderException extends RuntimeException {

    /**
     *
     * @param message
     * @param cause
     */
    public FileReaderException(String message, Throwable cause) {
        super(message, cause);
    }
}
