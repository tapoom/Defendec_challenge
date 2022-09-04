package ee.defendec.challenge.shortidchecker.devices;

import ee.defendec.challenge.shortidchecker.setup.GeneralSetup;
import ee.defendec.challenge.shortidchecker.tools.ShortIDGenerator;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Camera {

    private static final String DATEFORMAT = GeneralSetup.DATEFORMAT;

    private String customerName;
    private String deviceGUID;
    private Date lastModified;
    private boolean inExternalDB;

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

    public String getDeviceGUID() {
        return deviceGUID;
    }

    public String getLastModifiedString() {
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
        return deviceGUID + ", " + lastModified + ", " + customerName;
    }

    public String getStringData() {
        return "[{\n" +
                "\'guid\': '" + deviceGUID + "',\n" +
                "\'seen\': '" + getLastModifiedString() + "',\n" +
                "\'customer\': '" + customerName + "',\n" +
                "\'external\': " + inExternalDB + ",\n" +
                "],";
    }
}
