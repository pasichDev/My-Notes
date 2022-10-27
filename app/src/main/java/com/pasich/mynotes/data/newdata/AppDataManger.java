package com.pasich.mynotes.data.newdata;

import android.content.Context;

import com.pasich.mynotes.data.tags.Tag;
import com.pasich.mynotes.di.scope.ApplicationContext;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Observable;

@Singleton
public class AppDataManger implements DataManger {


    private final Context context;
    private final DbHelper dbHelper;

    @Inject
    AppDataManger(@ApplicationContext Context context,
                  DbHelper dbHelper) {
        this.context = context;
        this.dbHelper = dbHelper;
    }

    @Override
    public Observable<List<Tag>> getTags() {
        return dbHelper.getTags();
    }

    @Override
    public Observable<List<Tag>> getTagsUser() {
        return dbHelper.getTagsUser();
    }

    @Override
    public Observable<Integer> getCountTagAll() {
        return dbHelper.getCountTagAll();
    }

    @Override
    public Completable addTag(Tag tag) {
        return dbHelper.addTag(tag);
    }

    @Override
    public Completable deleteTag(Tag tag) {
        return dbHelper.deleteTag(tag);
    }

    @Override
    public Completable updateTag(Tag tag) {
        return dbHelper.updateTag(tag);
    }
}
