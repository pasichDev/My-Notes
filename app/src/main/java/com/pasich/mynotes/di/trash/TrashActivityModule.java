package com.pasich.mynotes.di.trash;


import com.pasich.mynotes.ui.contract.TrashContract;
import com.pasich.mynotes.ui.presenter.TrashPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class TrashActivityModule {


    @Provides
    public TrashContract.presenter providerPresenter() {
        return new TrashPresenter();
    }


}
