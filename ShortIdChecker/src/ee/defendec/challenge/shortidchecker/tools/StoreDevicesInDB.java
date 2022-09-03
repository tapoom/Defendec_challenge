package ee.defendec.challenge.shortidchecker.tools;

import ee.defendec.challenge.shortidchecker.devices.Camera;
import ee.defendec.challenge.shortidchecker.exceptions.FileWriterException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StoreDevicesInDB {

    private static List<String> listOfDeviceData = new ArrayList<>();

    public static boolean storeDevices(HashMap<String, Camera> mapOfDevices, String filename) {
        for (String shortID : mapOfDevices.keySet()) {
            listOfDeviceData.add(mapOfDevices.get(shortID).toString());
        }
        return writeLinesToFile(listOfDeviceData, filename);
    }

    /**
     * Method  gets a List of lines to write to the file.
     * File name can contain the location of the file or not.
     * Return true if writing is successful.
     * @param lines
     * @param filename
     * @return
     */
    private static boolean writeLinesToFile(List<String> lines, String filename) {
        try {
            if (filename != null) {
                new File(filename);
                FileWriter myWriter = new FileWriter(filename);
                for (String line : lines) {
                    myWriter.write(line + "\n");
                }
                myWriter.close();
                return true;
            }
        } catch (IOException e) {
            throw new FileWriterException("No such file", e);
        }
        return false;
    }
}
