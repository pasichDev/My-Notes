package com.pasich.mynotes.utils;

import android.view.View;

import com.pasich.mynotes.models.adapter.NoteModel;
import com.pasich.mynotes.utils.Adapters.NotesAdapter;
import com.pasich.mynotes.view.customView.ActionPanel;

import java.util.ArrayList;
import java.util.List;

public class ActionUtils extends ActionPanel implements View.OnClickListener {

  private final NotesAdapter adapter;
  private final int PAYLOAD_BACKGROUND = 22;
  private final ArrayList<Long> ArrayChecked = new ArrayList<>();
  /** Panel close button indicator */
  private boolean ACTION_ON = false;

  public ActionUtils(View view, NotesAdapter adapter, int objectActivity) {
    super(view, objectActivity);
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
  private List<NoteModel> getDataAdapter() {
    return adapter.getData();
  }

  /**
   * @return - Number of marked items (int)
   */
  public int getCountCheckedItem() {
    List<NoteModel> data = getDataAdapter();
    int count = 0;
    for (int i = 0; i < data.size(); i++) {
      count = data.get(i).getChecked() ? count + 1 : count;
    }
    return count;
  }

  /** Clear all marks */
  private void checkedClean() {
    List<NoteModel> data = getDataAdapter();
    for (int i = 0; i < data.size(); i++) {
      if (data.get(i).getChecked()) data.get(i).setChecked(false);
      adapter.notifyItemChanged(i, PAYLOAD_BACKGROUND);
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
  public void isCheckedItemFalse(int NoteID) {
    if (getCountCheckedItem() == 0) {
      getArrayChecked().clear();
      closeActionPanel();
    } else {
      getArrayChecked().remove((long) NoteID);
    }
  }

  /** Action panel control when adding checkmark */
  public void isCheckedItem(int noteID) {
    if (!getArrayChecked().contains(noteID)) getArrayChecked().add((long) noteID);
    else getArrayChecked().remove((long) noteID);
    if (!(getAction())) setAction(true);
  }

  /** The method that disables the actionPanel when manually accessed from under the key */
  public void closeActionPanel() {
    checkedClean();
    deactivationActionPanel();
    setAction(false);
    getArrayChecked().clear();
  }

  public ArrayList<Long> getArrayChecked() {
    return this.ArrayChecked;
  }

  public void selectItemAction(int item) {
    NoteModel noteItem = getDataAdapter().get(item);
    if (noteItem.getChecked()) {
      noteItem.setChecked(false);
      isCheckedItemFalse(noteItem.getId());
    } else {
      isCheckedItem(noteItem.getId());
      noteItem.setChecked(true);
    }
    manageActionPanel();
    adapter.notifyItemChanged(item, PAYLOAD_BACKGROUND);
  }

  @Override
  public void onClick(View v) {
    if (v.getId() == getClosePanelButton().getId()) {
      closeActionPanel();
    }
  }
}
