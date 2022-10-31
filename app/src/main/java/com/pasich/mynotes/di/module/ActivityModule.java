package com.pasich.mynotes.di.module;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.databinding.DataBindingUtil;

import com.pasich.mynotes.R;
import com.pasich.mynotes.databinding.ActivityMainBinding;
import com.pasich.mynotes.databinding.ActivityNoteBinding;
import com.pasich.mynotes.di.scope.ActivityContext;
import com.pasich.mynotes.di.scope.PerActivity;
import com.pasich.mynotes.ui.contract.ChoiceTagDialogContract;
import com.pasich.mynotes.ui.contract.DeleteTagDialogContract;
import com.pasich.mynotes.ui.contract.MainContract;
import com.pasich.mynotes.ui.contract.NewTagDialogContract;
import com.pasich.mynotes.ui.contract.NoteContract;
import com.pasich.mynotes.ui.presenter.ChoiceTagDialogPresenter;
import com.pasich.mynotes.ui.presenter.DeleteTagDialogPresenter;
import com.pasich.mynotes.ui.presenter.MainPresenter;
import com.pasich.mynotes.ui.presenter.NewTagDialogPresenter;
import com.pasich.mynotes.ui.presenter.NotePresenter;
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


}
