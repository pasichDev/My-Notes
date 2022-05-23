package com.pasich.mynotes.Utils;

import android.view.View;
import android.widget.ImageButton;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.pasich.mynotes.Model.Adapter.NoteItemModel;
import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.Adapters.DefaultListAdapter;

import java.util.List;

public class ActionUtils {

  public final ImageButton actButtonDelete, actButtonClose;
  public final ConstraintLayout actionPanel;
  private final DefaultListAdapter adapter;
  private boolean ACTION_ON = false;
  public final int ID_CLOSE_BUTTON = R.id.actPanelDelete;

  public ActionUtils(View view, DefaultListAdapter adapter) {
    this.actionPanel = view.findViewById(R.id.actionPanel);
    this.actButtonDelete = actionPanel.findViewById(ID_CLOSE_BUTTON);
    this.actButtonClose = actionPanel.findViewById(R.id.actPanelClose);
    this.adapter = adapter;
  }

  public boolean getAction() {
    return this.ACTION_ON;
  }

  public void setAction(boolean arg) {
    this.ACTION_ON = arg;
  }

  private List<NoteItemModel> getDataAdapter() {
    return adapter.getData();
  }

  private int getCountCheckedItem() {
    List<NoteItemModel> data = getDataAdapter();
    int count = 0;
    for (int i = 0; i < data.size(); i++) {
      count = data.get(i).getChecked() ? count + 1 : count;
    }
    return count;
  }

  private void checkedClean() {
    List<NoteItemModel> data = getDataAdapter();
    for (int i = 0; i < data.size(); i++) {
      if (data.get(i).getChecked()) data.get(i).setChecked(false);
    }
  }

  private void activateActionPanel() {
    actionPanel.setVisibility(View.VISIBLE);
  }

  private void deactivationActionPanel() {
    actionPanel.setVisibility(View.GONE);
  }

  public void manageActionPanel() {
    if (getCountCheckedItem() == 0) deactivationActionPanel();
    else if (!getAction()) activateActionPanel();
  }

  public void isCheckedItemFalse() {
    if (getCountCheckedItem() == 0) {
      setAction(false);
      checkedClean();
      deactivationActionPanel();
    }
  }

  public void isCheckedItem() {
    if (!(getAction())) setAction(true);
    activateActionPanel();
  }

  public void closeActionPanel() {
    checkedClean();
    deactivationActionPanel();
  }
}
