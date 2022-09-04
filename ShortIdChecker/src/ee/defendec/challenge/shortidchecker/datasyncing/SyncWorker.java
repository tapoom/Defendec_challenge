package ee.defendec.challenge.shortidchecker.datasyncing;

import ee.defendec.challenge.shortidchecker.devices.Camera;
import ee.defendec.challenge.shortidchecker.setup.GeneralSetup;
import ee.defendec.challenge.shortidchecker.tools.CameraMapper;
import ee.defendec.challenge.shortidchecker.tools.StoreDevicesInDB;

import java.util.HashMap;

public class SyncWorker {

    private static HashMap<String, Camera> localDBDevicesMap = new HashMap<>();

    private static String getDatabaseLocation() {
        String os = System.getProperty("os.name");
        if (os.startsWith("Windows")) {
            return GeneralSetup.EXTERNAL_DB_LOCATION_WIN;
        }
        return GeneralSetup.EXTERNAL_DB_LOCATION_MAC;
    }

    /**
     * Gets the stored data and writes it into map of cameras
     */
    private static HashMap<String, Camera> fetchExternalDevices() {
        return CameraMapper.getCameraMap(getDatabaseLocation());

    }

    /**
     * Write external data to text DB
     */
    private static void storeDevicesInExternalDB(HashMap<String, Camera> devicesMap) {
        StoreDevicesInDB.storeDevices(devicesMap, getDatabaseLocation());
    }

    public static HashMap<String, Camera> getLocalDBDevicesMap() {
        return localDBDevicesMap;
    }

    /**
     * Get the stored data from external database and compare it to the local database.
     * If the camera object with the same short ID exists in both databases then compare the last modification date.
     * Store the last modified camera in the databases.
     *
     * If the camera does not exist in the external database, then add it to the external database and
     * set camera in external database.
     *
     * When the local database is synced to the external database, sync the external database to the local database.
     */
    public static void update() {
        HashMap<String, Camera> storedDataMap = fetchExternalDevices();

        for (String shortIDinLocal : localDBDevicesMap.keySet()) {
            Camera localDBCamera = localDBDevicesMap.get(shortIDinLocal);
            if (storedDataMap.containsKey(shortIDinLocal)) {
                // Check if stored data is older
                Camera externalDBCamera = storedDataMap.get(shortIDinLocal);
                if (externalDBCamera.getLastModifiedDateTime().before(localDBCamera.getLastModifiedDateTime())) {
                    storedDataMap.replace(shortIDinLocal, localDBCamera);
                } else {
                    localDBDevicesMap.replace(shortIDinLocal, externalDBCamera);
                }
            } else {
                // Camera did not exist in the external storage
                localDBCamera.setInExternalDB();
                storedDataMap.put(shortIDinLocal, localDBCamera);
            }
        }
        storeDevicesInExternalDB(storedDataMap);
        localDBDevicesMap = storedDataMap;
    }

}
