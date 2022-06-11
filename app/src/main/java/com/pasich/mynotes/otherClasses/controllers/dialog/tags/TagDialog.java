package com.pasich.mynotes.otherClasses.controllers.dialog.tags;

import android.app.Dialog;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.interfaces.ManageTag;
import com.pasich.mynotes.otherClasses.models.dialog.ChooseTagDialogModel;
import com.pasich.mynotes.otherClasses.view.dialog.TagDialogView;
import com.pasich.mynotes.utils.simplifications.TabLayoutListenerUtils;

import java.util.Objects;

public class TagDialog extends DialogFragment {

  private final int noteID;
  private final int position;
  private ManageTag ManageTag;

  public TagDialog(int noteID, int position) {
    this.position = position;
    this.noteID = noteID;
  }

  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {

    final BottomSheetDialog builder = new BottomSheetDialog(requireContext());
    final ChooseTagDialogModel model = new ChooseTagDialogModel(getContext(), noteID);
    final TagDialogView mView = new TagDialogView(getLayoutInflater());
    ManageTag = (ManageTag) getContext();

    mView.setTitle(
        model.tagNote.length() == 0
            ? getString(R.string.selectTagForNote)
            : getString(R.string.editSelectTagForNote));

    model.queryTags(mView.TabLayoutTags);

    mView.TabLayoutTags.selectTab(mView.TabLayoutTags.getTabAt(model.selectedPosition));

    mView
        .getSaveButton()
        .setOnClickListener(
            view1 -> {
              ManageTag.addTag(mView.getInputTag().getText().toString(), noteID, position);
              dismiss();
            });

    mView.TabLayoutTags.addOnTabSelectedListener(
        new TabLayoutListenerUtils() {
          @Override
          public void listener(TabLayout.Tab Tab) {
            ManageTag.addTagForNote(
                Tab.getPosition() == 0 ? "" : Objects.requireNonNull(Tab.getText()).toString(),
                noteID,
                position);
            dismiss();
          }
        });

    builder.setContentView(mView.getRootContainer());
    builder.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    return builder;
  }
}
