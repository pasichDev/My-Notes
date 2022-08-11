package com.pasich.mynotes.ui.view.dialogs.note;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

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
import com.pasich.mynotes.utils.adapters.SourceAdapter;
import com.pasich.mynotes.utils.recycler.SpacesItemDecoration;

import java.util.ArrayList;

public class SourceNoteDialog extends DialogFragment {
    private final SearchSourceNote searchSourceNote;
    private FilterSourceAdapter filterSourceAdapter;
    private SourceAdapter sourceAdapter;


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

        sourceAdapter = new SourceAdapter(searchSourceNote.getListSources("Url"));
        listDialogView.getItemsView().setAdapter(sourceAdapter);


        filterSourceAdapter.setOnItemClickListener(
                new FilterSourceAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(int position) {
                        chooseItem(position);
                        listDialogView.getItemsView().scheduleLayoutAnimation();
                    }

                    @Override
                    public void onLongClick(int position) {

                    }
                });

        listDialogView.getItemsView().setOnItemClickListener(
                (parent, v, position, id) -> {
                    Intent intent = null;
                    if (sourceAdapter.getItem(position).getType().equals("Mail")) {
                        intent = new Intent(Intent.ACTION_SENDTO)
                                .setData(Uri.parse("mailto:" + sourceAdapter.getItem(position).getSource()));
                    }
                    if (sourceAdapter.getItem(position).getType().equals("Url")) {
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sourceAdapter.getItem(position).getSource()));
                    }
                    if (sourceAdapter.getItem(position).getType().equals("Tel")) {
                        intent = new Intent(Intent.ACTION_DIAL)
                                .setData(Uri.parse("tel:" + sourceAdapter.getItem(position).getSource()));
                    }
                    assert intent != null;
                    if (intent.resolveActivity(requireContext().getPackageManager()) != null) {
                        startActivity(intent);
                    }
                });

        listDialogView.getItemsView().setOnItemLongClickListener((parent, v, position, id) -> {
            String selectedItem = sourceAdapter.getItem(position).getSource();
            ClipboardManager clipboard =
                    (ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setPrimaryClip(ClipData.newPlainText(selectedItem, selectedItem));
            Toast.makeText(requireContext(), getString(R.string.copyX) + " " + selectedItem, Toast.LENGTH_SHORT)
                    .show();
            return true;
        });

        listDialogView.getRootContainer().addView(listDialogView.getItemsView());
        builder.setContentView(listDialogView.getRootContainer());
        return builder;
    }

    private void chooseItem(int position) {
        filterSourceAdapter.chooseItem(position);
        sourceAdapter.refreshList(searchSourceNote.getListSources(filterSourceAdapter.getItem(position).getType()));
    }


    private RecyclerView createRecycleView() {
        RecyclerView listFilter = new RecyclerView(requireContext());
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
