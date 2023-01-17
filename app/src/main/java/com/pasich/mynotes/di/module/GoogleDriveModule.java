package com.pasich.mynotes.di.module;


import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Scope;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.services.drive.DriveScopes;
import com.pasich.mynotes.di.scope.ActivityContext;
import com.pasich.mynotes.di.scope.ApplicationContext;
import com.pasich.mynotes.di.scope.PerActivity;

import java.util.Collections;

import dagger.Module;
import dagger.Provides;

@Module
public class GoogleDriveModule {

    @Provides
    @PerActivity
    Scope providesACCESS_DRIVE_SCOPE() {
        return new Scope(DriveScopes.DRIVE_APPDATA);
    }

    @Provides
    @PerActivity
    GoogleSignInClient providesGoogleSignInClient(@ApplicationContext Context context) {
        return GoogleSignIn.getClient(context.getApplicationContext(), new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build());
    }

    @Provides
    @PerActivity
    GoogleSignInAccount providesGoogleSignInAccount(@ApplicationContext Context context) {
        return GoogleSignIn.getLastSignedInAccount(context.getApplicationContext());
    }

    @Provides
    @PerActivity
    GoogleAccountCredential providesGoogleAccountCredential(@ActivityContext Context context) {
        return GoogleAccountCredential.usingOAuth2(
                context, Collections.singleton(Scopes.DRIVE_FILE));
    }

}
