package com.pasich.mynotes.ui.view.dialogs.main;

import static com.pasich.mynotes.utils.constants.PreferencesConfig.ARGUMENT_DEFAULT_SORT_PREF;
import static com.pasich.mynotes.utils.constants.PreferencesConfig.ARGUMENT_PREFERENCE_SORT;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textview.MaterialTextView;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.view.MainSortView;
import com.pasich.mynotes.databinding.DialogChooseSortBinding;
import com.preference.PowerPreference;


public class ChooseSortDialog extends DialogFragment {
    private MainSortView sortView;
    private DialogChooseSortBinding binding;
    private String sortParam;

    @NonNull
    @Deprecated
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final BottomSheetDialog builder = new BottomSheetDialog(requireContext());
        this.binding = DialogChooseSortBinding.inflate(getLayoutInflater());
        this.sortView = (MainSortView) getContext();
        this.sortParam = PowerPreference.getDefaultFile().getString(ARGUMENT_PREFERENCE_SORT, ARGUMENT_DEFAULT_SORT_PREF);
        builder.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
        builder.setContentView(binding.getRoot());

        MaterialTextView title = builder.findViewById(R.id.headTextDialog);
        assert title != null;
        title.setText(R.string.sortHead);

        selectedAutoItem(sortParam);

        binding.DataSort.setOnClickListener(v -> selectedSort("DataSort"));
        binding.DataReserve.setOnClickListener(v -> selectedSort("DataReserve"));
        binding.TitleSort.setOnClickListener(v -> selectedSort("TitleSort"));
        binding.TitleReserve.setOnClickListener(v -> selectedSort("TitleReserve"));
        return builder;
    }

    private void selectedSort(String param) {
        if (!param.equals(sortParam)) {
            PowerPreference.getDefaultFile().setString(ARGUMENT_PREFERENCE_SORT, param);
            assert sortView != null;
            sortView.sortList(param);
            dismiss();
        }
    }

    @SuppressLint("ResourceType")
    public void selectedAutoItem(String param) {
        int colorBackground = R.color.colorPrimary;
        int colorText = R.color.colorPrimaryVariantBlue;
        switch (param) {
            case "DataSort":
                binding.DataSort.setBackgroundColor(getResources().getColor(colorBackground));
                binding.DataSortText.setTextColor(getResources().getColor(colorText));
                break;
            case "DataReserve":
                binding.DataReserve.setBackgroundColor(getResources().getColor(colorBackground));
                binding.DataReserveText.setTextColor(getResources().getColor(colorText));
                break;
            case "TitleSort":
                binding.TitleSort.setBackgroundColor(getResources().getColor(colorBackground));
                binding.TitleSortText.setTextColor(getResources().getColor(colorText));

                break;
            case "TitleReserve":
                binding.TitleReserve.setBackgroundColor(getResources().getColor(colorBackground));
                binding.TitleReserveText.setTextColor(getResources().getColor(colorText));

                break;
        }
    }


}
