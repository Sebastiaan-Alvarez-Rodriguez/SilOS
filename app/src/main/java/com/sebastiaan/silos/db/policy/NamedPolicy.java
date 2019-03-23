package com.sebastiaan.silos.db.policy;

import com.sebastiaan.silos.db.async.helper.helperNamed;
import com.sebastiaan.silos.db.entities.DbEntityNamed;
import com.sebastiaan.silos.db.policy.interfaces.DbPolicyConflictInterface;
import com.sebastiaan.silos.db.policy.interfaces.DbPolicyInterface;

import androidx.annotation.NonNull;

class NamedPolicy<T extends DbEntityNamed<T>> extends Policy<T> {
    protected helperNamed<T> helper;

    NamedPolicy(@NonNull DbPolicyInterface<T> policyInterface, helperNamed<T> helper) {
        super(policyInterface, helper);
        this.helper = helper;
    }

    /**
     * Determine if named entity X has a conflict.
     * It has a conflict, if there is an entity Y such that x.id != y.id and x.name == y.name
     * @param entity Entity to check
     * @param result confilictCallback. Gets conflicting entity if any, null otherwise
     */
    @Override
    public void determineConflict(T entity, DbPolicyConflictInterface<T> result) {
        helper.findByNameExact(entity, entity.getName(), conflictEntity -> {
            result.onConflict(conflictEntity == null || conflictEntity.getId() == entity.getId() ? null : conflictEntity);
        });
    }
}
