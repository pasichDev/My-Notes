package com.pasich.mynotes.ui.view.dialogs.main;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.pasich.mynotes.R;
import com.pasich.mynotes.data.DataManager;
import com.pasich.mynotes.data.notes.Note;
import com.pasich.mynotes.databinding.DialogSearchBinding;
import com.pasich.mynotes.ui.contract.dialog.SearchDialogContract;
import com.pasich.mynotes.ui.presenter.dialog.SearchDialogPresenter;
import com.pasich.mynotes.utils.adapters.SearchNotesAdapter;
import com.pasich.mynotes.utils.recycler.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class SearchDialog extends BottomSheetDialogFragment implements SearchDialogContract.view {

    private final SearchDialogPresenter searchDialogPresenter;
    public DataManager dataManager;
    private DialogSearchBinding binding;
    private BottomSheetDialog builder;
    private SearchNotesAdapter searchNotesAdapter;

    public SearchDialog() {
        this.dataManager = new DataManager();
        this.searchDialogPresenter = new SearchDialogPresenter();

    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        builder = new BottomSheetDialog(requireContext(), R.style.TransparentDialog);
        binding = DialogSearchBinding.inflate(getLayoutInflater());
        builder.setContentView(binding.getRoot());


        binding.actionSearch.requestFocus();


        init();
        searchDialogPresenter.attachView(this);
        searchDialogPresenter.setDataManager(dataManager);
        searchDialogPresenter.viewIsReady();

        return builder;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        onDestroy();
        requireActivity()
                .getWindow()
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


    @Override
    public void init() {
        builder.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        ((InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                .toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.SHOW_FORCED);
    }

    @Override
    public void initListeners() {

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
    public void onDestroy() {
        super.onDestroy();
        binding = null;
        builder = null;
        searchDialogPresenter.detachView();
        dataManager = null;
    }

    @Override
    public void settingsListResult() {
        binding.resultsList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.resultsList.addItemDecoration(new SpacesItemDecoration(25));
        searchNotesAdapter = new SearchNotesAdapter();
        binding.resultsList.setAdapter(searchNotesAdapter);

    }

    @Override
    public void createListenerSearch(List<Note> mNotes) {
        binding.actionSearch.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {

                        filter(newText, mNotes);
                        return false;
                    }
                });


    }
}
