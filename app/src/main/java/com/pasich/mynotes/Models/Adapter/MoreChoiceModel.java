package com.pasich.mynotes.Models.Adapter;

public class MoreChoiceModel {
  private final String name;
  private final String action;
  private final int icon;
  private boolean selected;

  public MoreChoiceModel(String name, int icon, String action, boolean selected) {
    this.name = name;
    this.action = action;
    this.icon = icon;
    this.selected = selected;
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

  public boolean getSelected() {
    return this.selected;
  }
}
