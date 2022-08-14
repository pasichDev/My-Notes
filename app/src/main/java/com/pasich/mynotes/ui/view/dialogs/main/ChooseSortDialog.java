package com.pasich.mynotes.ui.view.dialogs.main;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textview.MaterialTextView;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.view.MainSortView;
import com.preference.PowerPreference;


public class ChooseSortDialog extends DialogFragment {
    private MainSortView sortView;


    @NonNull
    @Deprecated
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final BottomSheetDialog builder = new BottomSheetDialog(requireContext());
        sortView = (MainSortView) getContext();
        final String sortParam = PowerPreference.getDefaultFile().getString("sortPref", "DataReserve");
        builder.setContentView(R.layout.dialog_choose_sort);

        MaterialTextView title = builder.findViewById(R.id.headTextDialog);
        assert title != null;
        title.setText(R.string.sortHead);


        builder.findViewById(R.id.DataSort).setOnClickListener(v -> selectedSort("DataSort"));
        builder.findViewById(R.id.DataReserve).setOnClickListener(v -> selectedSort("DataReserve"));
        builder.findViewById(R.id.TitleSort).setOnClickListener(v -> selectedSort("TitleSort"));
        builder.findViewById(R.id.TitleReserve).setOnClickListener(v -> selectedSort("TitleReserve"));
        return builder;
    }

    private void selectedSort(String param) {
        PowerPreference.getDefaultFile().setString("sortPref", param);
        assert sortView != null;
        sortView.sortList(param);
        dismiss();
    }


}
