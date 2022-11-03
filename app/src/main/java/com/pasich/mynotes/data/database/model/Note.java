package com.pasich.mynotes.data.database.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "notes",
        indices = {
                @Index(
                        value = {"value"},
                        unique = true)
        })
public class Note {


    @PrimaryKey(autoGenerate = true)
    public int id;

    private String title;
    private String value;
    private long date;
    private String tag;

    @Ignore
    private boolean Checked;

    public Note create(String title, String value, long date, String tag) {
        this.title = title;
        this.tag = tag;
        this.value = value;
        this.date = date;
        this.Checked = false;
        return this;
    }

    public Note create(String title, String value, long date) {
        this.title = title;
        this.tag = "";
        this.value = value;
        this.date = date;
        this.Checked = false;
        return this;
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean getChecked() {
        return this.Checked;
    }

    public void setChecked(boolean arg) {
        this.Checked = arg;
    }

    public long getDate() {
        return this.date;
    }

    public void setDate(long date) {
        this.date = date;
    }

}
