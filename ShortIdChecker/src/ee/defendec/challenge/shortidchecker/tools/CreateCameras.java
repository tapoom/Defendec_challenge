package ee.defendec.challenge.shortidchecker.tools;

import ee.defendec.challenge.shortidchecker.devices.Camera;
import ee.defendec.challenge.shortidchecker.setup.GeneralSetup;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CreateCameras {

    private static final String DATEFORMAT = GeneralSetup.DATEFORMAT;
    private List<Camera> listOfCameras = new ArrayList<>();
    private String lastModified;
    private DateFormat dateFormat = new SimpleDateFormat(DATEFORMAT);
    private String blankName;

    public List<Camera> MakeCameras(List<List<String>> dataFromDB) {

        for (List<String> data : dataFromDB) {
            // Data in database always has the ID, so we can assume that if we have only one element stored,
            // it will be the GUID. The second element should be the name, but the name is optional.
            // We need to check element 2 if it is a name or a time. If the format fits the required datetime format,
            // then we know it is not the name.

            // Since we create the data, we know that the data is in sequence GUID -> Name -> DateTime
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
            return listOfCameras;
        }
        return null;
    }
}
