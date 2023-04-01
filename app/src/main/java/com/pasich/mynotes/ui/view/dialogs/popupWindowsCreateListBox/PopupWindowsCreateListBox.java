package com.pasich.mynotes.ui.view.dialogs.popupWindowsCreateListBox;


import android.content.Context;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.pasich.mynotes.databinding.ViewPopupCreateListBoxBinding;

public class PopupWindowsCreateListBox {

    private final PopupWindow mPopupWindows;
    private final ViewPopupCreateListBoxBinding mBinding;
    private final int widthAnchor;
    private View mAnchor;
    private final PopupWindowsCreateListBoxHelper createListBoxHelper;

    public PopupWindowsCreateListBox(LayoutInflater layoutInflater, View anchor, PopupWindowsCreateListBoxHelper createListBoxHelper) {
        this.mBinding = ViewPopupCreateListBoxBinding.inflate(layoutInflater);
        this.mAnchor = anchor;
        this.createListBoxHelper = createListBoxHelper;
        this.widthAnchor = anchor.getWidth();
        this.mPopupWindows = new PopupWindow(mBinding.getRoot(), RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);

        mBinding.getRoot().measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        onVibrate();
        onSettingsView();
    }


    private void onSettingsView() {
        getPopupWindows().setElevation(10);
        getPopupWindows().setOnDismissListener(this::setOnDismissListener);
        initListeners();
        getPopupWindows().showAsDropDown(mAnchor, widthAnchor, -400);
    }


    private void onVibrate() {
        Vibrator vibrator = (Vibrator) mBinding.getRoot().getContext().getSystemService(Context.VIBRATOR_SERVICE);

        if (vibrator.hasVibrator()) {
            vibrator.vibrate(100L);
        }
    }


    private void initListeners() {
        mBinding.creteListToDataNote.setOnClickListener(v -> {
            createListBoxHelper.createListForData();
            getPopupWindows().dismiss();

        });
        mBinding.addListToNote.setOnClickListener(v -> {
            createListBoxHelper.addListToNote();
            getPopupWindows().dismiss();

        });
    }


    public PopupWindow getPopupWindows() {
        return mPopupWindows;
    }

    public void setOnDismissListener() {
        mAnchor = null;
        mBinding.creteListToDataNote.setOnClickListener(null);
        mBinding.addListToNote.setOnClickListener(null);
    }
}
