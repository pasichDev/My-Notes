package com.pasich.mynotes.data.database.helpers;

import com.pasich.mynotes.data.database.model.Tag;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface DbTagsHelper {
    /**
     * Tags
     */
    Observable<List<Tag>> getTags();

    Observable<List<Tag>> getTagsUser();

    Single<Integer> getCountTagAll();

    Completable addTag(Tag tag);

    void deleteTag(Tag tag);

    Completable updateTag(Tag tag);
}
