package com.pasich.mynotes.data.tags;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "tags")
public class Tag {
  @PrimaryKey(autoGenerate = true)
  public int id;

  @ColumnInfo(name = "name")
  private String nameTag;

  @ColumnInfo(name = "visibility")
  private int visibility;
  /**
   * SystemAction - тип Системной метки (1) - добавить метку (2) - все заметки (0) -
   * пользовательский тэг
   */
  @Ignore private int systemAction = 0;

  @Ignore private boolean selected = false;

  public Tag create(String nameTag, int systemAction, boolean selected) {
    this.nameTag = nameTag;
    this.systemAction = systemAction;
    this.selected = selected;

    return this;
  }

  public void create(String nameTag) {
    this.nameTag = nameTag;
  }

  public String getNameTag() {
    return this.nameTag;
  }

  public void setNameTag(String newNameTag) {
    this.nameTag = newNameTag;
  }

  public int getSystemAction() {
    return this.systemAction;
  }

  public void setSystemAction(int arg0) {
    this.systemAction = arg0;
  }

  public boolean getSelected() {
    return this.selected;
  }

  public void setSelected(boolean sel) {
    this.selected = sel;
  }

  public int getVisibility() {
    return this.visibility;
  }

  public void setVisibility(int arg0) {
    this.visibility = arg0;
  }
}
