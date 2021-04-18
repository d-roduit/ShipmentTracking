package ch.dc.shipment_tracking_app.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.firebase.database.Exclude;

import java.util.Date;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * Shipment entity that represents the Shipment table.
 */
public class Shipment {

    /**
     * Id
     */
    private String id;

    /**
     * Shipping number
     */
    private int shippingNumber;

    /**
     * Status
     */
    private int status;

    /**
     * Date
     */
    private Date date;

    /**
     * NPA
     */
    private String npa;

    /**
     * City
     */
    private String city;


    /**
     * Shipment constructor
     */
    public Shipment() {
        Date currentDate = new Date();
        setDate(currentDate);
    }

    public Shipment(int shippingNumber, int status, String npa, String city) {
        this.shippingNumber = shippingNumber;
        this.status = status;
        this.npa = npa;
        this.city = city;
    }

    /**
     * Getter for id
     * @return the id
     */
    @Exclude
    public String getId() {
        return id;
    }

    /**
     * Setter for id
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter for shippingNumber
     * @return the shipping number
     */
    public int getShippingNumber() {
        return shippingNumber;
    }

    /**
     * Setter for shippingNumber
     * @param shippingNumber the shipping number
     */
    public void setShippingNumber(int shippingNumber) {
        this.shippingNumber = shippingNumber;
    }

    /**
     * Getter for status
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * Setter for status
     * @param status the status
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Getter for date
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Setter for date
     * @param date the date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Getter for npa
     * @return the npa
     */
    public String getNpa() {
        return npa;
    }

    /**
     * Setter for npa
     * @param npa the npa
     */
    public void setNpa(String npa) {
        this.npa = npa;
    }

    /**
     * Getter for city
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Setter for city
     * @param city the city
     */
    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Shipment{" +
                "id=" + id +
                ", shippingNumber=" + shippingNumber +
                ", status=" + status +
                ", date=" + date +
                ", npa='" + npa + '\'' +
                ", city='" + city + '\'' +
                '}';
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("status", status);
        result.put("npa", npa);
        result.put("city", city);
        result.put("date", date);
        return result;
    }
}
