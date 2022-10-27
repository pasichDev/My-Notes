package com.pasich.mynotes.data.tags.source.dao;

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
  List<Tag> getTags();

  @Query("SELECT * FROM tags where systemAction = 0")
  List<Tag> getTagsUser();

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void addTag(Tag tag);

  @Query("SELECT COUNT(name) FROM tags WHERE systemAction = 0")
  int getCountAllTag();

  @Update
  void updateTag(Tag tag);

  @Delete
  void deleteTag(Tag tag);

  @Query("DELETE FROM tags")
  void deleteAll();
}
