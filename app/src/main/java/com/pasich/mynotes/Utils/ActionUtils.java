package com.pasich.mynotes.Utils;

import android.view.View;
import android.widget.ImageButton;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.pasich.mynotes.Model.Adapter.NoteItemModel;
import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.Adapters.DefaultListAdapter;

import java.util.ArrayList;
import java.util.List;

public class ActionUtils {

  public final ImageButton actButtonDelete, actButtonClose;
  public final ConstraintLayout actionPanel;
  private final DefaultListAdapter adapter;
  /** Panel close button indicator */
  public final int ID_CLOSE_BUTTON = R.id.actPanelClose;

  public final int ID_DELETE_BUTTON = R.id.actPanelDelete;
  private boolean ACTION_ON = false;

  public ActionUtils(View view, DefaultListAdapter adapter) {
    this.actionPanel = view.findViewById(R.id.actionPanel);
    this.actButtonDelete = actionPanel.findViewById(ID_DELETE_BUTTON);
    this.actButtonClose = actionPanel.findViewById(ID_CLOSE_BUTTON);
    this.adapter = adapter;
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
  private int getCountCheckedItem() {
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
    actionPanel.setVisibility(View.VISIBLE);
  }

  /** Deactivate the visibility of the action panel */
  private void deactivationActionPanel() {
    actionPanel.setVisibility(View.GONE);
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
      if (data.get(i).getChecked()) ArrayChecked.add(i);
    }
    return ArrayChecked;
  }
}
