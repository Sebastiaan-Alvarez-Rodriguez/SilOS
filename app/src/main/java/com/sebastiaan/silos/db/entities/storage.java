package com.sebastiaan.silos.db.entities;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.sebastiaan.silos.db.AppDatabase;
import com.sebastiaan.silos.db.interfaces.DbIDInterface;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import static androidx.room.ForeignKey.CASCADE;

@Entity(indices = {@Index(value = {"productID"}, unique = true)})
public class storage extends DbEntity<storage> implements Parcelable {

    @ForeignKey(entity = product.class, parentColumns = "productID", childColumns = "supplierID", onDelete = CASCADE)
    private long productID;

    private long quantity;


    public long getProductID() {
        return productID;
    }

    public void setProductID(long productID) {
        this.productID = productID;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public storage(long productID, long quantity) {
        this.productID = productID;
        this.quantity = quantity;
    }

    private storage(Parcel in) {
        id = in.readLong();
        productID = in.readLong();
        quantity = in.readLong();
    }

    public static final Creator<storage> CREATOR = new Creator<storage>() {
        @Override
        public storage createFromParcel(Parcel in) { return new storage(in); }

        @Override
        public storage[] newArray(int size) { return new storage[size]; }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(quantity);
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public DbIDInterface<storage> getInterface(Context context) {
        return AppDatabase.getDatabase(context).storageDao();
    }

}
