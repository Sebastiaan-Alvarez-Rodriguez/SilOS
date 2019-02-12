package com.sebastiaan.silos.db.entities;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.sebastiaan.silos.db.AppDatabase;
import com.sebastiaan.silos.db.interfaces.DbInterface;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity
public class barcode extends DbEntity <barcode> implements Parcelable {
    @PrimaryKey
    @NonNull
    private String barcodeString;
    @ForeignKey(entity = product.class, parentColumns = "productID", childColumns = "productID", onDelete = CASCADE)
    private long productID;

    private int amount;

    public barcode(@NonNull String barcodeString, int amount) {
        this.barcodeString = barcodeString;
        this.amount = amount;
    }

    protected barcode(Parcel in) {
        barcodeString = Objects.requireNonNull(in.readString());
        productID = in.readLong();
        amount = in.readInt();
    }

    public static final Creator<barcode> CREATOR = new Creator<barcode>() {
        @Override
        public barcode createFromParcel(Parcel in) {
            return new barcode(in);
        }

        @Override
        public barcode[] newArray(int size) {
            return new barcode[size];
        }
    };

    public long getProductID() {
        return productID;
    }

    public void setProductID(long productID) {
        this.productID = productID;
    }

    @NonNull
    public String getBarcodeString() {
        return barcodeString;
    }

    public void setBarcodeString(@NonNull String barcodeString) {
        this.barcodeString = barcodeString;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public DbInterface<barcode> getInterface(Context context) {
        return AppDatabase.getDatabase(context).barcodeDao();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(barcodeString);
        dest.writeInt(amount);
    }
}
