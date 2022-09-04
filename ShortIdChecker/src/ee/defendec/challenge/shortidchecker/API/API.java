package ee.defendec.challenge.shortidchecker.API;

import ee.defendec.challenge.shortidchecker.datasyncing.SyncWorker;
import ee.defendec.challenge.shortidchecker.devices.Camera;
import ee.defendec.challenge.shortidchecker.exceptions.BadGUIDException;
import ee.defendec.challenge.shortidchecker.exceptions.ShortIDException;
import ee.defendec.challenge.shortidchecker.tools.ShortIDChecker;

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

    // ToDo after adding we should do a post from the API?
    public void addCamera(String GUID) {
        Camera newCamera = new Camera(GUID);
        try {
            checkGUIDLength(GUID);
            try {
                ShortIDChecker.checkShortIDsAndUpdateLocalDB(newCamera);
                getLocalDBDevicesMap().put(newCamera.getGUID(), newCamera);
                if (!newCamera.getConflictedCameras().isEmpty()) {
                    throw new ShortIDException(GUID, newCamera.getConflictedGUIDsString());
                }
            } catch (ShortIDException message) {
                System.out.println(message);
            }
        } catch (BadGUIDException badGUIDMessage) {
            System.out.println(badGUIDMessage);
        }
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
