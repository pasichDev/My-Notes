package com.pasich.mynotes.utils.actionPanel;

import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.pasich.mynotes.R;
import com.pasich.mynotes.databinding.ActionPanelBinding;
import com.pasich.mynotes.utils.actionPanel.interfaces.ManagerViewAction;

import javax.inject.Inject;


public class ActionUtils {

    @Inject
    public ActionUtils() {
    }

    private static boolean ACTION_ON = false;
    private ActionPanelBinding binding;
    private CoordinatorLayout mViewRoot;
    private ManagerViewAction managerViewAction;


    public void createObject(CoordinatorLayout view) {
        this.mViewRoot = view;
        this.binding = ActionPanelBinding.bind(view.findViewById(R.id.actionInclude));
        this.managerViewAction = (ManagerViewAction) mViewRoot.getContext();
        setListener();
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
        mViewRoot.findViewById(R.id.actionInclude).setVisibility(View.VISIBLE);
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
        managerViewAction.toolCleanChecked();
        deactivationActionPanel();
        setAction(false);
    }
}
