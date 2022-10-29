package com.pasich.mynotes.di.module;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.pasich.mynotes.R;
import com.pasich.mynotes.di.scope.ActivityContext;
import com.pasich.mynotes.di.scope.PerActivity;
import com.pasich.mynotes.ui.contract.MainContract;
import com.pasich.mynotes.ui.presenter.MainPresenter;
import com.pasich.mynotes.utils.recycler.SpacesItemDecoration;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private final AppCompatActivity activity;

    public ActivityModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return activity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return activity;
    }


    @Named("NotesItemSpaceDecoration")
    @Provides
    @PerActivity
    SpacesItemDecoration providerSpaceItemDecorationNotes() {
        return new SpacesItemDecoration(15);
    }


    @Provides
    @PerActivity
    MainContract.presenter providesMainPresenter(MainPresenter presenter) {
        return presenter;
    }

    @Named("MainActivityRootLayout")
    @Provides
    @PerActivity
    CoordinatorLayout providerMainLayout() {
        return activity.findViewById(R.id.activity_main);
    }
}
