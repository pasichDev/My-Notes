package com.pasich.mynotes.data.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.pasich.mynotes.data.model.ItemListNote;

import java.util.List;

@Dao
public interface ItemListNoteDao {
    @Query("SELECT * FROM listItemsNote WHERE idNote = :idNote")
    List<ItemListNote> getListForIdNote(long idNote);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveListNote(List<ItemListNote> listNotes);

    @Query("DELETE FROM listItemsNote WHERE idNote=:idNote")
    void deleteItemsList(int idNote);

}
