package com.pasich.mynotes.Controllers.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.preference.PreferenceManager;

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
    final String sortParam =
        PreferenceManager.getDefaultSharedPreferences(requireContext())
            .getString("sortPref", "DataReserve");
    view.setHeadTextView(getString(R.string.sortHead));

    arraySortOption.add(
        new MoreChoiceModel(
            getString(R.string.sort_Date_Increase),
            R.drawable.ic_sort,
            "DataSort",
            sortParam.equals("DataSort")));
    arraySortOption.add(
        new MoreChoiceModel(
            getString(R.string.sort_Date_Decrease),
            R.drawable.ic_sort,
            "DataReserve",
            sortParam.equals("DataReserve")));

    arraySortOption.add(
        new MoreChoiceModel(
            getString(R.string.sort_Title_Increase),
            R.drawable.ic_sort,
            "TitleSort",
            sortParam.equals("TitleSort")));
    arraySortOption.add(
        new MoreChoiceModel(
            getString(R.string.sort_Title_Decrease),
            R.drawable.ic_sort,
            "TitleReserve",
            sortParam.equals("TitleReserve")));

    MoreListAdapter adapter =
        new MoreListAdapter(getContext(), R.layout.item_icon_text_simple, arraySortOption);
    view.listView.setAdapter(adapter);

    view.listView.setOnItemClickListener(
        (parent, v, position, id) -> {
          String sortPar = arraySortOption.get(position).getAction();
          requireContext()
              .getSharedPreferences(
                  requireContext().getString(R.string.PreferencesFileName), Context.MODE_PRIVATE)
              .edit()
              .putString("sortPref", sortPar)
              .apply();
          assert SortInterface != null;
          SortInterface.sortList(sortPar);
          dismiss();
        });
    builder.setContentView(view.getContainer());
    return builder;
  }
}
