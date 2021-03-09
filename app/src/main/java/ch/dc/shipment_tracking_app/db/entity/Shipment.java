package ch.dc.shipment_tracking_app.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
        @ForeignKey(
                entity = Item.class,
                parentColumns = "shipping_number",
                childColumns = "shipping_number",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
        )
}, indices = {
        @Index("shipping_number")
})
public class Shipment {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "shipping_number")
    private int shippingNumber;

    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShippingNumber() {
        return shippingNumber;
    }

    public void setShippingNumber(int shippingNumber) {
        this.shippingNumber = shippingNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
