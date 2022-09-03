package ee.defendec.challenge.shortidchecker.testing;

import ee.defendec.challenge.shortidchecker.API.API;
import ee.defendec.challenge.shortidchecker.datasyncing.SyncWorker;

public class main {
    public static void main(String[] args) {
        SyncWorker.update();
        API api = new API();
        api.getLocalDBDevicesMap();

    }
}
