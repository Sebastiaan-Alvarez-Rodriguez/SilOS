package com.sebastiaan.silos.db.policy.insert;

import com.sebastiaan.silos.db.async.helper.helperNamed;
import com.sebastiaan.silos.db.entities.DbEntityNamed;
import com.sebastiaan.silos.db.policy.DbPolicyConflictInterface;
import com.sebastiaan.silos.db.policy.DbPolicyInterface;

import androidx.annotation.NonNull;

public class newNamedPolicy<T extends DbEntityNamed<T>> extends newPolicy<T> {
    protected helperNamed<T> helper;

    public newNamedPolicy(@NonNull DbPolicyInterface<T> policyInterface, helperNamed<T> helper) {
        super(policyInterface, helper);
    }

    @Override
    public void determineConflict(T entity, DbPolicyConflictInterface<T> result) {
        helper.findByNameExact(entity, entity.getName(), result::onConflict);
    }
}
