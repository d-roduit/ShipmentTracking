package ch.dc.shipment_tracking_app.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import ch.dc.shipment_tracking_app.db.converter.Converters;
import ch.dc.shipment_tracking_app.db.dataAccessObject.ItemDao;
import ch.dc.shipment_tracking_app.db.dataAccessObject.ShipmentDao;
import ch.dc.shipment_tracking_app.db.entity.Item;
import ch.dc.shipment_tracking_app.db.entity.Shipment;

/**
 * Application database.
 */
//@Database(entities = { Item.class, Shipment.class }, version = 1)
//@TypeConverters({Converters.class})
//public abstract class AppDatabase extends RoomDatabase {
//
//    /**
//     * Database name
//     */
//    private static final String DATABASE_NAME = "shipment_tracking_database";
//
//    /**
//     * Database static instance
//     */
//    private static AppDatabase instance;
//
//    /**
//     * Method to build the database. Will be created when it's accessed for the first time.
//     * @param context the App context
//     * @return an instance of the AppDatabase
//     */
//    public static AppDatabase getInstance(Context context) {
//        if (instance == null) {
//            synchronized (AppDatabase.class) {
//                if (instance == null) {
//                    instance = Room.databaseBuilder(context.getApplicationContext(),
//                            AppDatabase.class, DATABASE_NAME)
//                            .fallbackToDestructiveMigration()
//                            .addCallback(roomCallback)
//                            .build();
//                }
//            }
//        }
//        return instance;
//    }
//
//    /**
//     * Callback
//     */
//    private static final RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
//        @Override
//        public void onCreate(@NonNull SupportSQLiteDatabase db) {
//        super.onCreate(db);
//        }
//    };
//
//    /**
//     * Method to get the itemDao
//     * @return the itemDao
//     */
//    public abstract ItemDao itemDao();
//
//    /**
//     * Method to get the ShipmentDao
//     * @return the shipmentDao
//     */
//    public abstract ShipmentDao shipmentDao();

//}