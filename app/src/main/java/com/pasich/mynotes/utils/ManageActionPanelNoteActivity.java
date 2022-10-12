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
    private int widthActionPanel;

    @Inject
    public ManageActionPanelNoteActivity() {

    }

    @SuppressLint("ClickableViewAccessibility")
    public void startListener(ScrollView scrollView, View actionPanel) {
        this.actionPanel = actionPanel;
        this.widthActionPanel = (int) (actionPanel.getMeasuredWidth() * prefixWidth);
        scrollView.setOnTouchListener((v, event) -> {
            if (event.getAction() == 2) hideView();
            if (event.getAction() == 1) showView();
            Log.wtf("pasic", "startListener: " + event.getAction());
            return false;
        });
    }

    private void hideView() {
        if (!mHideActionPanel) {
            actionPanel.animate().x((actionPanel.getX() + widthActionPanel)).setDuration(durationAnimation).start();
            mHideActionPanel = true;
        }
    }

    private void showView() {
        if (mHideActionPanel) {
            actionPanel.animate().x((actionPanel.getX() - widthActionPanel)).setDuration(durationAnimation).start();
            mHideActionPanel = false;
        }
    }

    public boolean getHideActionPanel() {
        return this.mHideActionPanel;
    }
}
