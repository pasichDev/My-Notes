package com.pasich.mynotes.ui.view.dialogs.error;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textview.MaterialTextView;
import com.pasich.mynotes.R;

public class PermissionsError extends BottomSheetDialogFragment {


    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final BottomSheetDialog builder = new BottomSheetDialog(requireActivity());

        builder.setContentView(R.layout.dialog_message);

        MaterialTextView title = builder.findViewById(R.id.headTextDialog);
        assert title != null;
        title.setText(getString(R.string.errorPermisions));

        MaterialTextView message = builder.findViewById(R.id.textMessageDialog);
        assert message != null;
        message.setText(getString(R.string.errorPermisions_Message));

        builder.findViewById(R.id.buttonInfoLayout).setVisibility(View.VISIBLE);
        builder.findViewById(R.id.buttonInfoApp).setOnClickListener(v -> {
            openIntentInfoApp();
            dismiss();
        });
        return builder;
    }

    private void openIntentInfoApp() {
        startActivity(new Intent().setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                .setData(Uri.fromParts("package", requireActivity().getPackageName(), null))
        );
    }
}
