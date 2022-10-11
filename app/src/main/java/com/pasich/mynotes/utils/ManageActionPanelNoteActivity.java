package com.pasich.mynotes.utils;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

public class ManageActionPanelNoteActivity {

    private final View actionPanel;
    private final int durationAnimation = 200;
    private final ScrollView scrollView;
    private boolean mHideActionPanel = false;
    private final float prefixWidth = 1.25F;
    private int widthActionPanel;

    public ManageActionPanelNoteActivity(ScrollView scrollView, View actionPanel) {
        this.scrollView = scrollView;
        this.actionPanel = actionPanel;
    }

    @SuppressLint("ClickableViewAccessibility")
    public void startListener() {
        scrollView.setOnTouchListener((v, event) -> {
            if (event.getAction() == 2) hideView();
            if (event.getAction() == 1) showView();
            Log.wtf("pasic", "startListener: " + event.getAction());
            return false;
        });

        widthActionPanel = actionPanel.getMeasuredWidth();
    }


    /**
     * Исправить ошибку, из-за которой в widthActionPanel  == 0
     * а также нужно добавить, прейиксы
     */


    private void hideView() {
        if (!getHideActionPanel()) {
            actionPanel.animate().x((actionPanel.getX() + actionPanel.getWidth())).setDuration(durationAnimation).start();
            mHideActionPanel = true;

            Log.wtf("pasic", "listenerView: " + actionPanel.getX() + "/" + actionPanel.getWidth());
        }
    }

    private void showView() {
        if (getHideActionPanel()) {
            actionPanel.animate().x((actionPanel.getX() - actionPanel.getWidth())).setDuration(durationAnimation).start();
            mHideActionPanel = false;
            Log.wtf("pasic", "listenerView: " + actionPanel.getX() + "/" + actionPanel.getWidth());
        }
    }

    public boolean getHideActionPanel() {
        return this.mHideActionPanel;
    }
}
