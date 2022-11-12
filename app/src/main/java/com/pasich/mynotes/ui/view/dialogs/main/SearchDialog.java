package com.pasich.mynotes.ui.view.dialogs.main;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.dialog.SearchBaseDialogBottomSheets;
import com.pasich.mynotes.data.database.model.Note;
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
            binding.actionSearch.clearFocus();
            dismiss();
        });
        searchNotesAdapter.setItemClickListener(idNote -> startActivity(new Intent(requireActivity(), NoteActivity.class).putExtra("NewNote", false).putExtra("idNote", idNote).putExtra("shareText", "").putExtra("tagNote", "")));


    }


    private void filter(String text, List<Note> allNotes) {
        ArrayList<Note> newFilter = new ArrayList<>();

        for (Note item : allNotes) {
            if (item.getTitle().toLowerCase().contains(text.toLowerCase()) && text.trim().length() >= 2) {
                newFilter.add(item);
            }
        }
        if (newFilter.isEmpty()) {
            searchNotesAdapter.cleanResult();
        } else {

            searchNotesAdapter.filterList(newFilter);
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
                        filter(newText, notes);
                        return false;
                    }
                }))


        );


    }

}
