package ee.defendec.challenge.shortidchecker.main;

import ee.defendec.challenge.shortidchecker.API.API;
import ee.defendec.challenge.shortidchecker.datasyncing.SyncWorker;

public class Main {
    public static void main(String[] args) {

        SyncWorker.INSTANCE.start(20,false);
        API api = new API();

        api.doPostForTestingInternal("01DABAFOODFOODFO");
        api.doPostForTestingInternal("01DADAFOODFOODFO");
        api.doPostForTestingInternal("EAD5E2649FCB779A");
        api.doPostForTestingInternal("01BABAFOODFOODFO");
        api.doPostForTestingInternal("01BABACAFECAFECA");
        api.doPostForTestingInternal("01VABACAFECAFECA");
        api.doPostForTestingInternal("01VAVACAFECAFECA");
        api.doPostForTestingInternal("01VABACAKECAKECA");
        api.doPostForTestingInternal("01VABACAKECAKECAKE");
        api.doPostForTestingInternal("01VARACAKECAKECA");
        api.doPostForTestingInternal("C69DA9CEE58D8A7D");
        System.out.println("Customer name: " + api.getLocalDBDevicesMap().get("C69DA9CEE58D8A7D").getCustomerName());
        long pastTime = System.nanoTime();
        long updateAfter = 500000000;
        boolean updated = false;
        while (!updated) {
            if (pastTime + updateAfter < System.nanoTime()) {
                System.out.println("Should be prisma after updating");
                api.doPostForTestingInternal("C69DA9CEE58D8A7D");
                updated = true;
            }
        }

        api.doPostForTestingInternal("01DABAFOODFOODFO");
        api.doPostForTestingInternal("01DADAFOODFOODFO");
        api.doPostForTestingInternal("EAD5E2649FCB779A");
        api.doPostForTestingInternal("01BABAFOODFOODFO");
        api.doPostForTestingInternal("01BABACAFECAFECA");
        api.doPostForTestingInternal("01VABACAFECAFECA");
        api.doPostForTestingInternal("01VAVACAFECAFECA");
        api.doPostForTestingInternal("01VABACAKECAKECA");
        api.doPostForTestingInternal("01VARACAKECAKECA");
        api.doPostForTestingInternal("C69DA9CEE58D8A7D");

        api.runConsoleInterface();
        /**
         * /POST/"GUID" - Post the GUID into the local DB
         * /GET/"GUID" - Get the GUID info in local DB
         * /DELETE/"GUID" - Deletes the GUID from local DB
         * /UPDATE/"GUID" => "name" - Updates the name of the camera owner in local DB.
         *
         */

    }
}
