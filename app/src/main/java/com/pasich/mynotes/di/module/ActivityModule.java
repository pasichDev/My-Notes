package com.pasich.mynotes.di.module;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.databinding.DataBindingUtil;

import com.pasich.mynotes.R;
import com.pasich.mynotes.databinding.ActivityMainBinding;
import com.pasich.mynotes.databinding.ActivityNoteBinding;
import com.pasich.mynotes.databinding.ActivityTrashBinding;
import com.pasich.mynotes.di.scope.ActivityContext;
import com.pasich.mynotes.di.scope.PerActivity;
import com.pasich.mynotes.ui.contract.HelloContract;
import com.pasich.mynotes.ui.contract.MainContract;
import com.pasich.mynotes.ui.contract.NoteContract;
import com.pasich.mynotes.ui.contract.TrashContract;
import com.pasich.mynotes.ui.contract.dialogs.ChoiceTagDialogContract;
import com.pasich.mynotes.ui.contract.dialogs.ClearTrashDialogContract;
import com.pasich.mynotes.ui.contract.dialogs.DeleteTagDialogContract;
import com.pasich.mynotes.ui.contract.dialogs.NewTagDialogContract;
import com.pasich.mynotes.ui.contract.dialogs.SearchDialogContract;
import com.pasich.mynotes.ui.presenter.HelloPresenter;
import com.pasich.mynotes.ui.presenter.MainPresenter;
import com.pasich.mynotes.ui.presenter.NotePresenter;
import com.pasich.mynotes.ui.presenter.TrashPresenter;
import com.pasich.mynotes.ui.presenter.dialogs.ChoiceTagDialogPresenter;
import com.pasich.mynotes.ui.presenter.dialogs.ClearTrashDialogPresenter;
import com.pasich.mynotes.ui.presenter.dialogs.DeleteTagDialogPresenter;
import com.pasich.mynotes.ui.presenter.dialogs.NewTagDialogPresenter;
import com.pasich.mynotes.ui.presenter.dialogs.SearchDialogPresenter;
import com.pasich.mynotes.utils.actionPanel.ActionUtils;
import com.pasich.mynotes.utils.recycler.SpacesItemDecoration;
import com.pasich.mynotes.utils.rx.AppSchedulerProvider;
import com.pasich.mynotes.utils.rx.SchedulerProvider;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

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


    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider provideScheduleProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    @PerActivity
    ActivityNoteBinding providerActivityNoteBinding(AppCompatActivity activity) {
        return DataBindingUtil.setContentView(activity, R.layout.activity_note);
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

    @Named("NotesItemSpaceDecoration")
    @Provides
    @PerActivity
    SpacesItemDecoration providerSpaceItemDecorationNotes() {
        return new SpacesItemDecoration(15);
    }

    @Named("MainActivityRootLayout")
    @Provides
    @PerActivity
    CoordinatorLayout providerMainLayout() {
        return activity.findViewById(R.id.activity_main);
    }


    @Named("TrashActivityRootLayout")
    @Provides
    @PerActivity
    CoordinatorLayout providerTrashLayout() {
        return activity.findViewById(R.id.activity_trash);
    }

    @Provides
    @PerActivity
    HelloContract.presenter providesHelloPresenter(HelloPresenter presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    MainContract.presenter providesMainPresenter(MainPresenter presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    NoteContract.presenter providesNotePresenter(NotePresenter presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    TrashContract.presenter providerTrashActivityPresenter(TrashPresenter presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    NewTagDialogContract.presenter providerNewTagDialogPresenter(NewTagDialogPresenter presenter) {
        return presenter;

    }

    @Provides
    @PerActivity
    ChoiceTagDialogContract.presenter providerChoiceTagDialogPresenter(ChoiceTagDialogPresenter presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    DeleteTagDialogContract.presenter providerDeleteTagDialogPresenter(DeleteTagDialogPresenter presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    SearchDialogContract.presenter providerSearchDialogPresenter(SearchDialogPresenter presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    ClearTrashDialogContract.presenter providerClearTrashDialogPresenter(ClearTrashDialogPresenter presenter) {
        return presenter;
    }


    @Named("ActionUtilsTrash")
    @Provides
    @PerActivity
    ActionUtils providerActionUtilsTrash(@Named("TrashActivityRootLayout") CoordinatorLayout coordinatorLayout) {
        return new ActionUtils(coordinatorLayout);
    }

    @Named("ActionUtilsMain")
    @Provides
    @PerActivity
    ActionUtils providerActionUtilsMain(@Named("MainActivityRootLayout") CoordinatorLayout coordinatorLayout) {
        return new ActionUtils(coordinatorLayout);
    }
}
