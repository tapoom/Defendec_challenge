package ee.defendec.challenge.shortidchecker.tools;

import java.util.HexFormat;
// import javax.xml.bind.DatatypeConverter;

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
        int len = deviceGUID.length();
        byte[] data = new byte[len / 2];
        // Iterate over the string array by 2 and store all presented Bytes
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(deviceGUID.charAt(i), 16) << 4)
                    + Character.digit(deviceGUID.charAt(i+1), 16));
        }

        System.out.println(data);
        System.out.println(data[0]);
        System.out.println(data[1]);
        System.out.println(data[2]);
        System.out.println(data[3]);
        System.out.println(data[4]);
        System.out.println(data[5]);
        System.out.println(data[6]);
        System.out.println(data[7]);
        System.out.println(data[1] + data[2]);

        for (int i = 0; i < len; i += 2) {for (int i = 0; i < len; i += 2) {for (int i = 0 < len; i +=2)
        // System.out.println("oneliner");
        // System.out.println(DatatypeConverter.parseHexBinary(deviceGUID));
        return "DOIT";
    }

    public static void main(String[] args) {
        ShortIDGenerator.generate("018A23EA19000038");
        // ShortIDGenerator.generate("018A23EA19000038");
    }
}
