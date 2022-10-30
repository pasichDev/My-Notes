package com.pasich.mynotes.base;

import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.utils.rx.SchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;

public interface BasePresenter<V extends BaseView> {

    void attachView(V mVIew);

    void viewIsReady();

    void detachView();

    void destroy();

    DataManager getDataManager();

    CompositeDisposable getCompositeDisposable();

    SchedulerProvider getSchedulerProvider();
}
