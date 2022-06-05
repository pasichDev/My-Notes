package com.pasich.mynotes.controllers.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.preference.PreferenceManager;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.pasich.mynotes.R;
import com.pasich.mynotes.models.adapter.ChoiceModel;
import com.pasich.mynotes.utils.adapters.DialogListAdapter;
import com.pasich.mynotes.utils.interfaces.SortInterface;
import com.pasich.mynotes.view.dialog.ChooseSortDialogView;

import java.util.ArrayList;

public class ChooseSortDialog extends DialogFragment {

  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {

    final BottomSheetDialog builder = new BottomSheetDialog(requireContext());
    final ChooseSortDialogView view =
        new ChooseSortDialogView(requireContext(), getLayoutInflater());
    final SortInterface SortInterface = (SortInterface) getContext();
    final ArrayList<ChoiceModel> arraySortOption = new ArrayList<>();
    final String sortParam =
        PreferenceManager.getDefaultSharedPreferences(requireContext())
            .getString("sortPref", "DataReserve");
    view.setHeadTextView(getString(R.string.sortHead));

    arraySortOption.add(
        new ChoiceModel(
            getString(R.string.sort_Date_Increase),
            R.drawable.ic_sort,
            "DataSort",
            sortParam.equals("DataSort")));
    arraySortOption.add(
        new ChoiceModel(
            getString(R.string.sort_Date_Decrease),
            R.drawable.ic_sort,
            "DataReserve",
            sortParam.equals("DataReserve")));

    arraySortOption.add(
        new ChoiceModel(
            getString(R.string.sort_Title_Increase),
            R.drawable.ic_sort,
            "TitleSort",
            sortParam.equals("TitleSort")));
    arraySortOption.add(
        new ChoiceModel(
            getString(R.string.sort_Title_Decrease),
            R.drawable.ic_sort,
            "TitleReserve",
            sortParam.equals("TitleReserve")));

    DialogListAdapter adapter = new DialogListAdapter(arraySortOption);
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
