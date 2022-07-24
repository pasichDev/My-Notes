package com.pasich.mynotes.utils;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DiskExecutor implements Executor {

  private final Executor diskExecutor;
  public ExecutorService executorService;

  public DiskExecutor() {
    this.diskExecutor = Executors.newSingleThreadExecutor();
    this.executorService = Executors.newSingleThreadExecutor();
  }

  @Override
  public void execute(Runnable runnable) {
    diskExecutor.execute(runnable);
  }


}
