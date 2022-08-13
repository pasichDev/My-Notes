package com.pasich.mynotes.ui.view.dialogs.note;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.pasich.mynotes.R;
import com.pasich.mynotes.ui.view.customView.dialog.ListDialogView;

public class PermissionsError extends BottomSheetDialogFragment {


    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final BottomSheetDialog builder = new BottomSheetDialog(requireActivity());
        final ListDialogView view = new ListDialogView(getLayoutInflater());
        view.LP_DEFAULT.setMargins(60, 10, 60, 60);

        view.addTitle(getString(R.string.errorPermisions));
        TextView textMessage = new TextView(getContext());
        textMessage.setText(getString(R.string.errorPermisions_Message));
        textMessage.setTextSize(TypedValue.COMPLEX_UNIT_SP, view.TEXT_MESSAGE_SIZE);

        view.addView(textMessage, view.LP_DEFAULT);
        view.addView(createButtonInfo(), view.LP_DEFAULT);

        view.getRootContainer().findViewById(R.id.errorPermissionsButton).setOnClickListener(view1 -> {
            openIntentInfoApp();
            dismiss();
        });
        builder.setContentView(view.getRootContainer());
        return builder;
    }


    private Button createButtonInfo() {
        Button button = new Button(getContext());
        button.setText(R.string.open_infoApp);
        button.setBackgroundResource(R.drawable.background_button_note);
        button.setTextColor(getResources().getColor(R.color.white));
        button.setId(R.id.errorPermissionsButton);
        return button;
    }

    private void openIntentInfoApp() {
        startActivity(new Intent().setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                .setData(Uri.fromParts("package", requireActivity().getPackageName(), null))
        );
    }
}
