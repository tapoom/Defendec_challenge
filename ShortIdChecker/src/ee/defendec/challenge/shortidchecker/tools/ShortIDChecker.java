package ee.defendec.challenge.shortidchecker.tools;

import ee.defendec.challenge.shortidchecker.datasyncing.SyncWorker;
import ee.defendec.challenge.shortidchecker.devices.Camera;
import java.util.HashMap;

public class ShortIDChecker {

    /**
     * Check if local DB contains the short GUID.
     * @return
     */
    public static void checkShortIDsAndUpdateLocalDB(Camera newCamera) {
        HashMap<String, Camera> localDB = SyncWorker.getLocalDBDevicesMap();
        for (String storedGUID : localDB.keySet()) {
            Camera storedCamera = localDB.get(storedGUID);
            if (newCamera.getShortID().equals(storedCamera.getShortID())) {
                newCamera.addConflictedGUID(storedCamera);
                storedCamera.addConflictedGUID(newCamera);
            }
        }
    }
}
