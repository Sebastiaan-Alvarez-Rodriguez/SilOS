package com.sebastiaan.silos.ui.entities;

import com.sebastiaan.silos.db.entities.product;

public class ui_product extends UiEntity {
    public String productname, description;

    public ui_product(String productname, String description) {
        this.productname = productname;
        this.description = description;
    }

    public product to_product(int productID) {
        product p = new product(productname, description);
        p.setProductID(productID);
        return p;
    }

    public product to_product(long productID) {
        product p = new product(productname, description);
        p.setProductID(productID);
        return p;
    }
}
