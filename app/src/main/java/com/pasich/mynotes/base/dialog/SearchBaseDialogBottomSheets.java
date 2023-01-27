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
import com.pasich.mynotes.base.view.BaseView;

public abstract class SearchBaseDialogBottomSheets extends BottomSheetDialogFragment implements BaseView {

    private BottomSheetDialog mDialog;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mDialog = new BottomSheetDialog(requireContext(), R.style.SearchDialog);
        mDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);

    }

    @Override
    public void selectTheme() {

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
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onInfoSnack(int resID, View view, int typeInfo, int time) {

    }

    @Override
    public void vibrateOpenDialog(boolean vibrate) {

    }
}