package ee.defendec.challenge.shortidchecker.API;

import ee.defendec.challenge.shortidchecker.datasyncing.SyncWorker;
import ee.defendec.challenge.shortidchecker.devices.Camera;
import ee.defendec.challenge.shortidchecker.exceptions.BadGUIDException;
import ee.defendec.challenge.shortidchecker.exceptions.ShortIDException;
import ee.defendec.challenge.shortidchecker.tools.ShortIDChecker;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class API extends HttpServlet {

    public HashMap<String, Camera> getLocalDBDevicesMap() {
        return SyncWorker.getLocalDBDevicesMap();
    }

    public boolean updateCameraName(String shortID, String newName) {
        if (getLocalDBDevicesMap().containsKey(shortID)) {
            getLocalDBDevicesMap().get(shortID).updateCustomerName(newName);
            return true;
        }
        return false;
    }

    private boolean checkGUIDLength(String GUID) throws BadGUIDException {
        if (GUID.length() != 16) {
            throw new BadGUIDException(GUID);
        }
        return true;
    }

    public void deleteDeviceFromLocalDB(String GUID) {
        SyncWorker.getLocalDBDevicesMap().remove(GUID);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String GUID = request.getParameter("device");
        if (SyncWorker.getLocalDBDevicesMap().containsKey(GUID)) {
            response.getOutputStream().println(SyncWorker.getLocalDBDevicesMap().get(GUID).getPostMessage());
        } else {
            response.getOutputStream().println("{}");
        }
    }

    /**
     *
     * @param GUID
     * @return
     */
    public void doGetForTestingInternal(String GUID) {
        try {
            checkGUIDLength(GUID);
            if (SyncWorker.getLocalDBDevicesMap().containsKey(GUID)) {
                System.out.println(SyncWorker.getLocalDBDevicesMap().get(GUID).getPostMessage());
            } else {
                System.out.println("{}");
            }
        } catch (BadGUIDException badGUIDMessage) {
            System.out.println(badGUIDMessage);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String GUID = request.getParameter("device");
        // Seems from the test example that we need to add a device even if it already exists in the local DB
        // I will not do this at the moment since it seems redundant.
        if (SyncWorker.getLocalDBDevicesMap().containsKey(GUID)) {
            response.getOutputStream().println(SyncWorker.getLocalDBDevicesMap().get(GUID).getPostMessage());
        } else {
            Camera newCamera = new Camera(GUID);
            try {
                checkGUIDLength(GUID);
                try {
                    ShortIDChecker.checkShortIDsAndUpdateLocalDB(newCamera);
                    getLocalDBDevicesMap().put(newCamera.getGUID(), newCamera);
                    if (!newCamera.getConflictedCameras().isEmpty()) {
                        throw new ShortIDException(GUID, newCamera.getConflictedGUIDsString());
                    }
                    response.getOutputStream().println(newCamera.getPostMessage());
                } catch (ShortIDException message) {
                    response.getOutputStream().println(message.toString());
                }
            } catch (BadGUIDException badGUIDMessage) {
                response.getOutputStream().println(badGUIDMessage.toString());
            }
        }
    }

    /**
     *
     * @param GUID
     */
    public void doPostForTestingInternal(String GUID) {
        if (SyncWorker.getLocalDBDevicesMap().containsKey(GUID)) {
            System.out.println(SyncWorker.getLocalDBDevicesMap().get(GUID).getPostMessage());
        } else {
            Camera newCamera = new Camera(GUID);
            try {
                checkGUIDLength(GUID);
                try {
                    ShortIDChecker.checkShortIDsAndUpdateLocalDB(newCamera);
                    getLocalDBDevicesMap().put(newCamera.getGUID(), newCamera);
                    if (!newCamera.getConflictedCameras().isEmpty()) {
                        throw new ShortIDException(GUID, newCamera.getConflictedGUIDsString());
                    }
                    System.out.println(newCamera.getPostMessage());
                } catch (ShortIDException message) {
                    System.out.println(message);
                }
            } catch (BadGUIDException badGUIDMessage) {
                System.out.println(badGUIDMessage);
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Not requested at this moment
        super.doPut(request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Not requested at this moment
        super.doDelete(request, response);
    }
}
