package ee.defendec.challenge.shortidchecker.testing;

import ee.defendec.challenge.shortidchecker.API.API;
import ee.defendec.challenge.shortidchecker.datasyncing.SyncWorker;
import ee.defendec.challenge.shortidchecker.devices.Camera;
import ee.defendec.challenge.shortidchecker.setup.GeneralSetup;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class main {
    public static void main(String[] args) throws ParseException {

        SyncWorker.update();
        API api = new API();
        System.out.println(api.getLocalDBDevicesMap());
        for (String shortID: api.getLocalDBDevicesMap().keySet()) {
            Camera camera = api.getLocalDBDevicesMap().get(shortID);
            System.out.println("Get guid: " + camera.getDeviceGUID());
            System.out.println("Get toString: " + camera.toString());
            System.out.println("Get toStringData: " + camera.getStringData());
        }

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
