package com.sebastiaan.silos.db.policy;

import com.sebastiaan.silos.db.async.helper.helperNamed;
import com.sebastiaan.silos.db.entities.product;
import com.sebastiaan.silos.db.policy.interfaces.DbPolicyInterface;
import com.sebastiaan.silos.ui.entities.ui_product;

import androidx.annotation.NonNull;

public class ProductPolicy extends NamedPolicy<product> {
    public ProductPolicy(@NonNull DbPolicyInterface<product> policyInterface, helperNamed<product> helper) {
        super(policyInterface, helper);
    }

    public void insert(ui_product input) {
        insert(new product(input.productname, input.description));
    }
}
