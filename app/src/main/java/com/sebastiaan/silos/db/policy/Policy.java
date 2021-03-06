package com.sebastiaan.silos.db.policy;

import com.sebastiaan.silos.db.async.helper.helper;
import com.sebastiaan.silos.db.entities.DbEntity;
import com.sebastiaan.silos.db.policy.interfaces.DbPolicyConflictInterface;
import com.sebastiaan.silos.db.policy.interfaces.DbPolicyInterface;

import androidx.annotation.NonNull;

abstract class Policy<T extends DbEntity<T>> {
    protected DbPolicyInterface<T> policyInterface;
    protected helper<T> helper;

    Policy(@NonNull DbPolicyInterface<T> policyInterface, helper<T> helper) {
        this.policyInterface = policyInterface;
        this.helper = helper;
    }

    /**
     * Insert entity if no conflict is found. Otherwise, return entity and conflicting entity
     * to caller via DbPolicyInterface.onConflict()
     * @param entity entity to be inserted
     */
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
                //Delete conflicting entity
                helper.delete(conflictEntity, result ->
                        //insert our entity
                        helper.insert(entity, resultEntityID -> {
                            entity.setId(resultEntityID);
                            policyInterface.onSuccess(entity);
                    }));
            }
        });
    }

    public void update(T entity) {
        determineConflict(entity, conflictEntity -> {
            if (conflictEntity == null) {
                helper.update(entity, result -> policyInterface.onSuccess(entity));
            } else {
                policyInterface.onConflict(entity, conflictEntity);
            }
        });
    }

    public void update_force(T entity) {
        determineConflict(entity, conflictEntity -> {
            if (conflictEntity == null) {
                helper.update(entity, result -> policyInterface.onSuccess(entity));
            } else {
                helper.delete(conflictEntity, result -> {
                    helper.update(entity, result1 -> policyInterface.onSuccess(entity));
                });
            }
        });
    }

    public abstract void determineConflict(T entity, DbPolicyConflictInterface<T> result);
}
