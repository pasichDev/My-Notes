package com.pasich.mynotes.data.model;

import android.util.Log;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "listItemsNote")
public class ItemListNote {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String value;
    private int idNote;
    private int dragPosition;
    private boolean isChecked;
    private boolean isTrash;
    @Ignore
    private boolean isSystem;


    public ItemListNote() {
    }

    public ItemListNote(String value, int idNote, boolean isSystem) {
        this.idNote = idNote;
        this.value = value;
        this.isSystem = isSystem;
        this.dragPosition = 0;
    }

    public ItemListNote(String value, int idNote, int dragPos) {
        this.idNote = idNote;
        this.value = value;
        this.isSystem = false;
        this.dragPosition = dragPos;
    }

    public int getId() {
        return id;
    }

    public int getIdNote() {
        return idNote;
    }

    public String getValue() {
        return value;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public void setValue(String value) {
        Log.wtf("xxx", "setValue: " + value);
        this.value = value;
    }

    public void setIdNote(int idNote) {
        this.idNote = idNote;
    }

    public boolean isTrash() {
        return isTrash;
    }

    public void setTrash(boolean trash) {
        isTrash = trash;
    }

    public boolean isSystem() {
        return isSystem;
    }

    public void setSystem(boolean system) {
        isSystem = system;
    }

    public int getDragPosition() {
        return dragPosition;
    }

    public void setDragPosition(int dragPosition) {
        this.dragPosition = dragPosition;
    }

    public void setId(int id) {
        this.id = id;
    }
}
