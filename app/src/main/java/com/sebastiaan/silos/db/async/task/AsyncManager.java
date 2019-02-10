package com.sebastiaan.silos.db.async.task;

import java.util.ArrayList;

public class AsyncManager {
    private ArrayList<DbAsyncTask> workList = new ArrayList<>();

    void registerWorkerStart(DbAsyncTask thread) {
        workList.add(thread);
    }

    void registerWorkerStop(DbAsyncTask thread) {
        workList.remove(thread);
    }

    public void cancelAllWorking() {
        for (DbAsyncTask task: workList) {
            task.cancel(false);
        }
    }

    public boolean noneWorking() {
        return workList.isEmpty();
    }
}
