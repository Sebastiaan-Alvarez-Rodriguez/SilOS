package com.sebastiaan.silos.db.policy.insert;

import com.sebastiaan.silos.db.async.helper.barcodeHelper;
import com.sebastiaan.silos.db.entities.barcode;
import com.sebastiaan.silos.db.policy.DbPolicyConflictInterface;
import com.sebastiaan.silos.db.policy.DbPolicyInterface;

public class barcodeNewPolicy extends newPolicy<barcode> {
    public barcodeHelper helper;

    public barcodeNewPolicy(DbPolicyInterface<barcode> policyInterface, barcodeHelper helper) {
        super(policyInterface, helper);
    }

    @Override
    public void insert(barcode entity) {
        helper.find(entity.getBarcodeString(), conflictEntity -> {
            if (conflictEntity == null) {
                helper.insert(entity, resultEntity -> policyInterface.onSuccess(resultEntity));
            } else {
                policyInterface.onConflict(conflictEntity);
            }
        });
    }

    @Override
    public void determineConflict(barcode entity, DbPolicyConflictInterface<barcode> result) {
        helper.find(entity.getBarcodeString(), result::onConflict);
    }
}
