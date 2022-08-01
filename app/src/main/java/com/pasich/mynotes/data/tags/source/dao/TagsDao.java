package com.pasich.mynotes.data.tags.source.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.pasich.mynotes.data.tags.Tag;

import java.util.List;

@Dao
public interface TagsDao {
  @Query("SELECT * FROM tags")
  LiveData<List<Tag>> getTags();

  @Query("SELECT * FROM tags where systemAction = 0")
  LiveData<List<Tag>> getTagsUser();

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void addTag(Tag tag);

  @Update
  void updateTag(Tag tag);

  @Delete
  void deleteTag(Tag tag);

  @Query("DELETE FROM tags")
  void deleteAll();
}
