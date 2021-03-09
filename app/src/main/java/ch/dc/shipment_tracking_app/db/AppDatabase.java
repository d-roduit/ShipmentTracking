package ch.dc.shipment_tracking_app.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ch.dc.shipment_tracking_app.db.dataAccessObject.ItemDao;
import ch.dc.shipment_tracking_app.db.dataAccessObject.ShipmentDao;
import ch.dc.shipment_tracking_app.db.entity.Item;
import ch.dc.shipment_tracking_app.db.entity.Shipment;

@Database(entities = { Item.class, Shipment.class }, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ItemDao itemDao();
    public abstract ShipmentDao shipmentDao();
}