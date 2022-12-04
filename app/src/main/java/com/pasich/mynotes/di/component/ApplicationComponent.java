package com.pasich.mynotes.di.component;

import android.content.Context;

import com.pasich.mynotes.MyApp;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.di.module.ApplicationModule;
import com.pasich.mynotes.di.scope.ApplicationContext;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ApplicationContext
    Context getContext();

    DataManager getDataManager();


    void inject(MyApp mvpApp);
}
