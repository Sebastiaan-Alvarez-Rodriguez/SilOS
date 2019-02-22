package com.sebastiaan.silos.db.entities;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.sebastiaan.silos.db.AppDatabase;
import com.sebastiaan.silos.db.interfaces.DbIDInterface;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;


/**
 * representation class for table 'supplier_product'
 *
 * Foreign keys are set up such that:
 * - If a supplier is deleted, all supplier-product links with given supplier are deleted, too
 * - If a product is deleted, all supplier-product links with given product are deleted, too
 */
@Entity(indices = {@Index(value = {"supplierID", "productID"}, unique = true)})
public class supplier_product extends DbEntity<supplier_product> implements Parcelable {
    @PrimaryKey
    private long supplier_productID;

    @ForeignKey(entity = supplier.class, parentColumns = "supplierID", childColumns = "supplierID",  onDelete = CASCADE)
    private long supplierID;
    @ForeignKey(entity = product.class, parentColumns = "productID", childColumns = "productID", onDelete = CASCADE)
    private long productID;

    public supplier_product(long supplierID, long productID) {
        this.supplierID = supplierID;
        this.productID = productID;
    }

    private supplier_product(Parcel in) {
        supplierID = in.readLong();
        productID = in.readLong();
    }

    public static final Creator<supplier_product> CREATOR = new Creator<supplier_product>() {
        @Override
        public supplier_product createFromParcel(Parcel in) {
            return new supplier_product(in);
        }

        @Override
        public supplier_product[] newArray(int size) {
            return new supplier_product[size];
        }
    };

    public long getSupplier_productID() {
        return supplier_productID;
    }

    public void setSupplier_productID(long supplier_productID) {
        this.supplier_productID = supplier_productID;
    }

    public long getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(long supplierID) {
        this.supplierID = supplierID;
    }

    public long getProductID() {
        return productID;
    }

    public void setProductID(long productID) {
        this.productID = productID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(supplierID);
        dest.writeLong(productID);
    }

    @Override
    public DbIDInterface<supplier_product> getInterface(Context context) {
        return AppDatabase.getDatabase(context).supplier_productDao();
    }
}
