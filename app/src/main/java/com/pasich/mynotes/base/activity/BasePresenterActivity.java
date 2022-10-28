package com.pasich.mynotes.base.activity;

import com.pasich.mynotes.data.DataManger;

public abstract class BasePresenterActivity<T extends BaseViewActivity> implements ActivityPresenter<T> {


    private final DataManger dataManager;
    private T view;


    public BasePresenterActivity(DataManger dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public DataManger getDataManager() {
        return dataManager;
    }


    @Override
    public void attachView(T v) {
        view = v;
    }

    @Override
    public void detachView() {
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
