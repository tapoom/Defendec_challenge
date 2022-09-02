package ee.defendec.challenge.shortidchecker.devices;

import ee.defendec.challenge.shortidchecker.tools.ShortIDGenerator;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Camera {

    private static final String DATEFORMAT = "yyyy/MM/dd HH:mm:ss";

    private String customerName;
    private String deviceGUID;
    private Date lastModified;  // What does this mean exactly, is it the last time the data was synced?
    // Do we need to update it every time we sync?

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

    public Camera(String deviceGUID, String customerName, String lastModified) {
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

    public String getDeviceGUID() {
        return deviceGUID;
    }

    public String getLastModified() {
        DateFormat dateFormat = new SimpleDateFormat(DATEFORMAT);
        return dateFormat.format(lastModified);
    }

    public void updateLastModified() {
        lastModified = new Date();
    }
}
