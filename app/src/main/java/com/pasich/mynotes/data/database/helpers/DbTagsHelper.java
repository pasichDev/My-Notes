package com.pasich.mynotes.data.database.helpers;

import com.pasich.mynotes.data.database.model.Tag;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface DbTagsHelper {
    /**
     * Tags
     */
    Observable<List<Tag>> getTags();

    Observable<List<Tag>> getTagsUser();

    Observable<Integer> getCountTagAll();

    Completable addTag(Tag tag);

    void deleteTag(Tag tag);

    Completable updateTag(Tag tag);
}
