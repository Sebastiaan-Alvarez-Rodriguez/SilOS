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

import java.io.Serializable;

@Entity(indices = {@Index(value = {"name"}, unique = true)})
public class supplier extends DbEntity<supplier> implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private long supplierID;
    private String name, streetname, housenumber, city, postalcode, phonenumber, emailaddress, website;

    public supplier(String name, String streetname, String housenumber, String city, String postalcode, String phonenumber, String emailaddress, String website) {
        this.name = name;
        this.streetname = streetname;
        this.housenumber = housenumber;
        this.city = city;
        this.postalcode = postalcode;
        this.phonenumber = phonenumber;
        this.emailaddress = emailaddress;
        this.website = website;
    }

    protected supplier(Parcel in) {
        supplierID = in.readLong();
        name = in.readString();
        streetname = in.readString();
        housenumber = in.readString();
        city = in.readString();
        postalcode = in.readString();
        phonenumber = in.readString();
        emailaddress = in.readString();
        website = in.readString();
    }

    public static final Creator<supplier> CREATOR = new Creator<supplier>() {
        @Override
        public supplier createFromParcel(Parcel in) {
            return new supplier(in);
        }

        @Override
        public supplier[] newArray(int size) {
            return new supplier[size];
        }
    };

    public long getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(long supplierID) {
        this.supplierID = supplierID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreetname() {
        return streetname;
    }

    public void setStreetname(String streetname) {
        this.streetname = streetname;
    }

    public String getHousenumber() {
        return housenumber;
    }

    public void setHousenumber(String housenumber) {
        this.housenumber = housenumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmailaddress() {
        return emailaddress;
    }

    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof supplier)) {
            return false;
        }
        supplier other = (supplier) obj;
        return this.supplierID == other.supplierID;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(supplierID);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(supplierID);
        dest.writeString(name);
        dest.writeString(streetname);
        dest.writeString(housenumber);
        dest.writeString(city);
        dest.writeString(postalcode);
        dest.writeString(phonenumber);
        dest.writeString(emailaddress);
        dest.writeString(website);
    }

    @Override
    public DbInterface<supplier> getInterface(Context context) {
        return AppDatabase.getDatabase(context).supplierDao();
    }
}