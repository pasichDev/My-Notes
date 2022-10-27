package com.pasich.mynotes.data.newdata;


import com.pasich.mynotes.data.tags.Tag;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Observable;

@Singleton
public class AppDbHelper implements DbHelper {

    private final AppDatabase appDatabase;

    @Inject
    AppDbHelper(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }


    @Override
    public Observable<List<Tag>> getTags() {
        return Observable.fromCallable(() -> appDatabase.tagsDao().getTags());
    }

    @Override
    public Observable<List<Tag>> getTagsUser() {
        return Observable.fromCallable(() -> appDatabase.tagsDao().getTagsUser());
    }

    @Override
    public Observable<Integer> getCountTagAll() {
        return Observable.fromCallable(() -> appDatabase.tagsDao().getCountAllTag());
    }

    @Override
    public Completable addTag(Tag tag) {
        return Completable.fromAction(() -> appDatabase.tagsDao().addTag(tag));
    }

    @Override
    public Completable deleteTag(Tag tag) {
        return Completable.fromAction(() -> appDatabase.tagsDao().deleteTag(tag));
    }

    @Override
    public Completable updateTag(Tag tag) {
        return Completable.fromAction(() -> appDatabase.tagsDao().updateTag(tag));
    }
}
