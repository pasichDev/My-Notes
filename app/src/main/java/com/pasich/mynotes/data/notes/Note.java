package com.pasich.mynotes.data.notes;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "notes",
    indices = {
      @Index(
          value = {"title"},
          unique = true)
    })
public class Note {

  @PrimaryKey(autoGenerate = true)
  public int id;

  private String title;
  private String value;
  private String date;
  private String type;
  private String tag;

  @Ignore private boolean Checked;

  public Note create(String title, String value, String date, String type, String tag) {
    this.title = title;
    this.tag = tag;
    this.value = value;
    this.date = date;
    this.type = type;
    this.Checked = false;
    return this;
  }

  public Note create(String title, String value, String date) {
    this.title = title;
    this.tag = "";
    this.value = value;
    this.date = date;
    this.type = "note";
    this.Checked = false;
    return this;
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

  public String getDate() {
    return this.date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getType() {
    return this.type;
  }
}
