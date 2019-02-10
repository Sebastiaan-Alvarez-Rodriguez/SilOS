package com.sebastiaan.silos.db.async.group;

import com.sebastiaan.silos.db.async.DbAsyncInterface;
import com.sebastiaan.silos.db.async.task.DbAsyncTask;

import java.util.ArrayList;
import java.util.Collections;

public class parallelgroup {
    private ArrayList<DbAsyncTask> taskGroup;
    private ArrayList<Boolean> finished;
    private DbAsyncInterface<Void> callback;

    public parallelgroup(DbAsyncInterface<Void> callback) {
        taskGroup = new ArrayList<>();
        finished = new ArrayList<>();
        this.callback = callback;
    }

    public parallelgroup(ArrayList<DbAsyncTask> taskGroup, DbAsyncInterface<Void> callback) {
        this.taskGroup = taskGroup;
        finished = new ArrayList<>(Collections.nCopies(taskGroup.size(), false));
        this.callback = callback;
    }

    public void addTask(DbAsyncTask task) {
        taskGroup.add(task);
        finished.add(false);
    }

    public void execute() {
        for (DbAsyncTask<?> task : taskGroup)
            task.setCallback(result -> {
                finished.set(taskGroup.indexOf(task), true);
                if (!finished.contains(false))
                    callback.onComplete(null);
            }).execute();
    }
}
