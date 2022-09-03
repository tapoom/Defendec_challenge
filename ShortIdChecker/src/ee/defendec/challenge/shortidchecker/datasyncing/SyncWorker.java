package ee.defendec.challenge.shortidchecker.datasyncing;

import ee.defendec.challenge.shortidchecker.devices.Camera;
import ee.defendec.challenge.shortidchecker.setup.GeneralSetup;
import ee.defendec.challenge.shortidchecker.tools.CameraMapper;
import ee.defendec.challenge.shortidchecker.tools.StoreDevicesInDB;

import java.util.HashMap;

public class SyncWorker {

    private static final String EXTERNAL_DB_LOCATION = GeneralSetup.EXTERNAL_DB_LOCATION;
    private static final String LOCAL_DB_LOCATION = GeneralSetup.LOCAL_DB_LOCATION;

    /**
     * Gets the stored data and writes it into map of cameras
     */
    private static HashMap<String, Camera> fetchLocalDevices() {
        return CameraMapper.getCameraMap(LOCAL_DB_LOCATION);
    }

    /**
     * Gets the stored data and writes it into map of cameras
     */
    private static HashMap<String, Camera> fetchExternalDevices() {
        return CameraMapper.getCameraMap(EXTERNAL_DB_LOCATION);
    }

    /**
     * Write local data to text DB
     */
    private static void storeDevicesInLocalDB(HashMap<String, Camera> localDBDevicesMap) {
        StoreDevicesInDB.storeDevices(localDBDevicesMap, LOCAL_DB_LOCATION);
    }

    /**
     * Write external data to text DB
     */
    private static void storeDevicesInExternalDB(HashMap<String, Camera> externalDBDevicesMap) {
        StoreDevicesInDB.storeDevices(externalDBDevicesMap, EXTERNAL_DB_LOCATION);
    }

}
