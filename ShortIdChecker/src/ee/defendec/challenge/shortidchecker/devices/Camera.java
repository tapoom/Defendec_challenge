package ee.defendec.challenge.shortidchecker.devices;

import ee.defendec.challenge.shortidchecker.setup.GeneralSetup;
import ee.defendec.challenge.shortidchecker.tools.ShortIDGenerator;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class Camera {

    private static final String DATEFORMAT = GeneralSetup.DATEFORMAT;

    private String customerName;
    private String deviceGUID;
    private Date lastModified;
    private boolean inExternalDB;
    private List<Camera> conflictedCameras = new ArrayList<>();

    /**
     * Create a camera object.
     * @param deviceGUID - unique device ID
     */
    public Camera(String deviceGUID) {
        this.deviceGUID = deviceGUID;
        this.customerName = "";
        updateLastModified();
    }

    public Camera( String deviceGUID, String customerName) {
        this.customerName = customerName;
        this.deviceGUID = deviceGUID;
        updateLastModified();

    }

    public Camera(String deviceGUID, String lastModified, String customerName) {
        this.customerName = customerName;
        this.deviceGUID = deviceGUID;
        try {
            this.lastModified = new SimpleDateFormat(DATEFORMAT).parse(lastModified);
        } catch(ParseException exception) {
            System.out.println(exception);
            System.out.println("Bad date, will write current time as last modified time stamp.");
            updateLastModified();

        }
    }

    public String getShortID() {
        return ShortIDGenerator.generate(deviceGUID);
    }

    public String getCustomerName() {
        return customerName;
    }

    public void updateCustomerName(String newCustomerName) {
        customerName = newCustomerName;
        updateLastModified();

    }

    public String getGUID() {
        return deviceGUID;
    }

    public String getLastModifiedString() {
        if (lastModified == null) {
            return "";
        }
        return lastModified.toString();
    }

    public Date getLastModifiedDateTime() {
        return lastModified;
    }

    public void updateLastModified() {
        lastModified = new Date();
    }

    public void setInExternalDB() {
        inExternalDB = true;
    }

    @Override
    public String toString() {
        return "[{\n" +
                "'guid': '" + deviceGUID + "',\n" +
                "'conflictsWithGUIDs': " + "[\n" + getConflictedGUIDsString() + "\n" +
                "'seen': '" + getLastModifiedString() + "',\n" +
                "'customer': '" + customerName + "',\n" +
                "'external': " + inExternalDB + ",\n";
    }


    public String getConflictedGUIDsString() {
        String conflictedGUIDsString = "";
        for (Camera conflictedCamera : conflictedCameras) {
            conflictedGUIDsString = conflictedGUIDsString.concat(conflictedCamera.getPostWithoutConflicts() + "\n");
        }
        return conflictedGUIDsString.trim() + "\n],";
    }

    public String getStoringStringForDB() {
        return deviceGUID + ", " + lastModified + ", " + customerName;
    }

    public String getPostWithoutConflicts() {
        return  "'guid': '" + deviceGUID + "'," +
                "'seen': '" + getLastModifiedString() + "'," + "'customer': '" +
                customerName + "'," + "'external': " + inExternalDB + ",";
    }

    public void addConflictedGUID(Camera conflictedCamera) {
        conflictedCameras.add(conflictedCamera);
    }

    public List<Camera> getConflictedCameras() {
        return conflictedCameras;
    }


}
