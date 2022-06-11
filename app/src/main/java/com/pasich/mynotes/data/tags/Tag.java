package com.pasich.mynotes.data.tags;

import android.arch.persistence.room.Entity;

@Entity(tableName = "movies")
public class Tag {

  private String nameTag;
  private final int SystemAction;
  private boolean selected;
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
