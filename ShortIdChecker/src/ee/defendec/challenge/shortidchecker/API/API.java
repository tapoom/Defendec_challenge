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
import java.util.Scanner;

public class API extends HttpServlet {

    /**
     * /POST/GUID - Post the GUID into the local DB
     * /GET/GUID - Get the GUID info in local DB
     * /DELETE/GUID - Deletes the GUID from local DB
     * /UPDATE/GUID => "name" - Updates the name of the camera owner in local DB.
     *
     */
    public void runConsoleInterface() {
        boolean running = true;
        while (running) {
            System.out.println("Command: ");
            Scanner scanner = new Scanner(System.in);
            String inputString = scanner.nextLine();
            if (inputString.startsWith("/POST/")) {
                String GUID = inputString.replaceFirst("/POST/", "");
                if (GUID.isBlank()) {
                    System.out.println("Enter GUID: ");
                    GUID = scanner.nextLine();
                }
                doPostForTestingInternal(GUID);
            } else if (inputString.startsWith("/GET/")) {
                String GUID = inputString.replaceFirst("/GET/", "");
                doGetForTestingInternal(GUID);
            } else if (inputString.startsWith("/DELETE/")) {
                String GUID = inputString.replaceFirst("/DELETE/", "");
                if (GUID.isBlank()) {
                    System.out.println("Enter GUID: ");
                    GUID = scanner.nextLine();
                }
                deleteDeviceFromLocalDB(GUID);
                doGetForTestingInternal(GUID);
            } else if (inputString.startsWith("/UPDATE/")) {
                String GUID = inputString.replaceFirst("/UPDATE/", "");
                if (GUID.isBlank()) {
                    System.out.println("Enter GUID: ");
                    GUID = scanner.nextLine();
                }
                System.out.println("Enter new name: ");
                String name = scanner.nextLine();
                updateCameraName(GUID, name);
                doGetForTestingInternal(GUID);
            }
        }
    }


    public HashMap<String, Camera> getLocalDBDevicesMap() {
        return SyncWorker.getLocalDBDevicesMap();
    }

    public boolean updateCameraName(String GUID, String newName) {
        try {
            checkGUIDLength(GUID);
            if (getLocalDBDevicesMap().containsKey(GUID)) {
                getLocalDBDevicesMap().get(GUID).updateCustomerName(newName);
                return true;
            }
        } catch (BadGUIDException badGUIDMessage) {
            System.out.println(badGUIDMessage);
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
        try {
            checkGUIDLength(GUID);
            if (SyncWorker.getLocalDBDevicesMap().containsKey(GUID)) {
                response.getOutputStream().println(SyncWorker.getLocalDBDevicesMap().get(GUID).getPostMessage());
            } else {
                response.getOutputStream().println("{}");
            }
        } catch (BadGUIDException badGUIDMessage) {
            response.getOutputStream().println(badGUIDMessage.toString());
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
        try {
            checkGUIDLength(GUID);
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
        } catch (BadGUIDException badGUIDMessage) {
            response.getOutputStream().println(badGUIDMessage.toString());
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
