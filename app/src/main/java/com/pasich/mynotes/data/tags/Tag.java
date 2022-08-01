package com.pasich.mynotes.data.tags;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "tags",
    indices = {
      @Index(
          value = {"name"},
          unique = true)
    })
public class Tag {

  @PrimaryKey(autoGenerate = true)
  public long id;

  @NonNull
  @ColumnInfo(name = "name")
  private String nameTag;

  @ColumnInfo(name = "visibility")
  private int visibility = 0;
  /**
   * SystemAction - тип Системной метки (1) - добавить метку (2) - все заметки (0) -
   * пользовательский тэг
   */
  @ColumnInfo(name = "systemAction")
  private int systemAction = 0;


  @Ignore
  private boolean selected = false;

  public Tag create(String nameTag, int systemAction) {
    this.nameTag = nameTag;
    this.systemAction = systemAction;

    return this;
  }

  public Tag create(String nameTag) {
    this.nameTag = nameTag;
    return this;
  }

  @NonNull
  public String getNameTag() {
    return this.nameTag;
  }

  public void setNameTag(@NonNull String newNameTag) {
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
