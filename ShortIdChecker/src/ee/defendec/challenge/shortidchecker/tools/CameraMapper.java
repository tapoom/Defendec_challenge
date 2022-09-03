package ee.defendec.challenge.shortidchecker.tools;

import ee.defendec.challenge.shortidchecker.devices.Camera;
import ee.defendec.challenge.shortidchecker.setup.GeneralSetup;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CameraMapper {

    private static final String DATEFORMAT = GeneralSetup.DATEFORMAT;
    private static List<Camera> listOfCameras = new ArrayList<>();
    private static String lastModified;
    private static DateFormat dateFormat = new SimpleDateFormat(DATEFORMAT);
    private static String blankName;
    private static HashMap<String, Camera> mapOfCameras = new HashMap<>();

    public static HashMap<String, Camera> getCameraMap(String fileLocation) {

        for (List<String> data : GetDevicesFromDB.getDeviceListFromDatabase(fileLocation)) {
            // Data in database always has the ID, so we can assume that if we have only one element stored,
            // it will be the GUID. The second element should be the name, but the name is optional.
            // We need to check element 2 if it is a name or a time. If the format fits the required datetime format,
            // then we know it is not the name.

            // Since we create the data, we know that the data is in sequence GUID -> DateTime -> Name
            // Exception is that either DateTime or Name might not be presented in the data.

            // ToDo Should create a builder for the Camera object.
            if (data.size() == 1) {
                listOfCameras.add(new Camera(data.get(0)));
            } else if (data.size() == 2) {
                try {
                    // Check if second placeholder is DateTime format. If it is then add it as last modified and leave
                    // the name blank.
                    lastModified = dateFormat.format(new SimpleDateFormat(DATEFORMAT).parse(data.get(1)));
                    listOfCameras.add(new Camera(data.get(0), blankName, data.get(1)));
                } catch(ParseException exception) {
                    // Second placeholder was not DateTime format, so we assume it is the name.
                    listOfCameras.add(new Camera(data.get(0), data.get(1)));
                }
            } else if (data.size() == 3) {
                // Data has 3 elements, so we can create the full Camera object.
                listOfCameras.add(new Camera(data.get(0), data.get(1), data.get(2)));
            }
        }
        for (Camera camera : listOfCameras) {
            camera.setInExternalDB();
            mapOfCameras.put(camera.getShortID(), camera);
        }
        return mapOfCameras;
    }
}
