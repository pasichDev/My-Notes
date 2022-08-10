package com.pasich.mynotes.ui.view.dialogs.note;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.pasich.mynotes.R;
import com.pasich.mynotes.data.model.SourceFilterModel;
import com.pasich.mynotes.ui.view.customView.dialog.ListDialogView;
import com.pasich.mynotes.utils.SearchSourceNote;
import com.pasich.mynotes.utils.adapters.FilterSourceAdapter;
import com.pasich.mynotes.utils.adapters.NoteSourceAdapter;
import com.pasich.mynotes.utils.recycler.SpacesItemDecoration;

import java.util.ArrayList;

public class SourceNoteDialog extends DialogFragment {
    private final SearchSourceNote searchSourceNote;
    private RecyclerView listFilter;
    private FilterSourceAdapter filterSourceAdapter;
    private NoteSourceAdapter noteSourceAdapter;


    public SourceNoteDialog(SearchSourceNote searchSourceNote) {
        this.searchSourceNote = searchSourceNote;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final BottomSheetDialog builder = new BottomSheetDialog(requireContext());
        final ListDialogView listDialogView = new ListDialogView(getLayoutInflater());

        listDialogView.addTitle(getString(R.string.sourceNotes));

        listDialogView.LP_DEFAULT.setMargins(30, 0, 10, 40);
        listDialogView.addView(createRecycleView(), listDialogView.LP_DEFAULT);

        noteSourceAdapter = new NoteSourceAdapter(searchSourceNote.getList());
        listDialogView.getItemsView().setAdapter(noteSourceAdapter);


        filterSourceAdapter.setOnItemClickListener(
                new FilterSourceAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(int position) {
                        chooseItem(position);
                    }

                    @Override
                    public void onLongClick(int position) {

                    }
                });

        listDialogView.getItemsView().setOnItemClickListener(
                (parent, v, position, id) -> {

                    dismiss();
                });


        listDialogView.getRootContainer().addView(listDialogView.getItemsView());
        builder.setContentView(listDialogView.getRootContainer());
        return builder;
    }

    private void chooseItem(int position) {
        filterSourceAdapter.chooseItem(position);
        noteSourceAdapter.refreshList(filterSourceAdapter.getItem(position).getType(), searchSourceNote.getList());
    }


    private RecyclerView createRecycleView() {
        listFilter = new RecyclerView(requireContext());
        listFilter.addItemDecoration(new SpacesItemDecoration(5));
        listFilter.setLayoutManager(
                new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));
        filterSourceAdapter = new FilterSourceAdapter(getArrayItems());
        listFilter.setAdapter(filterSourceAdapter);

        return listFilter;
    }

    private ArrayList<SourceFilterModel> getArrayItems() {
        ArrayList<SourceFilterModel> filterList = new ArrayList<>();
        filterList.add(new SourceFilterModel(getString(R.string.links), "Url", true));
        filterList.add(new SourceFilterModel(getString(R.string.tel), "Tel"));
        filterList.add(new SourceFilterModel(getString(R.string.mail), "Mail"));
        return filterList;
    }
}
