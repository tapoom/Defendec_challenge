package ee.defendec.challenge.shortidchecker.testing;

import ee.defendec.challenge.shortidchecker.devices.Camera;
import ee.defendec.challenge.shortidchecker.tools.GetDevicesFromDB;

public class main {
    public static void main(String[] args) {
        Camera camera = new Camera("Tanel", "343435434", "1988/08/25 17:05:30");
        System.out.println(camera.getLastModified());
        System.out.println(camera.getDeviceGUID());
        System.out.println(camera.getShortID());
        System.out.println(camera.getCustomerName());
        camera.updateLastModified();
        System.out.println(camera.getLastModified());
        Camera camera2 = new Camera("Tanel", "343435434", "1988/08");
        System.out.println(camera2.getLastModified());
        System.out.println(camera2.getDeviceGUID());
        System.out.println(camera2.getShortID());
        System.out.println(camera2.getCustomerName());
        camera2.updateLastModified();
        System.out.println(camera2.getLastModified());

        System.out.println(new GetDevicesFromDB().readTextFromFile("C:\\Users\\Tanel\\IdeaProjects" +
                "\\Defendec_challenge\\ShortIdChecker\\src\\ee\\defendec\\challenge\\shortidchecker" +
                "\\internalstorage\\localDB"));

    }
}
