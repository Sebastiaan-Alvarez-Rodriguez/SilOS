package com.sebastiaan.silos.db.policy.interfaces;

import com.sebastiaan.silos.db.entities.DbEntity;

public interface DbPolicyConflictInterface<T extends DbEntity<T>> {
    void onConflict(T conflictEntity);
}
