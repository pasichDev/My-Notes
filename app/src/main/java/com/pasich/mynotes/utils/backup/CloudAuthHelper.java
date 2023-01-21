package com.pasich.mynotes.utils.backup;

import android.content.Intent;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import javax.inject.Inject;

public class CloudAuthHelper {

    @Inject
    public CloudAuthHelper() {

    }

    public Task<GoogleSignInAccount> getResultAuth(Intent data) {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            task.getResult(ApiException.class);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }

        return task;
    }
}
