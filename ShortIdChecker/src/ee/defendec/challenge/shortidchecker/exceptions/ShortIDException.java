package ee.defendec.challenge.shortidchecker.exceptions;

import ee.defendec.challenge.shortidchecker.devices.Camera;

public class ShortIDException extends RuntimeException {

    /**
     *
     * @param providedGUID
     * @param camera
     */
    public ShortIDException(String providedGUID, Camera camera) {
        String message = "{\n" + "\'providedGUID\\':" + providedGUID + ",\n"+
                "\'conflictingWithGUIDS\':" + camera.getStringData() +
                "\n}";
    }
}
