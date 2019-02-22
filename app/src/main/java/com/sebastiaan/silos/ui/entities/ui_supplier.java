package com.sebastiaan.silos.ui.entities;

import com.sebastiaan.silos.db.entities.supplier;

public class ui_supplier extends UiEntity{
    public String name,
    streetname,
    housenumber,
    city,
    postalcode,
    phonenumber,
    emailaddress,
    website;

    public ui_supplier(String name, String streetname, String housenumber, String city, String postalcode, String phonenumber, String emailaddress, String website) {
        this.name = name;
        this.streetname = streetname;
        this.housenumber = housenumber;
        this.city = city;
        this.postalcode = postalcode;
        this.phonenumber = phonenumber;
        this.emailaddress = emailaddress;
        this.website = website;
    }

    public supplier to_supplier(long supplierID) {
        supplier s = new supplier(name, streetname, housenumber, city, postalcode, phonenumber, emailaddress, website);
        s.setSupplierID(supplierID);
        return s;
    }
}
