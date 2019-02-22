package com.sebastiaan.silos.db.policy.insert;

import com.sebastiaan.silos.db.async.helper.barcodeHelper;
import com.sebastiaan.silos.db.entities.barcode;
import com.sebastiaan.silos.db.policy.DbPolicyConflictInterface;
import com.sebastiaan.silos.db.policy.DbPolicyInterface;
import com.sebastiaan.silos.ui.entities.ui_barcode;

public class barcodeNewPolicy extends newPolicy<barcode> {
    public barcodeHelper helper;

    public barcodeNewPolicy(DbPolicyInterface<barcode> policyInterface, barcodeHelper helper) {
        super(policyInterface, helper);
    }

    public void insert(ui_barcode input, long productID) {
        insert(new barcode(input.barcodeString, productID, input.amount));
    }

    @Override
    public void determineConflict(barcode entity, DbPolicyConflictInterface<barcode> result) {
        helper.find(entity.getBarcodeString(), result::onConflict);
    }
}
