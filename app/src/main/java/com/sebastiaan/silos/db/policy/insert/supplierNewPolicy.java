package com.sebastiaan.silos.db.policy.insert;

import com.sebastiaan.silos.db.async.helper.helperNamed;
import com.sebastiaan.silos.db.async.helper.supplierHelper;
import com.sebastiaan.silos.db.entities.supplier;
import com.sebastiaan.silos.db.policy.DbPolicyInterface;
import com.sebastiaan.silos.ui.entities.ui_supplier;

import androidx.annotation.NonNull;

public class supplierNewPolicy extends newNamedPolicy<supplier> {
    protected supplierHelper helper;

    public supplierNewPolicy(@NonNull DbPolicyInterface<supplier> policyInterface, helperNamed<supplier> helper) {
        super(policyInterface, helper);
    }

    public void insert(ui_supplier input) {
        insert(new supplier(input.name, input.streetname, input.housenumber, input.city, input.postalcode, input.phonenumber, input.emailaddress, input.website));
    }
}
