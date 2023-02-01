package com.pasich.mynotes.di.fragment;

import com.pasich.mynotes.ui.contract.dialogs.ClearTrashDialogContract;
import com.pasich.mynotes.ui.contract.dialogs.DeleteTagDialogContract;
import com.pasich.mynotes.ui.contract.dialogs.NewTagDialogContract;
import com.pasich.mynotes.ui.contract.dialogs.SearchDialogContract;
import com.pasich.mynotes.ui.presenter.dialogs.ClearTrashDialogPresenter;
import com.pasich.mynotes.ui.presenter.dialogs.DeleteTagDialogPresenter;
import com.pasich.mynotes.ui.presenter.dialogs.NameTagDialogPresenter;
import com.pasich.mynotes.ui.presenter.dialogs.SearchDialogPresenter;
import com.pasich.mynotes.utils.adapters.searchAdapter.SearchNotesAdapter;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.FragmentComponent;
import dagger.hilt.android.scopes.FragmentScoped;

@Module
@InstallIn(FragmentComponent.class)
public class FragmentModule {


    @Provides
    @FragmentScoped
    NewTagDialogContract.presenter providerNewTagDialogPresenter(NameTagDialogPresenter presenter) {
        return presenter;

    }

    @Provides
    @FragmentScoped
    SearchNotesAdapter providerSearchAdapter() {
        return new SearchNotesAdapter();
    }

    @Provides
    @FragmentScoped
    DeleteTagDialogContract.presenter providerDeleteTagDialogPresenter(DeleteTagDialogPresenter presenter) {
        return presenter;
    }

    @Provides
    @FragmentScoped
    SearchDialogContract.presenter providerSearchDialogPresenter(SearchDialogPresenter presenter) {
        return presenter;
    }

    @Provides
    @FragmentScoped
    ClearTrashDialogContract.presenter providerClearTrashDialogPresenter(ClearTrashDialogPresenter presenter) {
        return presenter;
    }


}
