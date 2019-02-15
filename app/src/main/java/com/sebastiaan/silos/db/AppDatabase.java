package com.sebastiaan.silos.db;

import android.content.Context;

import com.sebastiaan.silos.db.entities.barcode;
import com.sebastiaan.silos.db.entities.product;
import com.sebastiaan.silos.db.entities.supplier;
import com.sebastiaan.silos.db.entities.supplier_product;
import com.sebastiaan.silos.db.interfaces.barcodeInterface;
import com.sebastiaan.silos.db.interfaces.productInterface;
import com.sebastiaan.silos.db.interfaces.supplierInterface;
import com.sebastiaan.silos.db.interfaces.supplier_productInterface;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


// Handigheden:
// https://developer.android.com/training/data-storage/room/accessing-data
// https://stackoverflow.com/questions/48123507/android-room-how-to-read-from-two-tables-at-the-same-time-duplicate-id-column?rq=1
// https://stackoverflow.com/questions/45059942/return-type-for-android-room-joins
@Database(entities = {product.class, supplier.class, supplier_product.class, barcode.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;
    private static final String DB_NAME = "app.db";

    public abstract productInterface productDao();
    public abstract supplierInterface supplierDao();
    public abstract supplier_productInterface supplier_productDao();
    public abstract barcodeInterface barcodeDao();

//    public <T extends DbEntity<T>> DbInterface<T> getInterface() {
//
//    }

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DB_NAME).build();
                }
            }
        }

        return INSTANCE;
    }
}