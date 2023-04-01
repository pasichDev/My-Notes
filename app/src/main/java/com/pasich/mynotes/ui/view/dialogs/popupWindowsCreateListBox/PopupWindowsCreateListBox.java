package com.pasich.mynotes.ui.view.dialogs.popupWindowsCreateListBox;


import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.res.Resources;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import androidx.core.content.ContextCompat;

import com.pasich.mynotes.R;
import com.pasich.mynotes.databinding.ViewPopupCreateListBoxBinding;

public class PopupWindowsCreateListBox {

    private final PopupWindow mPopupWindows;
    private final ViewPopupCreateListBoxBinding mBinding;
    private final int widthDisplay = Resources.getSystem().getDisplayMetrics().widthPixels;
    private final int widthAnchor;
    private View mAnchor;

    public PopupWindowsCreateListBox(LayoutInflater layoutInflater, View anchor) {
        this.mBinding = ViewPopupCreateListBoxBinding.inflate(layoutInflater);
        this.mAnchor = anchor;
        this.widthAnchor = anchor.getWidth();
        this.mPopupWindows = new PopupWindow(mBinding.getRoot(), RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);

        mBinding.getRoot().measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        onVibrate();
        onSettingsView();
    }


    private void onSettingsView() {
        int xof, widthDisplayCenter = widthDisplay / 2;
        if (mAnchor.getX() > widthDisplayCenter) {
            mBinding.getRoot().setBackground(ContextCompat.getDrawable(mBinding.getRoot().getContext(), R.drawable.background_popup_bottom_left));
            xof = (int) (-mBinding.getRoot().getMeasuredWidth() * 0.9);
        } else {
            Log.wtf(TAG, "onSettingsView: ");
            mBinding.getRoot().setBackground(ContextCompat.getDrawable(mBinding.getRoot().getContext(), R.drawable.background_popup_bottom_left));
            xof = widthAnchor / 2;
        }
        getPopupWindows().setElevation(10);
        getPopupWindows().setOnDismissListener(this::setOnDismissListener);
        initListeners();
        getPopupWindows().showAsDropDown(mAnchor, widthAnchor, -400);
        //getPopupWindows().showAtLocation(mAnchor, Gravity.BOTTOM, -xof,
        //         mAnchor.getBottom() + 100);
    }


    private void onVibrate() {
        Vibrator vibrator = (Vibrator) mBinding.getRoot().getContext().getSystemService(Context.VIBRATOR_SERVICE);

        if (vibrator.hasVibrator()) {
            vibrator.vibrate(100L);
        }
    }


    private void initListeners() {
     /*   mBinding.deleteTag.setOnClickListener(v -> {
          //  mListener.deleteTag();
            getPopupWindows().dismiss();

        });
        mBinding.renameTag.setOnClickListener(v -> {
         //   mListener.renameTag();
            getPopupWindows().dismiss();
        });
        mBinding.visibleTag.setOnClickListener(v -> {
        //    mListener.visibleEditTag();
            getPopupWindows().dismiss();
        });


      */
    }


    public PopupWindow getPopupWindows() {
        return mPopupWindows;
    }

    public void setOnDismissListener() {
        mAnchor = null;


    }
}
