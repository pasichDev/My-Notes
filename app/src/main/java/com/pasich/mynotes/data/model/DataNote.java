package com.pasich.mynotes.data.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class DataNote {

    @Embedded
    public Note note;

    @Relation(
            parentColumn = "id",
            entityColumn = "idNote"
    )
    public List<ItemListNote> itemListNotes;


    public Note getNote() {
        return note;
    }

    public List<ItemListNote> getItemListNotes() {
        return itemListNotes;
    }

    public void setItemListNotes(List<ItemListNote> itemListNotes) {
        this.itemListNotes = itemListNotes;
    }

    public void setNote(Note note) {
        this.note = note;
    }
}
