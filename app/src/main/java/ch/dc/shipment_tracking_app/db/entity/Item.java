package ch.dc.shipment_tracking_app.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Random;

/**
 * Item entity that represents the Item table. The Item contains all the package information.
 */
@Entity(tableName = "items", indices = {@Index(value = "shipping_number", unique = true)})
public class Item {

    /**
     * Id
     */
    @PrimaryKey(autoGenerate = true)
    private int id;

    /**
     * Shipping number
     */
    @ColumnInfo(name = "shipping_number")
    private int shippingNumber;

    /**
     * Shipping priority
     */
    @ColumnInfo(name = "shipping_priority")
    private char shippingPriority;

    /**
     * Weight
     */
    private double weight;

    /**
     * Sender firstname
     */
    @ColumnInfo(name = "sender_firstname")
    private String senderFirstname;

    /**
     * Sender lastname
     */
    @ColumnInfo(name = "sender_lastname")
    private String senderLastname;

    /**
     * Sender address
     */
    @ColumnInfo(name = "sender_address")
    private String senderAddress;

    /**
     * Sender NPA
     */
    @ColumnInfo(name = "sender_npa")
    private String senderNpa;

    /**
     * Sender city
     */
    @ColumnInfo(name = "sender_city")
    private String senderCity;

    /**
     * Recipient firstname
     */
    @ColumnInfo(name = "recipient_firstname")
    private String recipientFirstname;

    /**
     * Recipient lastname
     */
    @ColumnInfo(name = "recipient_lastname")
    private String recipientLastname;

    /**
     * Recipient address
     */
    @ColumnInfo(name = "recipient_address")
    private String recipientAddress;

    /**
     * Recipient NPA
     */
    @ColumnInfo(name = "recipient_npa")
    private String recipientNPA;

    /**
     * Recipient city
     */
    @ColumnInfo(name = "recipient_city")
    private String recipientCity;


    /**
     * Item constructor
     *
     * @param shippingPriority the shipping priority
     * @param weight the weight
     * @param senderFirstname the sender firstname
     * @param senderLastname the sender lastname
     * @param senderAddress the sender address
     * @param senderNpa the sender npa
     * @param senderCity the sender city
     * @param recipientFirstname the recipient firstname
     * @param recipientLastname the recipient lastname
     * @param recipientAddress the recipient address
     * @param recipientNPA the recipient npa
     * @param recipientCity the recipient city
     */
    public Item(char shippingPriority, double weight, String senderFirstname,
                String senderLastname, String senderAddress, String senderNpa,
                String senderCity, String recipientFirstname, String recipientLastname,
                String recipientAddress, String recipientNPA, String recipientCity) {
        this.shippingPriority = shippingPriority;
        this.weight = weight;
        this.senderFirstname = senderFirstname;
        this.senderLastname = senderLastname;
        this.senderAddress = senderAddress;
        this.senderNpa = senderNpa;
        this.senderCity = senderCity;
        this.recipientFirstname = recipientFirstname;
        this.recipientLastname = recipientLastname;
        this.recipientAddress = recipientAddress;
        this.recipientNPA = recipientNPA;
        this.recipientCity = recipientCity;

        setShippingNumber(generateUniqueShippingNumber());
    }

    /**
     * Method to generate a unique random number of 7 digits for the shipping number.
     *
     * @return a 7 digits int
     */
    private int generateUniqueShippingNumber() {
        Random random = new Random();
        int randomNumber = 1000 + random.nextInt(9000);

        long currentTime = System.currentTimeMillis();
        int lastThreeDigits = (int) (currentTime % 1000);

        String string = Integer.toString(randomNumber) + lastThreeDigits;

        return Integer.parseInt(string);
    }

    /**
     * Getter for id
     * @return id
     */
    public int getId() { return id; }

    /**
     * Setter for id
     * @param id the item id
     */
    public void setId(int id) { this.id = id; }

    /**
     * Getter for shippingNumber
     * @return the shipping number
     */
    public int getShippingNumber() { return shippingNumber; }

    /**
     * Setter for shippingNumber
     * @param shippingNumber the shipping number
     */
    public void setShippingNumber(int shippingNumber) { this.shippingNumber = shippingNumber; }

    /**
     * Getter for shippingPriority
     * @return the shipping priority
     */
    public char getShippingPriority() { return shippingPriority; }

    /**
     * Setter for shippingPriority
     * @param shippingPriority the shipping priority
     */
    public void setShippingPriority(char shippingPriority) { this.shippingPriority = shippingPriority; }

    /**
     * Getter for weight
     * @return the weight
     */
    public double getWeight() { return weight; }

    /**
     * Setter for weight
     * @param weight the weight
     */
    public void setWeight(double weight) { this.weight = weight; }

    /**
     * Getter for senderFirstname
     * @return the sender firstname
     */
    public String getSenderFirstname() { return senderFirstname; }

    /**
     * Setter for senderFirstname
     * @param senderFirstname the sender firstname
     */
    public void setSenderFirstname(String senderFirstname) { this.senderFirstname = senderFirstname; }

    /**
     * Getter for senderLastname
     * @return the sender lastname
     */
    public String getSenderLastname() { return senderLastname; }

    /**
     * Setter for senderLastname
     * @param senderLastname the sender lastname
     */
    public void setSenderLastname(String senderLastname) { this.senderLastname = senderLastname; }

    /**
     * Getter for senderAddress
     * @return the sender address
     */
    public String getSenderAddress() { return senderAddress; }

    /**
     * Setter for senderAddress
     * @param senderAddress the sender address
     */
    public void setSenderAddress(String senderAddress) { this.senderAddress = senderAddress; }

    /**
     * Getter for senderNpa
     * @return the sender npa
     */
    public String getSenderNpa() { return senderNpa; }

    /**
     * Setter for senderNpa
     * @param senderNpa the sender npa
     */
    public void setSenderNpa(String senderNpa) { this.senderNpa = senderNpa; }

    /**
     * Getter for senderCity
     * @return the sender city
     */
    public String getSenderCity() { return senderCity; }
    /**
     * Setter for senderCity
     * @param senderCity the sender city
     */
    public void setSenderCity(String senderCity) { this.senderCity = senderCity; }

    /**
     * Getter for recipientFirstname
     * @return the recipient firstname
     */
    public String getRecipientFirstname() { return recipientFirstname; }

    /**
     * Setter for recipientFirstname
     * @param recipientFirstname the recipient firstname
     */
    public void setRecipientFirstname(String recipientFirstname) { this.recipientFirstname = recipientFirstname; }

    /**
     * Getter for recipientLastname
     * @return the recipient lastname
     */
    public String getRecipientLastname() { return recipientLastname; }

    /**
     * Setter for recipientLastname
     * @param recipientLastname the recipient lastname
     */
    public void setRecipientLastname(String recipientLastname) { this.recipientLastname = recipientLastname; }

    /**
     * Getter for recipientAddress
     * @return the recipient address
     */
    public String getRecipientAddress() { return recipientAddress; }

    /**
     * Setter for recipientAddress
     * @param recipientAddress the recipient address
     */
    public void setRecipientAddress(String recipientAddress) { this.recipientAddress = recipientAddress; }

    /**
     * Getter for recipientNpa
     * @return the recipient npa
     */
    public String getRecipientNPA() { return recipientNPA; }

    /**
     * Setter for recipientNpa
     * @param recipientNPA the recipient npa
     */
    public void setRecipientNPA(String recipientNPA) { this.recipientNPA = recipientNPA; }

    /**
     * Getter for recipientCity
     * @return the recipient city
     */
    public String getRecipientCity() { return recipientCity; }

    /**
     * Setter for recipientCity
     * @param recipientCity the recipient city
     */
    public void setRecipientCity(String recipientCity) { this.recipientCity = recipientCity; }
}
