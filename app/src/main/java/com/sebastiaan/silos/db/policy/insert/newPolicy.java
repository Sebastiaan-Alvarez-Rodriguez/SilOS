package com.sebastiaan.silos.db.policy.insert;

import com.sebastiaan.silos.db.async.helper.helper;
import com.sebastiaan.silos.db.entities.DbEntity;
import com.sebastiaan.silos.db.policy.DbPolicyConflictInterface;
import com.sebastiaan.silos.db.policy.DbPolicyInterface;

import androidx.annotation.NonNull;

public abstract class newPolicy<T extends DbEntity<T>> {
    protected DbPolicyInterface<T> policyInterface;
    protected helper<T> helper;

    public newPolicy(@NonNull DbPolicyInterface<T> policyInterface, helper<T> helper) {
        this.policyInterface = policyInterface;
        this.helper = helper;
    }

    public void insert(T entity) {
        determineConflict(entity, conflictEntity -> {
            if (conflictEntity == null) {
                helper.insert(entity, resultEntityID -> {
                    entity.setId(resultEntityID);
                    policyInterface.onSuccess(entity);
                });
            } else {
                policyInterface.onConflict(entity, conflictEntity);
            }
        });
    }

    public void update(T entity) {
        determineConflict(entity, conflictEntity -> {
            if (conflictEntity == null) {
                helper.update(entity, result -> policyInterface.onSuccess(entity));
            }
        });
    }

    /**
     * During insert, a conflicting entity was found.
     * This function is to be called to insert, and remove any conflicting entities.
     * @param entity entity to be inserted
     */
    public void insert_force(T entity) {
        determineConflict(entity, conflictEntity -> {
            if (conflictEntity == null) {
                helper.insert(entity, resultEntityID -> {
                    entity.setId(resultEntityID);
                    policyInterface.onSuccess(entity);
                });
            } else {
                helper.delete(conflictEntity, result ->
                        helper.insert(entity, resultEntityID -> {
                            entity.setId(resultEntityID);
                            policyInterface.onSuccess(entity);
                        }));
            }
        });
    }

    public abstract void determineConflict(T entity, DbPolicyConflictInterface<T> result);
}
