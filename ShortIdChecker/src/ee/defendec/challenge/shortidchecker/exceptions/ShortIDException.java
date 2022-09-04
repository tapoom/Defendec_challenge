package ee.defendec.challenge.shortidchecker.exceptions;

public class ShortIDException extends Throwable {

    // ToDo Must show list of devices what are in conflict.
    public ShortIDException(String guid, String conflictedGUIDs) {
        super("\n{\n" + "\'providedGUID\\':" + guid + ",\n" +
                "'conflictingWithGUIDS': \n" +
                conflictedGUIDs +
                "\n}");
    }
}
