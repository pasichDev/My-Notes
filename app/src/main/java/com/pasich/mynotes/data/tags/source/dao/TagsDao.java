package com.pasich.mynotes.data.tags.source.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.pasich.mynotes.data.tags.Tag;

import java.util.List;

@Dao
public interface TagsDao {
  @Query("SELECT * FROM tags")
  LiveData<List<Tag>> getTags();

  @Insert
  void addTag(Tag tag);

}
