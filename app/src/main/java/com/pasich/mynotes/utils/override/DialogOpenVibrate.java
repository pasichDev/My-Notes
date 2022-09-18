package com.pasich.mynotes.utils.override;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class DialogOpenVibrate extends BottomSheetDialogFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Vibrator vibrator = (Vibrator) requireActivity().getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator.hasVibrator()) {
            vibrator.vibrate(100L);
        }
    }

}
