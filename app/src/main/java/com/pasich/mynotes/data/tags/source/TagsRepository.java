package com.pasich.mynotes.data.tags.source;


import androidx.lifecycle.LiveData;

import com.pasich.mynotes.data.tags.Tag;
import com.pasich.mynotes.data.tags.source.dao.TagsDao;
import com.pasich.mynotes.utils.DiskExecutor;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TagsRepository {

    private static TagsRepository instance;
    private Executor executor;
    private TagsDao tagsDao;

    private TagsRepository(Executor executor, TagsDao tagsDao) {
        this.executor = executor;
        this.tagsDao = tagsDao;
    }

    public static TagsRepository getInstance(TagsDao tagsDao) {
        if (instance == null) {
            instance = new TagsRepository(new DiskExecutor(), tagsDao);
        }
        return instance;
    }


    public LiveData<List<Tag>> getTags() {
        LiveData<List<Tag>> mTags;
        mTags = tagsDao.getTags();

        return mTags;
    }


    public LiveData<List<Tag>> getTagsUser() {
        LiveData<List<Tag>> mTags;
        mTags = tagsDao.getTagsUser();

        return mTags;
    }

    public void addTag(Tag tag) {

        Runnable runnable = () -> tagsDao.addTag(tag);
        executor.execute(runnable);
    }

    public void deleteTag(Tag tag) {

        Runnable runnable = () -> tagsDao.deleteTag(tag);
        executor.execute(runnable);
    }

    public void updateTag(Tag tag) {

        Runnable runnable = () -> tagsDao.updateTag(tag);
        executor.execute(runnable);
    }


    public int getCountTagAll() throws ExecutionException, InterruptedException {
        Future<?> future = Executors.newSingleThreadExecutor()
                .submit((Callable<?>) () -> tagsDao.getCountAllTag());
        return (int) future.get();
    }

}
