package com.pasich.mynotes.Controllers.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.pasich.mynotes.Models.Adapter.MoreChoiceModel;
import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.Adapters.MoreListAdapter;
import com.pasich.mynotes.Utils.Interface.SortInterface;
import com.pasich.mynotes.View.DialogView.ChooseSortDialogView;

import java.util.ArrayList;

public class ChooseSortDialog extends DialogFragment {

  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {

    final BottomSheetDialog builder = new BottomSheetDialog(requireContext());
    final ChooseSortDialogView view =
        new ChooseSortDialogView(requireContext(), getLayoutInflater());
    final SortInterface SortInterface = (SortInterface) getContext();
    final ArrayList<MoreChoiceModel> arraySortOption = new ArrayList<>();

    view.setHeadTextView(getString(R.string.cd_sort));

    arraySortOption.add(
        new MoreChoiceModel(
            getString(R.string.sort_Date_Increase), R.drawable.ic_sort, "DataSort"));
    arraySortOption.add(
        new MoreChoiceModel(
            getString(R.string.sort_Date_Decrease), R.drawable.ic_sort, "DataReserve"));

    arraySortOption.add(
        new MoreChoiceModel(
            getString(R.string.sort_Title_Increase), R.drawable.ic_sort, "TitleSort"));
    arraySortOption.add(
        new MoreChoiceModel(
            getString(R.string.sort_Title_Decrease), R.drawable.ic_sort, "TitleReserve"));

    MoreListAdapter adapter =
        new MoreListAdapter(getContext(), R.layout.item_icon_text_simple, arraySortOption);
    view.listView.setAdapter(adapter);

    view.listView.setOnItemClickListener(
        (parent, v, position, id) -> {
          String sortParam = arraySortOption.get(position).getAction();
          requireContext()
              .getSharedPreferences(
                  requireContext().getString(R.string.PreferencesFileName), Context.MODE_PRIVATE)
              .edit()
              .putString("sortPref", sortParam)
              .apply();
          assert SortInterface != null;
          SortInterface.sortList(sortParam);
          dismiss();
        });
    builder.setContentView(view.getContainer());
    return builder;
  }
}
