package ee.defendec.challenge.shortidchecker.devices;

import ee.defendec.challenge.shortidchecker.tools.ShortIDGenerator;

import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Camera {

    private String customerName;
    private String deviceGUID;
    private Date lastModified;

    /**
     * Create a camera object.
     * @param customerName - name of the customer.
     * @param deviceGUID - unique device ID
     */
    public Camera(String customerName, String deviceGUID) {
        this.customerName = customerName;
        this.deviceGUID = deviceGUID;
        updateLastModified();
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
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(lastModified);
    }

    public void updateLastModified() {
        lastModified = new Date();
    }
}
