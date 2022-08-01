package com.pasich.mynotes.base;

public class ChoiceModel {
  private final String name;
  private final String action;
  private final int icon;
  private boolean selected;

  public ChoiceModel(String name, int icon, String action, boolean selected) {
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
