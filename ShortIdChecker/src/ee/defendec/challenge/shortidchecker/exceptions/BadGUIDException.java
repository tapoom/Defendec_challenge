package ee.defendec.challenge.shortidchecker.exceptions;

public class BadGUIDException extends Exception {

    public BadGUIDException(String inputGUID) {
        super("{\nInvalid input: '" + inputGUID + "'\n" + "GUID must be 8Bytes / 16digits!\n}");
    }
}
