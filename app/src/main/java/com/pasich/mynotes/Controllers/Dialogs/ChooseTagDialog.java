package com.pasich.mynotes.Controllers.Dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.pasich.mynotes.Model.Adapter.MoreChoiceModel;
import com.pasich.mynotes.Model.ChooseTagDialogModel;
import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.Adapters.MoreListAdapter;
import com.pasich.mynotes.Utils.Anim.ListViewAnimation;
import com.pasich.mynotes.View.DialogView.ChooseTagDialogView;

import java.util.ArrayList;

public class ChooseTagDialog extends DialogFragment {

  private final int position;
  private final int noteID;

  public ChooseTagDialog(int position, int noteID) {
    this.position = position;
    this.noteID = noteID;
  }

  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {

    final BottomSheetDialog builder = new BottomSheetDialog(requireContext());
    final ChooseTagDialogModel model = new ChooseTagDialogModel(getContext(), noteID);
    final ChooseTagDialogView view = new ChooseTagDialogView(requireContext(), getLayoutInflater());

    view.setHeadTextView(
        model.tagNote.length() == 0
            ? getString(R.string.selectTagForNote)
            : getString(R.string.editSelectTagForNote));

    MoreListAdapter adapter =
        new MoreListAdapter(getContext(), R.layout.item_icon_text_simple, model.arrayChoice);
    view.listView.setAdapter(adapter);
    checkTagSelected(adapter.getData(), model.tagNote);

    ListViewAnimation.setListviewAnimationLeftToShow(view.listView);
    view.listView.setOnItemClickListener((parent, v, position, id) -> {});

    builder.setContentView(view.getContainer());
    return builder;
  }

  private void checkTagSelected(ArrayList<MoreChoiceModel> arrayTags, String noteTag) {

    for (MoreChoiceModel tag : arrayTags) {
      Log.wtf("pasic", tag.getName() + tag.getView());
    }
  }
}
