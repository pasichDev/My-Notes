package com.pasich.mynotes.Controllers.Dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.pasich.mynotes.R;
import com.pasich.mynotes.View.CustomView.CustomUIDialog;

public class NewTagDialog extends DialogFragment {

  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    final BottomSheetDialog builder = new BottomSheetDialog(getContext());
    final CustomUIDialog uiDialog = new CustomUIDialog(getContext(), getLayoutInflater());
    uiDialog.setHeadTextView(getString(R.string.addTag));

    EditText inputNameTag = new EditText(getContext());
    inputNameTag.setBackgroundResource(R.drawable.item_note_background);
    inputNameTag.setPadding(20, 20, 20, 20);
    uiDialog.getContainer().addView(inputNameTag, uiDialog.lp);

    uiDialog.getCloseButton().setOnClickListener(view -> dismiss());
    uiDialog.getSaveButton().setOnClickListener(view -> dismiss());

    builder.setContentView(uiDialog.getContainer());

    return builder;
  }
}
