package com.pasich.mynotes.Model.Adapter;

import android.util.Log;
import android.view.View;

public class MoreChoiceModel {
  private final String name;
  private final String action;
  private final int icon;
  private View itemView;

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

  public void setItemView(View v) {
    Log.wtf("pasic", "setItemView ->" + v);
    itemView = v;
  }

  public View getView() {
    Log.wtf("pasic", "getView ->" + itemView);

    return itemView;
  }
}
