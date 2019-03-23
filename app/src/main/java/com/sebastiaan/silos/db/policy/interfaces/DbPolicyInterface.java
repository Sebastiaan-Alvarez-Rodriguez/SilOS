package com.sebastiaan.silos.db.policy.interfaces;

import com.sebastiaan.silos.db.entities.DbEntity;

public interface DbPolicyInterface<T extends DbEntity<T>> {

    void onConflict(T entity, T conflictEntity);
    void onSuccess(T entity);
}
