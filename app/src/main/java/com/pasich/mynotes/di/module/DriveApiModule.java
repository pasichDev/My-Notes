package com.pasich.mynotes.di.module;


import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
import com.pasich.mynotes.di.scope.ApplicationContext;
import com.pasich.mynotes.di.scope.PerActivity;
import com.pasich.mynotes.di.scope.ScopeDriveInfo;
import com.pasich.mynotes.utils.backup.CloudCacheHelper;
import com.pasich.mynotes.utils.constants.Drive_Scope;

import dagger.Module;
import dagger.Provides;

@Module
public class DriveApiModule {


    @Provides
    @ScopeDriveInfo
    Scope provideCloudAccessDriveScope() {
        return Drive_Scope.ACCESS_DRIVE_SCOPE;
    }


    @Provides
    @PerActivity
    GoogleSignInClient providesGoogleSignInClient(@ApplicationContext Context mContext, @ScopeDriveInfo Scope accessDrive) {
        return GoogleSignIn.getClient(mContext, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().requestScopes(accessDrive).build());
    }

    @Provides
    @PerActivity
    CloudCacheHelper providesCloudCacheHelper(@ApplicationContext Context mContext, @ScopeDriveInfo Scope accessDrive) {
        GoogleSignInAccount mLastAccount = GoogleSignIn.getLastSignedInAccount(mContext);

        if (mLastAccount != null) {
            return new CloudCacheHelper().build(mLastAccount, GoogleSignIn.hasPermissions(mLastAccount, accessDrive));
        } else {
            return new CloudCacheHelper();
        }

    }
}
