package com.pasich.mynotes.di.module;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.pasich.mynotes.di.scope.ActivityContext;
import com.pasich.mynotes.di.scope.PerActivity;
import com.pasich.mynotes.ui.contract.MainContract;
import com.pasich.mynotes.ui.presenter.MainPresenter;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private final AppCompatActivity activity;

    public ActivityModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Inject
    String text;


    @Provides
    @ActivityContext
    Context provideContext() {
        return activity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return activity;
    }


    @Provides
    @PerActivity
    MainContract.presenter providesMainPresenter(MainPresenter presenter) {
        return presenter;
    }




}
