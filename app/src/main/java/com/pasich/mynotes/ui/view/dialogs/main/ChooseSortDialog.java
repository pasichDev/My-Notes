package com.pasich.mynotes.ui.view.dialogs.main;

import static com.pasich.mynotes.utils.constants.PreferencesConfig.ARGUMENT_DEFAULT_SORT_PREF;
import static com.pasich.mynotes.utils.constants.PreferencesConfig.ARGUMENT_PREFERENCE_SORT;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.color.MaterialColors;
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
        final BottomSheetDialog builder = new BottomSheetDialog(requireContext(), R.style.BottomSheetsStyleCustom);
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

    public void selectedAutoItem(String param) {
        int colorBackground = MaterialColors.getColor(requireContext(), R.attr.colorSurfaceVariant, Color.GRAY);
        int colorText = MaterialColors.getColor(requireContext(), R.attr.colorPrimary, Color.BLACK);

        switch (param) {
            case "DataSort":
                binding.DataSort.setBackgroundColor(colorBackground);
                binding.DataSortText.setTextColor(colorText);
                binding.DataSortCheck.setVisibility(View.VISIBLE);
                break;
            case "DataReserve":
                binding.DataReserve.setBackgroundColor(colorBackground);
                binding.DataReserveText.setTextColor(colorText);
                binding.DataReserveCheck.setVisibility(View.VISIBLE);
                break;
            case "TitleSort":
                binding.TitleSort.setBackgroundColor(colorBackground);
                binding.TitleSortText.setTextColor(colorText);
                binding.TitleSortCheck.setVisibility(View.VISIBLE);

                break;
            case "TitleReserve":
                binding.TitleReserve.setBackgroundColor(colorBackground);
                binding.TitleReserveText.setTextColor(colorText);
                binding.TitleReserveCheck.setVisibility(View.VISIBLE);

                break;
        }


    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        binding.DataSort.setOnClickListener(null);
        binding.DataReserve.setOnClickListener(null);
        binding.TitleSort.setOnClickListener(null);
        binding.TitleReserve.setOnClickListener(null);

    }
}
