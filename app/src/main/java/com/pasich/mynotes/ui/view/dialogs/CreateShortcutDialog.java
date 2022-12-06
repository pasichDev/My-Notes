package com.pasich.mynotes.ui.view.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textview.MaterialTextView;
import com.pasich.mynotes.R;
import com.pasich.mynotes.data.database.model.Label;
import com.pasich.mynotes.databinding.DialogShortcutBinding;
import com.pasich.mynotes.utils.adapters.labelAdapter.LabelAdapter;
import com.pasich.mynotes.utils.recycler.SpacesItemDecoration;

import java.util.ArrayList;


public class CreateShortcutDialog extends DialogFragment {
    private DialogShortcutBinding binding;

    @NonNull
    @Deprecated
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final BottomSheetDialog builder = new BottomSheetDialog(requireContext(), R.style.BottomSheetsStyleCustom);
        this.binding = DialogShortcutBinding.inflate(getLayoutInflater());
        builder.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
        builder.setContentView(binding.getRoot());

        MaterialTextView title = builder.findViewById(R.id.headTextDialog);
        assert title != null;
        title.setText(R.string.titleDialogShortCut);

        test();

        return builder;
    }


    private void test() {
        ArrayList<Label> labels = new ArrayList<>();
        labels.add(new Label(R.mipmap.ic_launcher, true));
        labels.add(new Label(R.mipmap.ic_launcher_note));
        labels.add(new Label(R.mipmap.ic_launcher_note));
        labels.add(new Label(R.mipmap.ic_launcher_note));


        LabelAdapter labelAdapter = new LabelAdapter(requireContext(), labels);
        binding.iconsLabel.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));
        binding.iconsLabel.addItemDecoration(new SpacesItemDecoration(20));
        binding.iconsLabel.setAdapter(labelAdapter);

    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);


    }
}
