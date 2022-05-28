package com.pasich.mynotes.View.CustomView;

import android.content.res.ColorStateList;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

import com.pasich.mynotes.R;

public class ActionPanelDialogUI {

  private final View rootView;
  private final ConstraintLayout actionPanel;
  private final LinearLayout linearLayoutButtons;
  private final ImageButton closePanelButton;
  /** Identifier of the object to which we will bind the action Panel */
  private final int objectBind;
  /** The activity view to which the panel will be added */
  private final ConstraintLayout rootConstraintLayout;

  public ActionPanelDialogUI(View rootView, int objectBind) {
    this.rootConstraintLayout = rootView.findViewById(R.id.activity_main);
    this.objectBind = objectBind;
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

  public LinearLayout.LayoutParams getLayoutsParamsDefault() {
    LinearLayout.LayoutParams lp =
        new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    lp.setMargins(12, 12, 12, 12);
    return lp;
  }

  private ImageButton createClosePanelButton() {
    ImageButton closePanel = new ImageButton(rootView.getContext());
    closePanel.setImageResource(R.drawable.ic_close);
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

    set.connect(actionPanel.getId(), ConstraintSet.RIGHT, objectBind, ConstraintSet.LEFT, 30);
    set.connect(actionPanel.getId(), ConstraintSet.BOTTOM, objectBind, ConstraintSet.BOTTOM, 0);

    set.applyTo(rootConstraintLayout);
  }

  /**
   * Method that adds an object to a LinearLayout
   *
   * @param buttonView - button to add
   */
  private void addButtonToLinearLayout(View buttonView) {
    getLinearLayoutButtons().addView(buttonView, getLayoutsParamsDefault());
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
