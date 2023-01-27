package com.pasich.mynotes.di.activity;


import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
import com.pasich.mynotes.utils.backup.CloudCacheHelper;
import com.pasich.mynotes.utils.constants.Drive_Scope;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.android.scopes.ActivityScoped;

@Module
@InstallIn(ActivityComponent.class)
public class DriveApiModule {


    @Provides
    @ActivityScoped
    Scope provideCloudAccessDriveScope() {
        return Drive_Scope.ACCESS_DRIVE_SCOPE;
    }


    @Provides
    @ActivityScoped
    GoogleSignInClient providesGoogleSignInClient(@ApplicationContext Context mContext, Scope accessDrive) {
        return GoogleSignIn.getClient(mContext, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().requestScopes(accessDrive).build());
    }


    @Provides
    @ActivityScoped
    CloudCacheHelper providesCloudCacheHelper(@ApplicationContext Context mContext, Scope accessDrive) {
        GoogleSignInAccount mLastAccount = GoogleSignIn.getLastSignedInAccount(mContext);

        if (mLastAccount != null) {
            return new CloudCacheHelper().build(mLastAccount, GoogleSignIn.hasPermissions(mLastAccount, accessDrive));
        } else {
            return new CloudCacheHelper();
        }

    }
}
