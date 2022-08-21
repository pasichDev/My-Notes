package com.pasich.mynotes.ui.view.dialogs.main.TagDialog;

import static com.pasich.mynotes.utils.constants.TagSettings.MAX_TAG_COUNT;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.textview.MaterialTextView;
import com.pasich.mynotes.R;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.notes.Note;
import com.pasich.mynotes.data.tags.Tag;
import com.pasich.mynotes.databinding.DialogAddTagToNoteBinding;
import com.pasich.mynotes.ui.contract.dialog.TagDialogContract;
import com.pasich.mynotes.ui.presenter.dialog.TagDialogPresenter;

import java.util.List;
import java.util.concurrent.ExecutionException;


public class TagDialog extends BottomSheetDialogFragment implements TagDialogContract.view {

    private final Note note;
    private final TagDialogPresenter dialogPresenter;
    public final DataManager dataManager;
    public BottomSheetDialog builder;
    private DialogAddTagToNoteBinding binding;

    public TagDialog(Note note) {
        this.note = note;
        this.dataManager = new DataManager();
        this.dialogPresenter = new TagDialogPresenter();

    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        builder = new BottomSheetDialog(requireContext());
        binding = DialogAddTagToNoteBinding.inflate(getLayoutInflater());
        builder.setContentView(binding.getRoot());


        init();
        dialogPresenter.attachView(this);
        dialogPresenter.setDataManager(dataManager);
        dialogPresenter.viewIsReady();
        return builder;
    }


    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        dialogPresenter.detachView();

    }

    @Override
    public void initTitle() {
        MaterialTextView title = builder.findViewById(R.id.headTextDialog);
        assert title != null;
        title.setText(note.getTag().length() == 0 ? getString(R.string.selectTagForNote) : getString(R.string.editSelectTagForNote));

    }

    @Override
    public void loadingTagsOfChips(List<Tag> tagsList) {
        for (Tag tag : tagsList) {
            if (!note.getTag().equals(tag.getNameTag())) createChipTag(tag.getNameTag());
        }
    }


    @Override
    public void init() {
        initTitle();
        binding.chipGroupSystem.setVisibility(View.VISIBLE);
    }

    @Override
    public void initListeners() {
        try {
            if (dialogPresenter.getCountTags() < MAX_TAG_COUNT) {
                binding.addTagChip.setVisibility(View.VISIBLE);
                binding.addTagChip.setOnCheckedChangeListener((buttonView, isChecked) -> {
                });
            }

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        if (note.getTag().length() >= 2) {
            binding.dellTagChip.setVisibility(View.VISIBLE);
            binding.dellTagChip.setOnCheckedChangeListener((buttonView, isChecked) -> {
                dialogPresenter.removeTagNote(note);
                dismiss();
            });
        }




/*
        mView.getSaveButton()
                .setOnClickListener(
                        view1 -> {
                            dialogPresenter.createTagNote(new Tag().create(mView.getText()), note);
                            dismiss();
                        });
        tagsAdapter.setOnItemClickListener(
                new TagsAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(int position) {
                        if (!tagsAdapter.getCurrentList().get(position).getNameTag().equals(note.getTag())) {
                            dialogPresenter.editTagNote(tagsAdapter.getCurrentList().get(position), note);
                            dismiss();
                        }
                    }

                    @Override
                    public void onLongClick(int position) {

                    }
                });



        mView.getRootContainer().findViewById(R.id.addTagForDialog)
                .setOnClickListener(
                        view1 -> {
                            mView.visibilityInputNewTag();
                            mView.getRootContainer().findViewById(R.id.addTagForDialog).setVisibility(View.GONE);
                            mView.getInputTag().requestFocus();
                            builder.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                            ((InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                                    .toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.SHOW_FORCED);

                        });
  */
    }


    private void selectedTag(String nameChip) {
        if (!nameChip.equals(note.getTag())) {
            dialogPresenter.editTagNote(nameChip, note);
            dismiss();
        }
    }

    private void createChipTag(String nameChip) {
        Chip newChip = (Chip) getLayoutInflater().inflate(R.layout.layout_chip_entry, binding.chipGroupSystem, false);
        newChip.setText(nameChip);
        binding.chipGroupSystem.addView(newChip);
        newChip.setOnCheckedChangeListener((buttonView, isChecked) -> selectedTag(nameChip));
    }
}
