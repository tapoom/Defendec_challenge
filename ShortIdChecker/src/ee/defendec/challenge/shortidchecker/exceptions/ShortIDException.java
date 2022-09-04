package ee.defendec.challenge.shortidchecker.exceptions;

import ee.defendec.challenge.shortidchecker.devices.Camera;
import ee.defendec.challenge.shortidchecker.tools.ShortIDGenerator;

public class ShortIDException extends Throwable {

    public ShortIDException(String guid, Camera conflictedCamera) {
        super("{\n" + "\'providedGUID\\':" + guid + ",\n" +
                "\'conflictingWithGUIDS\':" +
                conflictedCamera.getStringData() +
                "\n}");
    }
}
