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

    private final boolean isVisibleList;

    public PopupWindowsCreateListBox(LayoutInflater layoutInflater, View anchor, boolean isVisibleList, PopupWindowsCreateListBoxHelper createListBoxHelper) {
        this.mBinding = ViewPopupCreateListBoxBinding.inflate(layoutInflater);
        this.mAnchor = anchor;
        this.createListBoxHelper = createListBoxHelper;
        this.mPopupWindows = new PopupWindow(mBinding.getRoot(), RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        this.isVisibleList = isVisibleList;
        mBinding.deleteList.setVisibility(isVisibleList ? View.VISIBLE : View.GONE);
        mBinding.creteListToDataNote.setVisibility(!isVisibleList ? View.VISIBLE : View.GONE);
        mBinding.addListToNote.setVisibility(!isVisibleList ? View.VISIBLE : View.GONE);
        mBinding.getRoot().measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        onSettingsView();
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
        if (isVisibleList) {
            mBinding.deleteList.setOnClickListener(v -> {
                createListBoxHelper.deleteList();
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
        if (isVisibleList) {

            mBinding.deleteList.setOnClickListener(null);
        } else {

            mBinding.creteListToDataNote.setOnClickListener(null);
            mBinding.addListToNote.setOnClickListener(null);
        }

    }
}
