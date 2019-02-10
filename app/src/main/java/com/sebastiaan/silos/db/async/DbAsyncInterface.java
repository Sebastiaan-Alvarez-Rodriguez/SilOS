package com.sebastiaan.silos.db.async;

public interface DbAsyncInterface<T> {
    void onComplete(T result);
}
