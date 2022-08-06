package com.pasich.mynotes.data.notes;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Comparator;

@Entity(
        tableName = "notes",
        indices = {
                @Index(
                        value = {"title"},
                        unique = true)
        })
public class Note {

  public static Comparator<Note> COMPARE_BY_TITLE_REVERSE =
          (one, other) -> other.getTitle().compareTo(one.getTitle());
  public static Comparator<Note> COMPARE_BY_TITLE_SORT =
          (one, other) -> one.getTitle().compareTo(other.getTitle());


 /* public static Comparator<Note> COMPARE_BY_DATE_REVERSE =
          (one, other) -> {
            return  (int)  other.getDate().compareTo(one.getDate());
          };
  public static Comparator<Note> COMPARE_BY_DATE_SORT =
          (one, other) -> one.getDate().compareTo(other.getDate());
*/

  public static Comparator<Note> COMPARE_BY_DATE_REVERSE = (e1, e2) -> Math.toIntExact((long) (e1.getDate() - e2.getDate()));
  public static Comparator<Note> COMPARE_BY_DATE_SORT = (e1, e2) -> Math.toIntExact((long) (e2.getDate() - e1.getDate()));


  @PrimaryKey(autoGenerate = true)
  public int id;

  private String title;
  private String value;
  private long date;
  private String type;
  private String tag;

  @Ignore private boolean Checked;

  public Note create(String title, String value, long date, String type, String tag) {
    this.title = title;
    this.tag = tag;
    this.value = value;
    this.date = date;
    this.type = type;
    this.Checked = false;
    return this;
  }

  public Note create(String title, String value, long date) {
    this.title = title;
    this.tag = "";
    this.value = value;
    this.date = date;
    this.type = "note";
    this.Checked = false;
    return this;
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

  public void setType(String type) {
    this.type = type;
  }

  public String getType() {
    return this.type;
  }
}
