package com.pasich.mynotes.di.module;


import com.pasich.mynotes.di.scope.PerActivity;
import com.pasich.mynotes.utils.adapters.searchAdapter.SearchNotesAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class SearchDialogModule {


    @Provides
    @PerActivity
    SearchNotesAdapter providerSearchAdapter() {
        return new SearchNotesAdapter();
    }


}
