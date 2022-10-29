package com.pasich.mynotes.base.activity;

import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.utils.SchedulerProvider.SchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BasePresenterActivity<T extends BaseViewActivity> implements ActivityPresenter<T> {

    private final SchedulerProvider schedulerProvider;
    private final CompositeDisposable compositeDisposable;
    private final DataManager dataManager;
    private T view;


    public BasePresenterActivity(SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable, DataManager dataManager) {
        this.schedulerProvider = schedulerProvider;
        this.compositeDisposable = compositeDisposable;
        this.dataManager = dataManager;
    }

    @Override
    public DataManager getDataManager() {
        return dataManager;
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }


    public SchedulerProvider getSchedulerProvider() {
        return schedulerProvider;
    }

    @Override
    public void attachView(T v) {
        view = v;
    }

    @Override
    public void detachView() {
        compositeDisposable.dispose();
        view = null;
    }

    public T getView() {
        return view;
    }

    protected boolean isViewAttached() {
        return view != null;
    }

    @Override
    public void destroy() {
        view = null;
    }
}
