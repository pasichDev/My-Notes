package com.pasich.mynotes.base.dialog;


import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.pasich.mynotes.base.BaseView;
import com.pasich.mynotes.base.activity.BaseActivity;
import com.pasich.mynotes.di.component.ActivityComponent;

public abstract class BaseDialogBottomSheets extends BottomSheetDialogFragment implements BaseView {

    private BaseActivity activity;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof BaseActivity) {
            activity = (BaseActivity) context;
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
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


    public ActivityComponent getActivityComponent() {
        if (activity != null) return activity.getActivityComponent();
        return null;
    }

    public BaseActivity getBaseActivity() {
        return activity;
    }
}