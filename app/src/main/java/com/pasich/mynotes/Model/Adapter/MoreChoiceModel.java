package com.pasich.mynotes.Model.Adapter;

public class MoreChoiceModel {
  private final String name;
  private final int icon;

  public MoreChoiceModel(String name, int icon) {
    this.name = name;
    this.icon = icon;
  }

  public String getName() {
    return this.name;
  }

  public int getIcon() {
    return this.icon;
  }
}
