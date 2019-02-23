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

    /**
     * Determine if barcode X has a conflict.
     * It has a conflict, if there is a barcode Y such that x.id != y.id and x.barcodeString == y.barcodeString
     * @param entity Entity to check
     * @param result confilictCallback. Gets conflicting entity if any, null otherwise
     */
    @Override
    public void determineConflict(barcode entity, DbPolicyConflictInterface<barcode> result) {
        helper.find(entity.getBarcodeString(), conflictEntity -> {
            result.onConflict(conflictEntity.getId() == entity.getId() ? null : conflictEntity);
        });
    }
}
