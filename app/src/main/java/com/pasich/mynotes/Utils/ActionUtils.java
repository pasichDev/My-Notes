package com.pasich.mynotes.Utils;

import android.view.View;

import com.pasich.mynotes.Models.Adapter.NoteItemModel;
import com.pasich.mynotes.Utils.Adapters.ListNotesAdapter;
import com.pasich.mynotes.View.CustomView.ActionPanelDialogUI;

import java.util.ArrayList;
import java.util.List;

public class ActionUtils extends ActionPanelDialogUI implements View.OnClickListener {

  private final ListNotesAdapter adapter;
  /** Panel close button indicator */
  private boolean ACTION_ON = false;

  public ActionUtils(View view, ListNotesAdapter adapter, int objectBind, int objectActivity) {
    super(view, objectBind, objectActivity);
    this.adapter = adapter;
    getActionPanel().setVisibility(View.GONE);
    getClosePanelButton().setOnClickListener(this);
  }

  /**
   * @return - Returns the value of ACTION_ON
   */
  public boolean getAction() {
    return this.ACTION_ON;
  }

  /**
   * Set value to ACTION_ON
   *
   * @param arg - (boolean) true/false
   */
  public void setAction(boolean arg) {
    this.ACTION_ON = arg;
  }

  /**
   * Returns an array of data from the adapter
   *
   * @return - data adapter
   */
  private List<NoteItemModel> getDataAdapter() {
    return adapter.getData();
  }

  /**
   * @return - Number of marked items (int)
   */
  public int getCountCheckedItem() {
    List<NoteItemModel> data = getDataAdapter();
    int count = 0;
    for (int i = 0; i < data.size(); i++) {
      count = data.get(i).getChecked() ? count + 1 : count;
    }
    return count;
  }

  /** Clear all marks */
  private void checkedClean() {
    List<NoteItemModel> data = getDataAdapter();
    for (int i = 0; i < data.size(); i++) {
      if (data.get(i).getChecked()) data.get(i).setChecked(false);
    }
  }

  /** Activate the visibility of the action panel */
  private void activateActionPanel() {
    getActionPanel().setVisibility(View.VISIBLE);
  }

  /** Deactivate the visibility of the action panel */
  private void deactivationActionPanel() {
    getActionPanel().setVisibility(View.GONE);
  }

  /** The method that controls the visibility of the action panel */
  public void manageActionPanel() {
    int countChecked = getCountCheckedItem();
    if (countChecked == 0) deactivationActionPanel();
    else if (!getAction() || countChecked == 1) activateActionPanel();
  }

  /** Action panel control when unchecked */
  public void isCheckedItemFalse() {
    if (getCountCheckedItem() == 0) {
      closeActionPanel();
    }
  }

  /** Action panel control when adding checkmark */
  public void isCheckedItem() {
    if (!(getAction())) setAction(true);
  }

  /** The method that disables the actionPanel when manually accessed from under the key */
  public void closeActionPanel() {
    checkedClean();
    deactivationActionPanel();
    setAction(false);
    adapter.notifyDataSetChanged();
  }

  /**
   * Method that returns all checked elements in an array
   *
   * @return - checked elements array
   */
  public ArrayList<Integer> getArrayChecked() {
    List<NoteItemModel> data = getDataAdapter();
    ArrayList<Integer> ArrayChecked = new ArrayList<>();
    for (int i = 0; i < data.size(); i++) {
      if (data.get(i).getChecked()) ArrayChecked.add(data.get(i).getId());
    }
    return ArrayChecked;
  }

  public void selectItemAction(int item) {
    NoteItemModel noteItem = getDataAdapter().get(item);
    if (noteItem.getChecked()) {
      noteItem.setChecked(false);
      isCheckedItemFalse();
    } else {
      isCheckedItem();
      noteItem.setChecked(true);
    }
    manageActionPanel();
    adapter.notifyDataSetChanged();
  }

  @Override
  public void onClick(View v) {
    if (v.getId() == getClosePanelButton().getId()) {
      closeActionPanel();
    }
  }
}
