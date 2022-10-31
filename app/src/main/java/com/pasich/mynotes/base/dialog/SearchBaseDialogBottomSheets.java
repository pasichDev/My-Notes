package com.pasich.mynotes.base.dialog;


import android.app.Dialog;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.BaseView;
import com.pasich.mynotes.base.activity.BaseActivity;
import com.pasich.mynotes.di.component.ActivityComponent;

public abstract class SearchBaseDialogBottomSheets extends BottomSheetDialogFragment implements BaseView {

    private BaseActivity activity;
    private BottomSheetDialog mDialog;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mDialog = new BottomSheetDialog(requireContext(), R.style.SearchDialog);
        mDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
        if (context instanceof BaseActivity) {
            activity = (BaseActivity) context;
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        mDialog = null;
    }


    @Nullable
    @Override
    public Dialog getDialog() {
        return this.mDialog;
    }

    @Override
    public void onDetach() {
        activity = null;
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showMessage(int resID) {

    }

    @Override
    public void onError(String message, View view) {

    }

    @Override
    public void onError(int resID, View view) {

    }

    @Override
    public void vibrateOpenDialog(boolean vibrate) {

    }

    public ActivityComponent getActivityComponent() {
        if (activity != null) return activity.getActivityComponent();
        return null;
    }

    public BaseActivity getBaseActivity() {
        return activity;
    }
}