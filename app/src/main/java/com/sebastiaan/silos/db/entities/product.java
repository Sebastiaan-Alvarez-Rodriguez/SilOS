package com.sebastiaan.silos.db.entities;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.sebastiaan.silos.db.AppDatabase;
import com.sebastiaan.silos.db.interfaces.DbInterface;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"productName"}, unique = true)})
public class product extends DbEntity<product> implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private long productID;
    private String productName, productDescription;

    public product(String productName, String productDescription) {
        this.productName = productName;
        this.productDescription = productDescription;
    }

    protected product(Parcel in) {
        productID = in.readLong();
        productName = in.readString();
        productDescription = in.readString();
    }

    public static final Creator<product> CREATOR = new Creator<product>() {
        @Override
        public product createFromParcel(Parcel in) {
            return new product(in);
        }

        @Override
        public product[] newArray(int size) {
            return new product[size];
        }
    };

    public long getProductID() {
        return productID;
    }

    public void setProductID(long productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof product)) {
            return false;
        }
        product other = (product) obj;
        return this.productID == other.productID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(productID);
        dest.writeString(productName);
        dest.writeString(productDescription);
    }

    @Override
    public DbInterface<product> getInterface(Context context) {
        return AppDatabase.getDatabase(context).productDao();
    }
}
