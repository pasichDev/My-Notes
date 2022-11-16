package com.pasich.mynotes.ui.view.dialogs.settings;

import static com.pasich.mynotes.utils.constants.LinkConstants.LINK_TELEGRAM_DEVELOP;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textview.MaterialTextView;
import com.pasich.mynotes.R;
import com.pasich.mynotes.databinding.DialogFeedbackBinding;

public class FeedbackDialog extends BottomSheetDialogFragment {

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final BottomSheetDialog builder = new BottomSheetDialog(requireActivity(), R.style.InputsDialog);
        com.pasich.mynotes.databinding.DialogFeedbackBinding binding = DialogFeedbackBinding.inflate(getLayoutInflater());
        builder.setContentView(binding.getRoot());

        MaterialTextView title = builder.findViewById(R.id.headTextDialog);
        MaterialTextView message = builder.findViewById(R.id.textMessageDialog);

        assert title != null;
        title.setText(R.string.writeMe);
        assert message != null;
        message.setText(R.string.mesFeedback);

        binding.telegramSend.setOnClickListener(v ->
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(LINK_TELEGRAM_DEVELOP))));
        binding.emailSend.setOnClickListener(v -> sendEmail());
        return builder;
    }

    private void sendEmail() {
        Intent intent = new Intent(Intent.ACTION_SENDTO)
                .setData(Uri.parse("mailto:pasichDev@outlook.com"));
        if (intent.resolveActivity(requireContext().getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        requireDialog().findViewById(R.id.telegramSend).setOnClickListener(null);
        requireDialog().findViewById(R.id.emailSend).setOnClickListener(null);
    }
}
