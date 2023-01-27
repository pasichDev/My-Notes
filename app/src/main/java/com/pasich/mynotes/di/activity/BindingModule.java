package com.pasich.mynotes.di.activity;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.databinding.DataBindingUtil;

import com.pasich.mynotes.R;
import com.pasich.mynotes.databinding.ActivityAboutBinding;
import com.pasich.mynotes.databinding.ActivityBackupBinding;
import com.pasich.mynotes.databinding.ActivityMainBinding;
import com.pasich.mynotes.databinding.ActivityNoteBinding;
import com.pasich.mynotes.databinding.ActivityThemeBinding;
import com.pasich.mynotes.databinding.ActivityTrashBinding;
import com.pasich.mynotes.databinding.DialogAboutActivityBinding;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.scopes.ActivityScoped;


@Module
@InstallIn(ActivityComponent.class)
public class BindingModule {


    @Provides
    @ActivityScoped
    ActivityNoteBinding providerActivityNoteBinding(Activity activity) {
        return DataBindingUtil.setContentView((AppCompatActivity) activity, R.layout.activity_note);
    }

    @Provides
    @ActivityScoped
    DialogAboutActivityBinding providerDialogAboutActivityBinding(Activity activity) {
        return DialogAboutActivityBinding.inflate(activity.getLayoutInflater());
    }


    @Provides
    @ActivityScoped
    ActivityThemeBinding providerActivityThemeBinding(Activity activity) {
        return DataBindingUtil.setContentView(activity, R.layout.activity_theme);
    }

    @Provides
    @ActivityScoped
    ActivityMainBinding providerActivityMainBinding(Activity activity) {
        return DataBindingUtil.setContentView(activity, R.layout.activity_main);
    }

    @Provides
    @ActivityScoped
    ActivityTrashBinding providerActivityTrashBinding(Activity activity) {
        return DataBindingUtil.setContentView(activity, R.layout.activity_trash);
    }

    @Provides
    @ActivityScoped
    ActivityBackupBinding providerActivityBackupBinding(Activity activity) {
        return DataBindingUtil.setContentView(activity, R.layout.activity_backup);
    }

    @Provides
    @ActivityScoped
    ActivityAboutBinding providerActivityAboutBinding(Activity activity) {
        return DataBindingUtil.setContentView(activity, R.layout.activity_about);
    }

    @Named("MainActivityRootLayout")
    @Provides
    @ActivityScoped
    CoordinatorLayout providerMainLayout(Activity activity) {
        return activity.findViewById(R.id.activity_main);
    }


    @Named("TrashActivityRootLayout")
    @Provides
    @ActivityScoped
    CoordinatorLayout providerTrashLayout(Activity activity) {
        return activity.findViewById(R.id.activity_trash);
    }

}
