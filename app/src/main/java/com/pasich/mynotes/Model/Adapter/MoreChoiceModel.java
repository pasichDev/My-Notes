package com.pasich.mynotes.Model.Adapter;

public class MoreChoiceModel {
  private final String name;
  private final String action;
  private final int icon;

  public MoreChoiceModel(String name, int icon, String action) {
    this.name = name;
    this.action = action;
    this.icon = icon;
  }

  public String getName() {
    return this.name;
  }

  public String getAction() {
    return this.action;
  }

  public int getIcon() {
    return this.icon;
  }
}
