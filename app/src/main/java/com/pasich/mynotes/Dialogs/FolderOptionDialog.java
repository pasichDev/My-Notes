package com.pasich.mynotes.Dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.pasich.mynotes.R;
import com.pasich.mynotes.View.CustomView.CustomUIDialog;
import com.pasich.mynotes.Utils.File.FileCore;

public class FolderOptionDialog extends DialogFragment {
  private final String editName;

  public FolderOptionDialog(String editName) {
    this.editName = editName;
  }

  public interface EditNameDialogListener {
    void onFinishfolderOption(boolean updateList);
  }

  @SuppressLint("RtlHardcoded")
  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    FileCore fileCore = new FileCore(getContext());
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    EditNameDialogListener listener = (EditNameDialogListener) getTargetFragment();

    LinearLayout container = new LinearLayout(getContext());
    container.setOrientation(LinearLayout.VERTICAL);
    CustomUIDialog uiDialog = new CustomUIDialog(getContext(), getLayoutInflater());

    final EditText input = new EditText(getContext());

    input.setGravity(android.view.Gravity.TOP | android.view.Gravity.LEFT);
    input.setInputType(
        InputType.TYPE_TEXT_FLAG_CAP_SENTENCES | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
    input.setLines(1);
    input.setHint(getString(R.string.inputNameFolder));
    input.setMaxLines(1);
    uiDialog.setHeadTextView(getString(editName.equals("")? R.string.newFolder : R.string.renameFolder));
    uiDialog.getContainer().addView(input, uiDialog.lp);
    builder.setView(uiDialog.getContainer());

    if (editName.length() >= 1) input.setText(editName);
    input.setEnabled(true);
    input.setFocusable(true);
    input.setSelection(input.getText().length());
    input.setFocusableInTouchMode(true);
    input.requestFocus();
    input.setFilters(new InputFilter[] {new InputFilter.LengthFilter(25)});

    if (editName.trim().length() >= 1) { //
      builder.setPositiveButton(
          getString(R.string.save),
          (dialog, which) -> {
            if (!input.getText().toString().equals(editName)) {
              fileCore.saveNameFolder(input.getText().toString(), true, editName);
              assert listener != null;
              listener.onFinishfolderOption(true);
            }
          });
    } else if (editName.trim().length() == 0 || editName.equals("")) {
      builder.setPositiveButton(
          getString(R.string.createFolder),
          (dialog, which) -> {
            if (input.getText().toString().length() >= 1) {
              fileCore.saveNameFolder(input.getText().toString(), false, "");
              assert listener != null;
              listener.onFinishfolderOption(true);
            }
          });
    }

    InputMethodManager inputMgr =
        (InputMethodManager)
            builder.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    inputMgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.SHOW_FORCED);

    return builder.create();
  }

  @Override
  public void onDismiss(@NonNull DialogInterface dialog) {
    super.onDismiss(dialog);
    getActivity()
        .getWindow()
        .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
  }
}
