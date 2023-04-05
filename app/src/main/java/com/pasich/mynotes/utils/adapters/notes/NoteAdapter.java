package com.pasich.mynotes.utils.adapters.notes;


import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.pasich.mynotes.R;
import com.pasich.mynotes.data.model.DataNote;
import com.pasich.mynotes.data.model.Tag;
import com.pasich.mynotes.utils.adapters.ItemListNote.DemoItemListNoteAdapter;
import com.pasich.mynotes.utils.adapters.baseGenericAdapter.GenericAdapter;
import com.pasich.mynotes.utils.adapters.baseGenericAdapter.GenericAdapterCallback;
import com.pasich.mynotes.utils.recycler.diffutil.DiffUtilNote;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

public class NoteAdapter<VM extends ViewDataBinding> extends GenericAdapter<DataNote, VM> {

    private final List<String> nameTagsHidden = new ArrayList<>();
    private List<DataNote> defaultList = new ArrayList<>();


    @Override
    public void onBindViewHolder(GenericAdapter.RecyclerViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        final DataNote item = getItem(position);
        final int sizeArray = item.getItemListNotes().size();
        if (sizeArray >= 1 && item.getNote().getValue().length() >= 2) {
            TextView textView = holder.itemView.findViewById(R.id.listItemHidden);
            textView.setText(holder.itemView.getContext().getResources().getQuantityString(R.plurals.countListItems, sizeArray, sizeArray));
        } else if (sizeArray >= 1 && item.getNote().getValue().length() < 2) {
            RecyclerView recyclerView = holder.itemView.findViewById(R.id.listItemsRecycler);
            recyclerView.setAdapter(new DemoItemListNoteAdapter(item.getItemListNotes()));
            recyclerView.setVisibility(View.VISIBLE);
        }

    }

    @Inject
    public NoteAdapter(@NonNull DiffUtilNote diffCallback, int layoutId, GenericAdapterCallback<VM, DataNote> bindingInterface) {
        super(diffCallback, layoutId, bindingInterface);
    }


    public int setNameTagsHidden(List<Tag> tagList, String nameTag) {
        nameTagsHidden.clear();
        for (Tag tag : tagList) {
            if (tag.getVisibility() == 1) nameTagsHidden.add(tag.getNameTag());
        }
        if (nameTag.equals("allNotes")) return updateFromVisibilityTags();
        else return getItemCount();
    }


    public void sortList(String arg) {
        ArrayList<DataNote> newList = new ArrayList<>(getCurrentList());
        Collections.sort(newList, new NoteComparator().getComparator(arg));
        submitList(newList);
    }

    public int sortList(List<DataNote> notesList, String arg, String tagSelected) {
        Collections.sort(notesList, new NoteComparator().getComparator(arg));
        defaultList = notesList;
        return filter(tagSelected);
    }


    public int updateFromVisibilityTags() {

        ArrayList<DataNote> newList = new ArrayList<>();
        if (defaultList.size() >= 1) {
            for (DataNote item : defaultList) {
                if (!nameTagsHidden.contains(item.getNote().getTag())) {
                    newList.add(item);
                }
            }
            submitList(newList);
        }
        return newList.size();

    }


    public int filter(String tagSelected) {
        ArrayList<DataNote> newFilter = new ArrayList<>();

        if (tagSelected.equals("allNotes")) {
            for (DataNote item : defaultList) {
                if (!nameTagsHidden.contains(item.getNote().getTag())) {
                    newFilter.add(item);
                }
            }

            if (nameTagsHidden.size() >= 1) {
                submitList(newFilter);
                return newFilter.size();
            } else {
                submitList(defaultList);
                return defaultList.size();
            }
        } else {
            for (DataNote item : defaultList) {

                if (item.getNote().getTag().equals(tagSelected)) {
                    newFilter.add(item);
                }
            }

            submitList(newFilter);
            return newFilter.size();
        }

    }


    public static class NoteComparator {
        public Comparator<DataNote> getComparator(String arg) {
            return switch (arg) {
                case "DataSort" ->
                        (e1, e2) -> Long.compare(e2.getNote().getDate(), e1.getNote().getDate());
                case "TitleSort" ->
                        (e1, e2) -> e1.getNote().getTitle().toLowerCase().compareTo(e2.getNote().getTitle().toLowerCase());
                case "TitleReserve" ->
                        (e1, e2) -> e2.getNote().getTitle().toLowerCase().compareTo(e1.getNote().getTitle().toLowerCase());
                default -> (e1, e2) -> Long.compare(e1.getNote().getDate(), e2.getNote().getDate());
            };
        }
    }

}
