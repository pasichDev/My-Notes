package com.pasich.mynotes.Controllers.Dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;
import com.pasich.mynotes.Model.ChooseTagDialogModel;
import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.Interface.ManageTag;
import com.pasich.mynotes.Utils.Simplifications.TabLayoutListenerUtils;
import com.pasich.mynotes.View.DialogView.ChooseTagDialogView;

public class ChooseTagDialog extends DialogFragment {

  private final int noteID;
  private final int position;
  private ManageTag ManageTag;

  public ChooseTagDialog(int noteID, int postion) {
    this.position = postion;
    this.noteID = noteID;
  }

  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {

    final BottomSheetDialog builder = new BottomSheetDialog(requireContext());
    final ChooseTagDialogModel model = new ChooseTagDialogModel(getContext(), noteID);
    final ChooseTagDialogView view = new ChooseTagDialogView(requireContext(), getLayoutInflater());
    ManageTag = (ManageTag) getContext();

    view.setHeadTextView(
        model.tagNote.length() == 0
            ? getString(R.string.selectTagForNote)
            : getString(R.string.editSelectTagForNote));

    model.queryTags(view.TabLayoutTags);

    view.TabLayoutTags.selectTab(view.TabLayoutTags.getTabAt(model.selectedPosition));
    view.NewTagVIewUi.getSaveButton()
        .setOnClickListener(
            view1 -> {
              ManageTag.addTag(
                  view.NewTagVIewUi.getInputTag().getText().toString(), noteID, position);
              dismiss();
            });

    view.TabLayoutTags.addOnTabSelectedListener(
        new TabLayoutListenerUtils() {
          @Override
          public void listener(TabLayout.Tab Tab) {
            ManageTag.addTagForNote(
                Tab.getPosition() == 0 ? "" : (String) Tab.getText(), noteID, position);
            dismiss();
          }
        });

    builder.setContentView(view.getContainer());
    builder.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    return builder;
  }
}
