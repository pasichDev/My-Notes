package com.pasich.mynotes.utils;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

import javax.inject.Inject;

public class ManageActionPanelNoteActivity {

    private final int durationAnimation = 200;
    private final float prefixWidth = 1.25F;
    private View actionPanel;
    private boolean mHideActionPanel = false;
    private float widthActionPanel;
    private float actionPanelX;

    @Inject
    public ManageActionPanelNoteActivity() {

    }

    @SuppressLint("ClickableViewAccessibility")
    public void startListener(ScrollView scrollView, View actionPanel) {
        this.actionPanel = actionPanel;
        this.widthActionPanel = actionPanel.getWidth() * prefixWidth;
        this.actionPanelX = actionPanel.getX();
        scrollView.setOnTouchListener((v, event) -> {

            if (event.getAction() == 2 && !mHideActionPanel) {
                hideView();

                Log.wtf("pasic", "hide");
            }
            if (event.getAction() == 1 && mHideActionPanel) {
                showView();
                Log.wtf("pasic", "show");
            }

            Log.wtf("pasic", "startListener: " + event.getAction() + "/");

            return false;
        });
    }

    private void hideView() {
        mHideActionPanel = true;
        actionPanel.animate().x((actionPanelX + widthActionPanel)).setDuration(durationAnimation).start();

        Log.wtf("pasic", "x formuls: " + actionPanelX + "/" + widthActionPanel);
        Log.wtf("pasic", "x core: " + actionPanel.getX() + "/" + actionPanel.getWidth() * prefixWidth);
        Log.wtf("pasic", "hideView");

    }

    private void showView() {
        mHideActionPanel = false;
        actionPanel.animate().x((actionPanel.getX() - widthActionPanel)).setDuration(durationAnimation).start();

    }

}
