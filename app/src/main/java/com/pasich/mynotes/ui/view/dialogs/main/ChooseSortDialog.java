package com.pasich.mynotes.ui.view.dialogs.main;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.pasich.mynotes.R;
import com.pasich.mynotes.data.model.ChoiceModel;
import com.pasich.mynotes.ui.view.customView.dialog.ListDialogView;
import com.pasich.mynotes.utils.adapters.DialogListAdapter;
import com.preference.PowerPreference;

import java.util.ArrayList;

public class ChooseSortDialog extends DialogFragment {

  @NonNull
  @Deprecated
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    final BottomSheetDialog builder = new BottomSheetDialog(requireContext());
    final ListDialogView view = new ListDialogView(getLayoutInflater());
    final ArrayList<ChoiceModel> arraySortOption = new ArrayList<>();
    final String sortParam = PowerPreference.getDefaultFile().getString("sortPref", "DataReserve");

    view.addTitle(getString(R.string.sortHead));
    view.addView(view.getItemsView());

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
    view.getItemsView().setAdapter(adapter);

    view.getItemsView().setOnItemClickListener(
            (parent, v, position, id) -> {
              PowerPreference.getDefaultFile().setString("sortPref", arraySortOption.get(position).getAction());
              //     assert SortInterface != null;
              //      SortInterface.sortList(sortPar);
              dismiss();
            });
    builder.setContentView(view.getRootContainer());
    return builder;
  }
}
