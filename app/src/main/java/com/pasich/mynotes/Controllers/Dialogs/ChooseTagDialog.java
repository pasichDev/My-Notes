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
import com.pasich.mynotes.Utils.Interface.ManageTag;
import com.pasich.mynotes.View.DialogView.ChooseTagDialogView;

import java.util.ArrayList;

public class ChooseTagDialog extends DialogFragment {

  private final int noteID;
  private ManageTag ManageTag;

  public ChooseTagDialog(int noteID) {
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

    MoreListAdapter adapter =
        new MoreListAdapter(getContext(), R.layout.item_icon_text_simple, model.arrayChoice);
    view.listView.setAdapter(adapter);
    checkTagSelected(adapter.getData(), model.tagNote);

    ListViewAnimation.setListviewAnimationLeftToShow(view.listView);
    view.listView.setOnItemClickListener((parent, v, position, id) -> {});

    view.NewTagVIewUi.getSaveButton()
        .setOnClickListener(
            view1 -> {
              ManageTag.addTag(view.NewTagVIewUi.getInputTag().getText().toString(), noteID);
              dismiss();
            });

    view.listView.setOnItemClickListener(
        (parent, v, position, id) -> {
          ManageTag.addTagForNote(
              adapter.getItem(position).getAction().equals("noTag")
                  ? ""
                  : adapter.getItem(position).getAction(),
              noteID);
          dismiss();
        });

    builder.setContentView(view.getContainer());
    return builder;
  }

  private void checkTagSelected(ArrayList<MoreChoiceModel> arrayTags, String noteTag) {

    for (MoreChoiceModel tag : arrayTags) {
      Log.wtf("pasic", tag.getName() + tag.getView());
    }
  }
}
