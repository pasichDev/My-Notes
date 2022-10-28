package com.pasich.mynotes.data.database;


import com.pasich.mynotes.data.database.model.Tag;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface DbHelper {
    Observable<List<Tag>> getTags();

    Observable<List<Tag>> getTagsUser();

    Observable<Integer> getCountTagAll();

    Completable addTag(Tag tag);

    Completable deleteTag(Tag tag);

    Completable updateTag(Tag tag);


}
