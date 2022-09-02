package ee.defendec.challenge.shortidchecker.tools;

import ee.defendec.challenge.shortidchecker.devices.Camera;

import java.util.ArrayList;
import java.util.List;

public class CreateCameras {

    List<Camera> listOfCameras = new ArrayList<>();

    public List<Camera> MakeCameras(List<List<String>> dataFromDB) {
        for (List<String> data : dataFromDB) {
            if (data.size() == 1) {
                listOfCameras.add(new Camera(data.get(0)));
            } else if (data.size() == 2) {
                // ToDo
            } else if (data.size() == 3) {
                // ToDo
            }

            return listOfCameras;
        }
        return null;
    }
}
