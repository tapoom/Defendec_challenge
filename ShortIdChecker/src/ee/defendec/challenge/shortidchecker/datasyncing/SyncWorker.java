package ee.defendec.challenge.shortidchecker.datasyncing;

import ee.defendec.challenge.shortidchecker.devices.Camera;
import ee.defendec.challenge.shortidchecker.setup.GeneralSetup;
import ee.defendec.challenge.shortidchecker.tools.CameraMapper;
import ee.defendec.challenge.shortidchecker.tools.StoreDevicesInDB;

import java.util.HashMap;

public class SyncWorker implements Runnable{

    public static final SyncWorker INSTANCE = new SyncWorker();

    private static HashMap<String, Camera> localDBDevicesMap = new HashMap<>();
    private static HashMap<String, Camera> externalDBDeviceMap = new HashMap<>();

    private boolean running;

    public synchronized void start() {
        this.running = true;
        final Thread thread = new Thread(this);
        thread.start();
    }

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
    private static void fetchExternalDevices() {
        externalDBDeviceMap = CameraMapper.getCameraMap(getDatabaseLocation());
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
        fetchExternalDevices();
        for (String shortIDinLocal : localDBDevicesMap.keySet()) {
            Camera localDBCamera = localDBDevicesMap.get(shortIDinLocal);
            if (externalDBDeviceMap.containsKey(shortIDinLocal)) {
                // Check if stored data is older
                Camera externalDBCamera = externalDBDeviceMap.get(shortIDinLocal);
                // If local DB camera does not have a name then check if the external DB has it. If it does then
                // update it. If they both have a name then use the most recently updated camera name and assign it.
                if (!localDBCamera.getCustomerName().isBlank() && !externalDBCamera.getCustomerName().isBlank()) {
                    if (externalDBCamera.getLastModifiedDateTime().before(localDBCamera.getLastModifiedDateTime())) {
                        localDBCamera.updateLastModified();
                        externalDBDeviceMap.replace(shortIDinLocal, localDBCamera);
                    } else {
                        externalDBCamera.updateLastModified();
                        localDBDevicesMap.replace(shortIDinLocal, externalDBCamera);
                    }
                } else if (localDBCamera.getCustomerName().isBlank() && !externalDBCamera.getCustomerName().isBlank()) {
                    externalDBCamera.updateLastModified();
                    localDBDevicesMap.replace(shortIDinLocal, externalDBCamera);
                } else {
                    localDBCamera.updateLastModified();
                    externalDBDeviceMap.replace(shortIDinLocal, localDBCamera);
                }
            } else {
                // Camera did not exist in the external storage
                localDBCamera.setInExternalDB();
                externalDBDeviceMap.put(shortIDinLocal, localDBCamera);
            }
        }
        storeDevicesInExternalDB(externalDBDeviceMap);
    }

    public static HashMap<String, Camera> getExternalDBMap() {
        fetchExternalDevices();
        return externalDBDeviceMap;
    }

    @Override
    public void run() {
        long pastTime = System.nanoTime();
        double updatesPerSecond = 0.2;  // Update every 5 seconds
        double ns = 1000000000 / updatesPerSecond;
        double delta = 0;

        while (this.running) {
            try {
                Thread.sleep((long) (60F / updatesPerSecond));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long now = System.nanoTime();
            delta += (now - pastTime) / ns;
            pastTime = now;

            while (delta > 0) {
                update();
                delta--;
            }
        }
    }
}
