package com.pasich.mynotes.data.tags.source.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.pasich.mynotes.data.tags.Tag;

import java.util.List;

@Dao
public interface TagsDao {
  @Query("SELECT * FROM tags")
  List<Tag> getTags();

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void saveTags(List<Tag> movies);

  @Insert
  void addTag(Tag tag);

}
