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

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  void addTag(Tag tag);

  /**
   * @Query("SELECT COUNT(name) FROM notes WHERE tag = nameTag") int getCountNotesTags(String
   * nameTAg);
   */
  @Update
  void updateTag(Tag tag);

  /* @Query("DELETE FROM tags WHERE name = :nameTag")
  void deleteTag(String nameTag);*/

  @Delete
  void deleteTag(Tag tag);

  @Query("DELETE FROM tags")
  void deleteAll();
}
