package com.pasich.mynotes.ui.view.dialogs.popupWindowsCreateListBox;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.pasich.mynotes.databinding.ViewPopupCreateListBoxBinding;

public class PopupWindowsCreateListBox {

    private final PopupWindow mPopupWindows;
    private final ViewPopupCreateListBoxBinding mBinding;
    private final PopupWindowsCreateListBoxHelper createListBoxHelper;
    private View mAnchor;
    //isVisibleListItems,isValidTextNote
    private final boolean[] statusHelper;

    public PopupWindowsCreateListBox(LayoutInflater layoutInflater, View anchor, boolean[] statusHelper, PopupWindowsCreateListBoxHelper createListBoxHelper) {
        this.mBinding = ViewPopupCreateListBoxBinding.inflate(layoutInflater);
        this.mAnchor = anchor;
        this.createListBoxHelper = createListBoxHelper;
        this.mPopupWindows = new PopupWindow(mBinding.getRoot(), RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        this.statusHelper = statusHelper;
        visibleItems();
        mBinding.getRoot().measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        onSettingsView();
    }

    private void visibleItems() {
        mBinding.creteListToDataNote.setVisibility(!statusHelper[0] && statusHelper[1] ? View.VISIBLE : View.GONE);
        mBinding.deleteList.setVisibility(statusHelper[0] ? View.VISIBLE : View.GONE);
        mBinding.convertToNote.setVisibility(statusHelper[0] ? View.VISIBLE : View.GONE);
        mBinding.addListToNote.setVisibility(!statusHelper[0] ? View.VISIBLE : View.GONE);

    }


    private void onSettingsView() {
        getPopupWindows().setElevation(10);
        getPopupWindows().setOnDismissListener(this::setOnDismissListener);
        getPopupWindows().setInputMethodMode(PopupWindow.INPUT_METHOD_NOT_NEEDED);
        initListeners();

        int y = (mBinding.getRoot().getMeasuredHeight() + mAnchor.getHeight()) + 40;
        int x = (mAnchor.getWidth() / 2);
        getPopupWindows().showAsDropDown(mAnchor, x, -y);

    }


    private void initListeners() {
        if (statusHelper[0]) {
            mBinding.deleteList.setOnClickListener(v -> {
                createListBoxHelper.deleteList();
                getPopupWindows().dismiss();

            });
            mBinding.convertToNote.setOnClickListener(v -> {
                createListBoxHelper.convertToNote();
                getPopupWindows().dismiss();

            });
        } else {
            mBinding.creteListToDataNote.setOnClickListener(v -> {
                createListBoxHelper.createListForData();
                getPopupWindows().dismiss();

            });
            mBinding.addListToNote.setOnClickListener(v -> {
                createListBoxHelper.addListToNote();
                getPopupWindows().dismiss();

            });
        }


    }


    public PopupWindow getPopupWindows() {
        return mPopupWindows;
    }

    public void setOnDismissListener() {
        mAnchor = null;
        if (statusHelper[0]) {
            mBinding.convertToNote.setOnClickListener(null);
            mBinding.deleteList.setOnClickListener(null);
        } else {

            mBinding.creteListToDataNote.setOnClickListener(null);
            mBinding.addListToNote.setOnClickListener(null);
        }

    }
}
