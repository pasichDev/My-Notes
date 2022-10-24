package com.pasich.mynotes.di.component;

import android.app.Application;
import android.content.Context;

import com.pasich.mynotes.MyApp;
import com.pasich.mynotes.di.ApplicationContext;
import com.pasich.mynotes.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Abhijit on 08-11-2017.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ApplicationContext
    Context getContext();

    Application getApplication();

    //  DataManager getDataManager();

    //   GoogleSignInClient getGoogleSignInClient();

    //  CallbackManager getCallbackManager();


    void inject(MyApp mvpApp);
}
