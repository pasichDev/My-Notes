package com.pasich.mynotes.utils.actionPanel;

import android.view.LayoutInflater;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.pasich.mynotes.databinding.ActionPanelBinding;
import com.pasich.mynotes.utils.actionPanel.interfaces.ManagerViewAction;


public class ActionUtils {

    private static boolean ACTION_ON = false;
    private ActionPanelBinding binding;
    private ConstraintLayout mViewRoot;
    private ManagerViewAction managerViewAction;


    public void createObject(LayoutInflater inflater, ConstraintLayout view) {
        this.mViewRoot = view;
        this.binding = ActionPanelBinding.inflate(inflater);
        this.managerViewAction = (ManagerViewAction) mViewRoot.getContext();
        addActionPanel();
        setListener();
    }


    private void setListener() {
        binding.closeActionPanel.setOnClickListener(v -> closeActionPanel());
        binding.actionPanelDelete.setOnClickListener(v -> managerViewAction.deleteNotes());
        binding.actionPanelShare.setOnClickListener(v -> managerViewAction.shareNotes());
        binding.actionPanelRestore.setOnClickListener(v -> managerViewAction.shareNotes());
    }

    private void addActionPanel() {
        mViewRoot.addView(binding.getRoot());
        createConstraintSetActionPanel();
    }

    private void createConstraintSetActionPanel() {
        ConstraintSet set = new ConstraintSet();
        set.constrainHeight(binding.actionPanel.getId(), ConstraintSet.WRAP_CONTENT);
        set.constrainWidth(binding.actionPanel.getId(), ConstraintSet.WRAP_CONTENT);

        set.connect(
                binding.actionPanel.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 50);
        set.connect(
                binding.actionPanel.getId(),
                ConstraintSet.BOTTOM,
                ConstraintSet.PARENT_ID,
                ConstraintSet.BOTTOM,
                50);

        set.applyTo(mViewRoot);

        binding.actionPanel.setVisibility(View.GONE);
    }

    public void setTrash() {
        binding.actionPanelShare.setVisibility(View.GONE);
        binding.actionPanelDelete.setVisibility(View.GONE);
        binding.actionPanelRestore.setVisibility(View.VISIBLE);
    }

    /**
     * @return - Returns the value of ACTION_ON
     */
    public static boolean getAction() {
        return ACTION_ON;
    }

    /**
     * Set value to ACTION_ON
     *
     * @param arg - (boolean) true/false
     */
    public static void setAction(boolean arg) {
        ACTION_ON = arg;
    }

    /**
     * Activate the visibility of the action panel
     */
    private void activateActionPanel() {
        managerViewAction.activateActionPanel();
        binding.actionPanel.setVisibility(View.VISIBLE);
    }

    /**
     * Deactivate the visibility of the action panel
     */
    private void deactivationActionPanel() {
        managerViewAction.deactivationActionPanel();
        binding.actionPanel.setVisibility(View.GONE);
    }

    /**
     * The method that controls the visibility of the action panel
     */
    public void manageActionPanel(int countChecked) {
        if (countChecked == 0) deactivationActionPanel();
        else if (!getAction() || countChecked == 1) activateActionPanel();
    }

    public void closeActionPanel() {
        managerViewAction.toolCleanChecked();
        deactivationActionPanel();
        setAction(false);
    }
}
