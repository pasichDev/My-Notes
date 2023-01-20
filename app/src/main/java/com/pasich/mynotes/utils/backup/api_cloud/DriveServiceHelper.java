package com.pasich.mynotes.utils.backup.api_cloud;

import com.google.api.services.drive.Drive;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DriveServiceHelper {


    private final Executor mExecutor = Executors.newSingleThreadExecutor();
    private final Drive mDriveService;


    public DriveServiceHelper(Drive driveService) {
        mDriveService = driveService;
    }


}
