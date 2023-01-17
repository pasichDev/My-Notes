package com.pasich.mynotes.ui.view.dialogs.main;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.dialog.SearchBaseDialogBottomSheets;
import com.pasich.mynotes.data.model.IndexFilter;
import com.pasich.mynotes.data.model.Note;
import com.pasich.mynotes.databinding.DialogSearchBinding;
import com.pasich.mynotes.di.component.ActivityComponent;
import com.pasich.mynotes.ui.contract.dialogs.SearchDialogContract;
import com.pasich.mynotes.ui.presenter.dialogs.SearchDialogPresenter;
import com.pasich.mynotes.ui.view.activity.NoteActivity;
import com.pasich.mynotes.utils.adapters.searchAdapter.SearchNotesAdapter;
import com.pasich.mynotes.utils.recycler.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

public class SearchDialog extends SearchBaseDialogBottomSheets implements SearchDialogContract.view {

    @Inject
    SearchDialogPresenter mPresenter;
    @Inject
    SearchNotesAdapter searchNotesAdapter;

    private DialogSearchBinding binding;
    private FloatingActionButton fabNewNote;


    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        binding = DialogSearchBinding.inflate(getLayoutInflater());
        requireDialog().setContentView(binding.getRoot());

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            mPresenter.attachView(this);
            mPresenter.viewIsReady();
        } else {
            dismiss();
        }

        fabNewNote.hide();
        binding.actionSearch.requestFocus();

        return requireDialog();
    }


    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        fabNewNote.show();
        binding = null;
        searchNotesAdapter = null;
        mPresenter.detachView();

    }

    @Override
    public void initFabButton() {
        this.fabNewNote = getBaseActivity().findViewById(R.id.newNotesButton);
    }


    @Override
    public void initListeners() {
        binding.closeSearch.setOnClickListener(v -> {
            searchNotesAdapter.cleanResult();
            binding.actionSearch.clearFocus();
            dismiss();
        });
        searchNotesAdapter.setItemClickListener((idNote, view) -> {
            view.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.click_scale));
            startActivity(new Intent(requireActivity(), NoteActivity.class).putExtra("NewNote", false).putExtra("idNote", Long.parseLong(String.valueOf(idNote))).putExtra("shareText", "").putExtra("tagNote", ""));
        });


    }




    private void filter(String text, List<Note> allNotes) {
        ArrayList<Note> newFilter = new ArrayList<>();
        ArrayList<IndexFilter> indexFilter = new ArrayList<>();

        for (Note item : allNotes) {
            int indexTitle = item.getTitle().toLowerCase().indexOf(text.toLowerCase());
            int indexValue = item.getValue().toLowerCase().indexOf(text.toLowerCase());
            int countArrays = indexFilter.size();
            while (indexTitle != -1) {
                indexFilter.add(new IndexFilter(item.id, indexTitle, -1));
                indexTitle = item.getTitle().toLowerCase().indexOf(text.toLowerCase(), indexTitle + 1);
            }

            while (indexValue != -1) {
                indexFilter.add(new IndexFilter(item.id, -1, indexValue));
                indexValue = item.getValue().toLowerCase().indexOf(text.toLowerCase(), indexValue + 1);
            }


            if (indexFilter.size() != countArrays) {
                newFilter.add(item);
            }
        }
        if (newFilter.isEmpty()) {
            searchNotesAdapter.cleanResult();
        } else {

            searchNotesAdapter.filterList(newFilter, text, indexFilter);
        }
    }


    @Override
    public void settingsListResult() {
        binding.resultsList.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, true));
        binding.resultsList.addItemDecoration(new SpacesItemDecoration(25));
        binding.resultsList.setAdapter(searchNotesAdapter);

    }

    @Override
    public void createListenerSearch(Flowable<List<Note>> mNotes) {

        mPresenter.getCompositeDisposable().add(mNotes.subscribeOn(mPresenter.getSchedulerProvider().io()).subscribe(notes -> binding.actionSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        if (newText.length() >= 2) filter(newText, notes);
                        else {
                            searchNotesAdapter.cleanResult();
                        }
                        return false;
                    }
                }))


        );


    }

}
