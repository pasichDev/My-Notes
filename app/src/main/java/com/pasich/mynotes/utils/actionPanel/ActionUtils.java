package com.pasich.mynotes.utils.actionPanel;

import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.pasich.mynotes.R;
import com.pasich.mynotes.databinding.ActionPanelBinding;
import com.pasich.mynotes.utils.actionPanel.interfaces.ManagerViewAction;

import javax.inject.Inject;

import dagger.hilt.android.scopes.ActivityScoped;

@ActivityScoped
public class ActionUtils {
    private static boolean ACTION_ON = false;
    private final ActionPanelBinding binding;
    private final CoordinatorLayout mViewRoot;
    private final ManagerViewAction managerViewAction;

    @Inject
    public ActionUtils(CoordinatorLayout view) {
        this.mViewRoot = view;
        this.binding = ActionPanelBinding.bind(view.findViewById(R.id.actionInclude));
        this.managerViewAction = (ManagerViewAction) mViewRoot.getContext();

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

    private void setListener() {
        binding.closeActionPanel.setOnClickListener(v -> closeActionPanel());
        binding.actionPanelDelete.setOnClickListener(v -> managerViewAction.deleteNotes());
        binding.actionPanelShare.setOnClickListener(v -> managerViewAction.shareNotes());
        binding.actionPanelRestore.setOnClickListener(v -> managerViewAction.restoreNotes());
    }

    public void setTrash() {
        binding.actionPanelShare.setVisibility(View.GONE);
        binding.actionPanelDelete.setVisibility(View.GONE);
        binding.actionPanelRestore.setVisibility(View.VISIBLE);
    }

    /**
     * Activate the visibility of the action panel
     */
    private void activateActionPanel() {
        managerViewAction.activateActionPanel();
        mViewRoot.findViewById(R.id.actionInclude).setVisibility(View.VISIBLE);
        setListener();
    }

    /**
     * Deactivate the visibility of the action panel
     */
    private void deactivationActionPanel() {
        managerViewAction.deactivationActionPanel();
        mViewRoot.findViewById(R.id.actionInclude).setVisibility(View.GONE);
    }

    /**
     * The method that controls the visibility of the action panel
     */
    public void manageActionPanel(int countChecked) {
        if (countChecked == 0) deactivationActionPanel();
        else if (!getAction() || countChecked == 1) activateActionPanel();
    }

    public void closeActionPanel() {
        binding.closeActionPanel.setOnClickListener(null);
        binding.actionPanelDelete.setOnClickListener(null);
        binding.actionPanelShare.setOnClickListener(null);
        binding.actionPanelRestore.setOnClickListener(null);
        managerViewAction.toolCleanChecked();
        deactivationActionPanel();
        setAction(false);
    }
}
