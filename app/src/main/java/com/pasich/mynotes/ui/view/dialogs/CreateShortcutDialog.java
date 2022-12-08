package com.pasich.mynotes.ui.view.dialogs;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textview.MaterialTextView;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.view.ShortCutView;
import com.pasich.mynotes.data.database.model.Label;
import com.pasich.mynotes.data.database.model.Note;
import com.pasich.mynotes.databinding.DialogShortcutBinding;
import com.pasich.mynotes.ui.view.activity.NoteActivity;
import com.pasich.mynotes.utils.adapters.labelAdapter.LabelAdapter;
import com.pasich.mynotes.utils.recycler.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;


public class CreateShortcutDialog extends DialogFragment {
    private DialogShortcutBinding binding;
    private LabelAdapter labelAdapter;
    private final Note mNote;
    private ShortCutView shortCutView;

    public CreateShortcutDialog(Note note) {
        this.mNote = note;
    }


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

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            shortCutView = (ShortCutView) requireContext();
            initialTitle();
            setListLabels();
        } else {
            dismiss();
            Toast.makeText(requireContext(), getString(R.string.functionNotSupport), Toast.LENGTH_SHORT).show();
        }

        return builder;
    }

    private void initialTitle() {
        binding.titleShortCut.setText(mNote.getTitle().length() >= 24 ? mNote.getTitle().substring(0, 24) : mNote.getTitle());
    }


    private void setListLabels() {
        labelAdapter = new LabelAdapter(requireContext(), getLabels());
        binding.iconsLabel.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));
        binding.iconsLabel.addItemDecoration(new SpacesItemDecoration(20));
        binding.iconsLabel.setAdapter(labelAdapter);
        setListener();
    }


    private void setListener() {
        labelAdapter.setSelectLabelListener(position -> labelAdapter.selectLabel(position));
        binding.createShortCut.setOnClickListener(v -> {
            String titleShortCut = String.valueOf(binding.titleShortCut.getText());
            if (titleShortCut.length() >= 1 && titleShortCut.length() < 25) {
                createShortCut(titleShortCut);
                dismiss();
            }

        });
    }


    @SuppressLint("UnspecifiedImmutableFlag")
    private void createShortCut(String titleShortCut) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            ShortcutManager shortcutManager = ContextCompat.getSystemService(requireContext(), ShortcutManager.class
            );
            if (!isCreateShortCutId(shortcutManager != null ? shortcutManager.getPinnedShortcuts() : null)) {
                ShortcutInfo pinShortcutInfo = new ShortcutInfo.Builder(
                        requireContext(),
                        Integer.toString(mNote.getId()))
                        .setShortLabel(titleShortCut)
                        .setIntent(new Intent(Intent.ACTION_VIEW,
                                Uri.parse(""),
                                requireContext(),
                                NoteActivity.class)
                                .putExtra("NewNote", false)
                                .putExtra("idNote", mNote.getId())
                                .putExtra("shareText", "")
                                .putExtra("tagNote", ""))
                        .setIcon(Icon.createWithResource(requireContext(), labelAdapter.getSelectLabel().getImage())).build();


                assert shortcutManager != null;
                shortcutManager.requestPinShortcut(pinShortcutInfo, PendingIntent.getBroadcast(requireContext(), 0,
                        shortcutManager.createShortcutResultIntent(pinShortcutInfo), 0).getIntentSender());
                shortCutView.createShortCut();
            } else {
                shortCutView.shortCutDouble();
            }
        }
    }


    @SuppressLint("NewApi")
    private boolean isCreateShortCutId(List<ShortcutInfo> shortcutInfo) {
        for (ShortcutInfo info : shortcutInfo) {
            if (Long.parseLong(info.getId()) == mNote.getId())
                return true;
        }
        return false;
    }

    private ArrayList<Label> getLabels() {
        ArrayList<Label> labels = new ArrayList<>();
        labels.add(new Label(R.mipmap.ic_launcher));
        labels.add(new Label(R.mipmap.ic_launcher_note));
        labels.add(new Label(R.mipmap.ic_edit_note));
        labels.add(new Label(R.mipmap.ic_edit_alert));
        return labels;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        binding.createShortCut.setOnClickListener(null);
    }
}
