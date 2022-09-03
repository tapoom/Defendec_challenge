package ee.defendec.challenge.shortidchecker.tools;

import ee.defendec.challenge.shortidchecker.datasyncing.SyncWorker;
import ee.defendec.challenge.shortidchecker.exceptions.ShortIDException;

public class ShortIDChecker {

    /**
     * Check if local DB contains the short GUID.
     * @param GUID
     * @return
     */
    public static boolean checkShortID(String GUID) {
        String shortID = ShortIDGenerator.generate(GUID);
        if (SyncWorker.getLocalDBDevicesMap().containsKey(shortID)) {
            throw new ShortIDException(GUID, SyncWorker.getLocalDBDevicesMap().get(shortID));
        }
        return true;
    }
}
