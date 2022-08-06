package com.pasich.mynotes.data.trash;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.pasich.mynotes.data.notes.Note;


@Entity(
        tableName = "trash",
        indices = {
                @Index(
                        value = {"title"},
                        unique = true)
        })
public class TrashNote {

    @PrimaryKey(autoGenerate = true)
    public int id;

    private String title;
    private String value;
    private long date;
    private String type;

    @Ignore
    private boolean Checked;

    public TrashNote create(String title, String value, long date, String type) {
        this.title = title;
        this.value = value;
        this.date = date;
        this.type = type;
        this.Checked = false;
        return this;
    }

    public TrashNote create(String title, String value, int date) {
        this.title = title;
        this.value = value;
        this.date = date;
        this.type = "note";
        return this;
    }

    /**
     * Метод который реализует конвертирование модели Note в модель TrashNote
     *
     * @param note - заметка которую нужно конвертировать
     * @return - получения заметка в новой модели
     */
    public TrashNote convertNote(Note note) {
        return new TrashNote().create(note.getTitle(), note.getValue(), note.getDate(), note.getType());
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

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

}
