package com.pasich.mynotes.data.tags;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "tags")
public class Tag {

  @ColumnInfo(name = "name")
  private String nameTag;

  @ColumnInfo(name = "visibility")
  private int visibility;

  @Ignore private final int SystemAction;
  @Ignore private boolean selected;

  /**
   * Конструктор данных метки
   *
   * @param nameTag - название метки
   * @param SystemAction - тип Системной метки (1) - добавить метку (2) - все заметки (0) -
   *     пользовательский тэг
   */
  public Tag(String nameTag, int SystemAction, boolean selected) {
    this.nameTag = nameTag;
    this.SystemAction = SystemAction;
    this.selected = selected;
  }

  public String getNameTag() {
    return this.nameTag;
  }

  public void setNameTag(String newNameTag) {
    this.nameTag = newNameTag;
  }

  public int getSystemAction() {
    return this.SystemAction;
  }

  public boolean getSelected() {
    return this.selected;
  }

  public void setSelected(boolean sel) {
    this.selected = sel;
  }
}
