package com.pasich.mynotes.data.tags;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "tags")
public class Tag {
  @PrimaryKey public int id;

  @ColumnInfo(name = "name")
  public String nameTag;

  @ColumnInfo(name = "visibility")
  public int visibility;
  /**
   * @param SystemAction - тип Системной метки (1) - добавить метку (2) - все заметки (0) -
   *     пользовательский тэг
   */
  @Ignore public int systemAction = 0;

  @Ignore public boolean selected = false;

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
