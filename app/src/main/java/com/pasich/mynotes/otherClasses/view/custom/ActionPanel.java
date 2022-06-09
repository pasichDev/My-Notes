package com.pasich.mynotes.otherClasses.view.custom;

import android.content.res.ColorStateList;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

import com.pasich.mynotes.R;

public class ActionPanel {

  private final android.view.View rootView;
  private final ConstraintLayout actionPanel;
  private final LinearLayout linearLayoutButtons;
  private final ImageButton closePanelButton;
  /** The activity MyView to which the panel will be added */
  private final ConstraintLayout rootConstraintLayout;

  public ActionPanel(android.view.View rootView, int objectActivity) {
    this.rootConstraintLayout = rootView.findViewById(objectActivity);
    this.rootView = rootView;
    this.actionPanel = createActionPanel();
    this.linearLayoutButtons = createLinearLayoutsButtons();
    this.closePanelButton = createClosePanelButton();

    initializationActionPanel();
  }

  private void initializationActionPanel() {
    actionPanel.addView(getLinearLayoutButtons());
    rootConstraintLayout.addView(getActionPanel());
    createConstraintSetActionPanel();
    addButtonToLinearLayout(getClosePanelButton());
  }

  /**
   * The method that creates the action Panel
   *
   * @return - action panel
   */
  private ConstraintLayout createActionPanel() {
    ConstraintLayout actionPanel = new ConstraintLayout(rootView.getContext());
    actionPanel.setBackground(
        ContextCompat.getDrawable(
            rootView.getRootView().getContext(), R.drawable.background_action_panel));
    actionPanel.setId(R.id.actionPanel);
    return actionPanel;
  }

  /**
   * @return - action panel
   */
  public ConstraintLayout getActionPanel() {
    return this.actionPanel;
  }

  /**
   * The method that creates a linearLayout in which we will add buttons
   *
   * @return - linearLayout
   */
  private LinearLayout createLinearLayoutsButtons() {
    LinearLayout LinearLayoutButtons = new LinearLayout(rootView.getContext());
    LinearLayoutButtons.setOrientation(LinearLayout.HORIZONTAL);
    return LinearLayoutButtons;
  }

  /**
   * @return - linearLayout
   */
  private LinearLayout getLinearLayoutButtons() {
    return this.linearLayoutButtons;
  }

  private ImageButton createClosePanelButton() {
    ImageButton closePanel = new ImageButton(rootView.getContext());
    closePanel.setImageResource(R.drawable.ic_close_search_view);
    closePanel.setBackground(null);
    closePanel.setImageTintList(
        ColorStateList.valueOf(rootView.getResources().getColor(R.color.white, null)));
    closePanel.setId(R.id.closeButtonPanel);
    return closePanel;
  }

  /**
   * Method that returns the close button of the panel
   *
   * @return - button close panel
   */
  public ImageButton getClosePanelButton() {
    return this.closePanelButton;
  }

  /** Method that creates bindings for the panel */
  private void createConstraintSetActionPanel() {
    ConstraintSet set = new ConstraintSet();
    set.constrainHeight(actionPanel.getId(), ConstraintSet.WRAP_CONTENT);
    set.constrainWidth(actionPanel.getId(), ConstraintSet.WRAP_CONTENT);

    set.connect(
        actionPanel.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 50);
    set.connect(
        actionPanel.getId(),
        ConstraintSet.BOTTOM,
        ConstraintSet.PARENT_ID,
        ConstraintSet.BOTTOM,
        50);

    set.applyTo(rootConstraintLayout);
  }

  /**
   * Method that adds an object to a LinearLayout
   *
   * @param buttonView - button to add
   */
  private void addButtonToLinearLayout(android.view.View buttonView) {
    getLinearLayoutButtons().addView(buttonView);
  }

  /**
   * Public method for adding a button to a panel
   *
   * @param srcImage - button image resource
   * @param idButton - id for the button that will be assigned to it and implemented in ids.xml
   */
  public void addButtonToActionPanel(int srcImage, int idButton) {
    ImageButton addButton = new ImageButton(rootView.getContext());
    addButton.setImageResource(srcImage);
    addButton.setBackground(null);
    addButton.setImageTintList(
        ColorStateList.valueOf(rootView.getResources().getColor(R.color.white, null)));
    addButton.setId(idButton);
    addButtonToLinearLayout(addButton);
  }
}
