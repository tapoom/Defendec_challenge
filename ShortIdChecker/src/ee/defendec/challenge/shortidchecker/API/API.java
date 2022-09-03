package ee.defendec.challenge.shortidchecker.API;

import ee.defendec.challenge.shortidchecker.datasyncing.SyncWorker;
import ee.defendec.challenge.shortidchecker.devices.Camera;
import ee.defendec.challenge.shortidchecker.tools.ShortIDChecker;
import ee.defendec.challenge.shortidchecker.tools.ShortIDGenerator;

import java.util.HashMap;

public class API {

    public HashMap<String, Camera> getLocalDBDevicesMap() {
        return SyncWorker.getLocalDBDevicesMap();
    }

    public boolean addCamera(String GUID) {
        if (ShortIDChecker.checkShortID(GUID)) {
            getLocalDBDevicesMap().put(ShortIDGenerator.generate(GUID), new Camera(GUID));
            return true;
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


}
