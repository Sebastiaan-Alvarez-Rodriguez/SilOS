package com.sebastiaan.silos.db.interfaces;

public interface DbInterface<T> {
    long[] insertAll(T... Ts);
    long insert(T T);

    void updateAll(T... Ts);
    void update(T T);

    void deleteAll(T... Ts);
    void delete(T T);
}
