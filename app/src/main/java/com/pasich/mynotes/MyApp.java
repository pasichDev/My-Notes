package com.pasich.mynotes;

import android.app.Application;

import com.pasich.mynotes.di.component.ApplicationComponent;
import com.pasich.mynotes.di.component.DaggerApplicationComponent;
import com.pasich.mynotes.di.module.ApplicationModule;

public class MyApp extends Application {

    private ApplicationComponent applicationComponent;


    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
        applicationComponent.inject(this);


    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

}