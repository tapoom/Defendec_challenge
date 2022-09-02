package ee.defendec.challenge.shortidchecker.testing;

import ee.defendec.challenge.shortidchecker.devices.Camera;

public class main {
    public static void main(String[] args) {
        Camera camera = new Camera("Tanel", "343435434");
        System.out.println(camera.getLastModified());
        System.out.println(camera.getDeviceGUID());
        System.out.println(camera.getShortID());
        System.out.println(camera.getCustomerName());
        camera.updateLastModified();
        System.out.println(camera.getLastModified());
    }
}
