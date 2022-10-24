package com.pasich.mynotes;

import android.app.Application;

import com.pasich.mynotes.di.component.ApplicationComponent;
import com.pasich.mynotes.di.component.DaggerApplicationComponent;
import com.pasich.mynotes.di.module.ApplicationModule;

public class MyApp extends Application {

    private ApplicationComponent applicationComponent;


    @Deprecated
    private static MyApp sInstance;


    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
        sInstance = this;
        applicationComponent.inject(this);


    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }


    @Deprecated
    public static MyApp getInstance() {
        return sInstance;
    }

}