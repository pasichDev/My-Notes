package com.pasich.mynotes.base;

import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.utils.rx.SchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;

public abstract class AppBasePresenter<T extends BaseView> implements BasePresenter<T> {

    private final SchedulerProvider schedulerProvider;
    private final CompositeDisposable compositeDisposable;
    private final DataManager dataManager;
    private T view;


    public AppBasePresenter(SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable, DataManager dataManager) {
        this.schedulerProvider = schedulerProvider;
        this.compositeDisposable = compositeDisposable;
        this.dataManager = dataManager;
    }

    @Override
    public DataManager getDataManager() {
        return dataManager;
    }

    @Override
    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    @Override
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
        compositeDisposable.dispose();
        view = null;
    }
}
