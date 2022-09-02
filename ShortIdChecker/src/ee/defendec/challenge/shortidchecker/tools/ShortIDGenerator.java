package ee.defendec.challenge.shortidchecker.tools;

public class ShortIDGenerator {

    /**
     * Get device GUID and return short ID
     * We produce devices that have unique serial numbers (GUID) of 8 bytes represented as hex.
     * Eg. 018A23EA19000038 is a device GUID
     *
     * For radio communications with neighboring devices a ShortID is used. A ShortID consists
     * of two bytes and can be extracted from the GUID.
     * 1) extract bytes 2-3 of the raw GUID (0x8A and 0x23 in the example)
     * 2) parse the two bytes as little-endian short
     * @return short GUID
     */
    public static String generate(String deviceGUID) {
        // ToDo
        return "DOIT";
    }
}
