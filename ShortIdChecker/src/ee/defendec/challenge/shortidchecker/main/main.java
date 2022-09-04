package ee.defendec.challenge.shortidchecker.main;

import ee.defendec.challenge.shortidchecker.API.API;
import ee.defendec.challenge.shortidchecker.datasyncing.SyncWorker;
import ee.defendec.challenge.shortidchecker.exceptions.ShortIDException;

import java.text.ParseException;


public class main {
    public static void main(String[] args) throws ParseException, ShortIDException {

        SyncWorker.INSTANCE.start();
        API api = new API();

        api.addCamera("4324234234323456");
        api.addCamera("4324234234323457");

        api.addCamera("EAD5E2649FCB779A");

        api.addCamera("4324234234323458");

        System.out.println(api.getLocalDBDevicesMap().get("4324234234323458"));
        /*
        api.addCamera("4324234234323459");
        api.addCamera("EAD5E2649FCB779A");
        System.out.println(api.getLocalDBDevicesMap().get("EAD5E2649FCB779A"));
        api.run();

         */


        /*
        for (String shortID : api.getLocalDBDevicesMap().keySet()) {
            System.out.println(api.getLocalDBDevicesMap().get(shortID).getStringData());
        }
        // EAD5E2649FCB779A,Thu Sep 01 13:56:37 EEST 2022,G4S site Alpha
        SyncWorker.update();
        for (String shortID : api.getLocalDBDevicesMap().keySet()) {
            System.out.println(api.getLocalDBDevicesMap().get(shortID).getStringData());
        }

        for (String shortID : SyncWorker.getLocalDBDevicesMap().keySet()) {
            System.out.println(SyncWorker.getExternalDBMap().get(shortID).getStringData());
        }

        api.updateCameraName("2423", "Tanel");


        System.out.println(api.getLocalDBDevicesMap().get("2423").getStringData());

        System.out.println(SyncWorker.getExternalDBMap().get("2423").getStringData());

        SyncWorker.update();

        System.out.println(SyncWorker.getExternalDBMap().get("2423").getStringData());

        System.out.println("Print out external DB map: " + SyncWorker.getExternalDBMap());

        for (String shortID : SyncWorker.getExternalDBMap().keySet()) {
            System.out.println(SyncWorker.getExternalDBMap().get(shortID).getStringData());
        }

         */






/*
        Date date = new Date();

        System.out.println("Stored date: " + date);
        String stringDate = date.toString();
        System.out.println("Stored date to String: " + stringDate);
        SimpleDateFormat formatter =  new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
        Date formattedDate = formatter.parse(stringDate);
        System.out.println("formatted date: " + formattedDate);
        //DateFormat dateFormat = new SimpleDateFormat(GeneralSetup.DATEFORMAT);
        //System.out.println(new SimpleDateFormat(GeneralSetup.DATEFORMAT).parse(new Date().toString()));

         */
    }
}
