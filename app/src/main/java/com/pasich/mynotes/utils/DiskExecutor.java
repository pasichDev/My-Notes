package com.pasich.mynotes.utils;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DiskExecutor implements Executor {

     final Executor diskExecutor;

    public DiskExecutor() {
        this.diskExecutor = Executors.newSingleThreadExecutor();
    }

    @Override
    public void execute(Runnable runnable) {
        diskExecutor.execute(runnable);
    }

}
