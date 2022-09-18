package com.pasich.mynotes.ui.view.dialogs.settings;

import static com.pasich.mynotes.utils.constants.LinkConstants.LINK_TELEGRAM_DEVELOP;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textview.MaterialTextView;
import com.pasich.mynotes.R;

public class FeedbackDialog extends BottomSheetDialogFragment {

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final BottomSheetDialog builder = new BottomSheetDialog(requireActivity());
        builder.setContentView(R.layout.dialog_feedback);

        MaterialTextView textTitle = builder.findViewById(R.id.headTextDialog);
        assert textTitle != null;
        textTitle.setText(R.string.writeMe);


        builder.findViewById(R.id.telegramSend).setOnClickListener(v ->
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(LINK_TELEGRAM_DEVELOP))));
        builder.findViewById(R.id.emailSend).setOnClickListener(v -> sendEmail());
        return builder;
    }

    private void sendEmail() {
        Intent intent = new Intent(Intent.ACTION_SENDTO)
                .setData(Uri.parse("mailto:pasichDev@outlook.com"));
        if (intent.resolveActivity(requireContext().getPackageManager()) != null) {
            startActivity(intent);
        }

    }
}
