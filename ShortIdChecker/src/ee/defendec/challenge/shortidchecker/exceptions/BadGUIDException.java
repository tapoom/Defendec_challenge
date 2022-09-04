package ee.defendec.challenge.shortidchecker.exceptions;

public class BadGUIDException extends Exception {

    public BadGUIDException(String inputGUID) {
        super("Invalid input: " + inputGUID + "\n" + "Must be 8Bytes / 16digits!");
    }
}
