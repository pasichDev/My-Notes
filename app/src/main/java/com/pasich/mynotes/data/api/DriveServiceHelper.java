package com.pasich.mynotes.data.api;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

public class DriveServiceHelper {


    private final Executor mExecutor = Executors.newSingleThreadExecutor();


    @Inject
    public DriveServiceHelper() {

    }


}
