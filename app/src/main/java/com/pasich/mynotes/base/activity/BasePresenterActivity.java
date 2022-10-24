package com.pasich.mynotes.base.activity;

import com.pasich.mynotes.data.DataManagerNew;
import com.pasich.mynotes.data.notes.source.NotesRepository;
import com.pasich.mynotes.data.tags.source.TagsRepository;
import com.pasich.mynotes.data.trash.source.TrashRepository;

public abstract class BasePresenterActivity<T extends BaseViewActivity> implements ActivityPresenter<T> {


    private final DataManagerNew dataManager;
    private T view;


    public BasePresenterActivity(DataManagerNew dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public DataManagerNew getDataManager() {
        return dataManager;
    }

    @Override
    public TrashRepository getTrashRepository() {
        return getDataManager().getTrashRepository();
    }

    @Override
    public NotesRepository getNotesRepository() {
        return getDataManager().getNotesRepository();
    }

    @Override
    public TagsRepository getTagsRepository() {
        return getDataManager().getTagsRepository();
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
