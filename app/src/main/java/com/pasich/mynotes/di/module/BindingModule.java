package com.pasich.mynotes.di.module;

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
import com.pasich.mynotes.di.scope.PerActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class BindingModule {


    @Provides
    @PerActivity
    ActivityNoteBinding providerActivityNoteBinding(AppCompatActivity activity) {
        return DataBindingUtil.setContentView(activity, R.layout.activity_note);
    }

    @Provides
    @PerActivity
    DialogAboutActivityBinding providerDialogAboutActivityBinding(AppCompatActivity activity) {
        return DialogAboutActivityBinding.inflate(activity.getLayoutInflater());
    }


    @Provides
    @PerActivity
    ActivityThemeBinding providerActivityThemeBinding(AppCompatActivity activity) {
        return DataBindingUtil.setContentView(activity, R.layout.activity_theme);
    }

    @Provides
    @PerActivity
    ActivityMainBinding providerActivityMainBinding(AppCompatActivity activity) {
        return DataBindingUtil.setContentView(activity, R.layout.activity_main);
    }

    @Provides
    @PerActivity
    ActivityTrashBinding providerActivityTrashBinding(AppCompatActivity activity) {
        return DataBindingUtil.setContentView(activity, R.layout.activity_trash);
    }

    @Provides
    @PerActivity
    ActivityBackupBinding providerActivityBackupBinding(AppCompatActivity activity) {
        return DataBindingUtil.setContentView(activity, R.layout.activity_backup);
    }

    @Provides
    @PerActivity
    ActivityAboutBinding providerActivityAboutBinding(AppCompatActivity activity) {
        return DataBindingUtil.setContentView(activity, R.layout.activity_about);
    }

    @Named("MainActivityRootLayout")
    @Provides
    @PerActivity
    CoordinatorLayout providerMainLayout(AppCompatActivity activity) {
        return activity.findViewById(R.id.activity_main);
    }


    @Named("TrashActivityRootLayout")
    @Provides
    @PerActivity
    CoordinatorLayout providerTrashLayout(AppCompatActivity activity) {
        return activity.findViewById(R.id.activity_trash);
    }

}
