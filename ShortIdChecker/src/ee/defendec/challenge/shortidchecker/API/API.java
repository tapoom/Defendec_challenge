package ee.defendec.challenge.shortidchecker.API;

import ee.defendec.challenge.shortidchecker.datasyncing.SyncWorker;
import ee.defendec.challenge.shortidchecker.devices.Camera;
import ee.defendec.challenge.shortidchecker.exceptions.BadGUIDException;
import ee.defendec.challenge.shortidchecker.exceptions.ShortIDException;
import ee.defendec.challenge.shortidchecker.tools.ShortIDChecker;
import ee.defendec.challenge.shortidchecker.tools.ShortIDGenerator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class API {

    public void run() {
        while (true) {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(System.in));
            // Reading data using readLine
            try {
                String name = reader.readLine();
                // Printing the read line
                System.out.println(name);
            } catch (IOException s) {
                System.out.println(s);
            }
        }
    }

    public HashMap<String, Camera> getLocalDBDevicesMap() {
        return SyncWorker.getLocalDBDevicesMap();
    }

    public boolean addCamera(String GUID) {
        try {
            checkGUIDLength(GUID);
            try {
                ShortIDChecker.checkShortID(GUID);
                getLocalDBDevicesMap().put(ShortIDGenerator.generate(GUID), new Camera(GUID));
                return true;
            } catch (ShortIDException message) {
                System.out.println(message);
                return false;
            }
        } catch (BadGUIDException badGUIDMessage) {
            System.out.println(badGUIDMessage);
        }
        return false;
    }

    public boolean updateCameraName(String shortID, String newName) {
        if (getLocalDBDevicesMap().containsKey(shortID)) {
            getLocalDBDevicesMap().get(shortID).updateCustomerName(newName);
            return true;
        }
        return false;
    }

    private boolean checkGUIDLength(String GUID) throws BadGUIDException {
        if (GUID.length() != 16) {
            throw new BadGUIDException(GUID);
        }
        return true;
    }

    public void deleteDeviceFromLocalDB(String shortID) {
        SyncWorker.getLocalDBDevicesMap().remove(shortID);
    }


}
