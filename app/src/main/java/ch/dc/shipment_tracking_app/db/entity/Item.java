package ch.dc.shipment_tracking_app.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "items", indices = {@Index(value = "shipping_number", unique = true)})
public class Item {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "shipping_number")
    private int shippingNumber;

    @ColumnInfo(name = "shipping_priority")
    private char shippingPriority;

    private double weight;

    @ColumnInfo(name = "sender_firstname")
    private String senderFirstname;

    @ColumnInfo(name = "sender_lastname")
    private String senderLastname;

    @ColumnInfo(name = "sender_address")
    private String senderAddress;

    @ColumnInfo(name = "sender_npa")
    private String senderNpa;

    @ColumnInfo(name = "sender_city")
    private String senderCity;

    @ColumnInfo(name = "recipient_firstname")
    private String recipientFirstname;

    @ColumnInfo(name = "recipient_lastname")
    private String recipientLastname;

    @ColumnInfo(name = "recipient_address")
    private String recipientAddress;

    @ColumnInfo(name = "recipient_npa")
    private String recipientNPA;

    @ColumnInfo(name = "recipient_city")
    private String recipientCity;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getShippingNumber() { return shippingNumber; }
    public void setShippingNumber(int shippingNumber) { this.shippingNumber = shippingNumber; }

    public char getShippingPriority() { return shippingPriority; }
    public void setShippingPriority(char shippingPriority) { this.shippingPriority = shippingPriority; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    public String getSenderFirstname() { return senderFirstname; }
    public void setSenderFirstname(String senderFirstname) { this.senderFirstname = senderFirstname; }

    public String getSenderLastname() { return senderLastname; }
    public void setSenderLastname(String senderLastname) { this.senderLastname = senderLastname; }

    public String getSenderAddress() { return senderAddress; }
    public void setSenderAddress(String senderAddress) { this.senderAddress = senderAddress; }

    public String getSenderNpa() { return senderNpa; }
    public void setSenderNpa(String senderNpa) { this.senderNpa = senderNpa; }

    public String getSenderCity() { return senderCity; }
    public void setSenderCity(String senderCity) { this.senderCity = senderCity; }

    public String getRecipientFirstname() { return recipientFirstname; }
    public void setRecipientFirstname(String recipientFirstname) { this.recipientFirstname = recipientFirstname; }

    public String getRecipientLastname() { return recipientLastname; }
    public void setRecipientLastname(String recipientLastname) { this.recipientLastname = recipientLastname; }

    public String getRecipientAddress() { return recipientAddress; }
    public void setRecipientAddress(String recipientAddress) { this.recipientAddress = recipientAddress; }

    public String getRecipientNPA() { return recipientNPA; }
    public void setRecipientNPA(String recipientNPA) { this.recipientNPA = recipientNPA; }

    public String getRecipientCity() { return recipientCity; }
    public void setRecipientCity(String recipientCity) { this.recipientCity = recipientCity; }
}
