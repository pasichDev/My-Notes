package com.pasich.mynotes.utils.adapters;


import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;

import com.pasich.mynotes.data.database.model.Note;
import com.pasich.mynotes.data.database.model.Tag;
import com.pasich.mynotes.utils.adapters.baseGenericAdapter.GenericAdapter;
import com.pasich.mynotes.utils.adapters.baseGenericAdapter.GenericAdapterCallback;
import com.pasich.mynotes.utils.recycler.diffutil.DiffUtilNote;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

public class NoteAdapter<VM extends ViewDataBinding> extends GenericAdapter<Note, VM> {

    private final List<String> nameTagsHidden = new ArrayList<>();
    private List<Note> defaultList = new ArrayList<>();


    @Inject
    public NoteAdapter(@NonNull DiffUtilNote diffCallback, int layoutId, GenericAdapterCallback<VM, Note> bindingInterface) {
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
        ArrayList<Note> newList = new ArrayList<>(getCurrentList());
        Collections.sort(newList, new NoteComparator().getComparator(arg));
        submitList(newList);
    }

    public int sortList(List<Note> notesList, String arg, String tagSelected) {
        Collections.sort(notesList, new NoteComparator().getComparator(arg));
        defaultList = notesList;
        return filter(tagSelected);
    }


    public int updateFromVisibilityTags() {

        ArrayList<Note> newList = new ArrayList<>();
        if (defaultList.size() >= 1) {
            for (Note item : defaultList) {
                if (!nameTagsHidden.contains(item.getTag())) {
                    newList.add(item);
                }
            }
            submitList(newList);
        }
        return newList.size();

    }


    public int filter(String tagSelected) {
        ArrayList<Note> newFilter = new ArrayList<>();

        if (tagSelected.equals("allNotes")) {
            for (Note item : defaultList) {
                if (!nameTagsHidden.contains(item.getTag())) {
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
            for (Note item : defaultList) {

                if (item.getTag().equals(tagSelected)) {
                    newFilter.add(item);
                }
            }

            submitList(newFilter);
            return newFilter.size();
        }

    }


    public static class NoteComparator {
        private final Comparator<Note> COMPARE_BY_TITLE_REVERSE = (e1, e2) -> e2.getTitle().toLowerCase().compareTo(e1.getTitle().toLowerCase());
        private final Comparator<Note> COMPARE_BY_TITLE_SORT = (e1, e2) -> e1.getTitle().toLowerCase().compareTo(e2.getTitle().toLowerCase());

        private final Comparator<Note> COMPARE_BY_DATE_REVERSE = (e1, e2) -> Long.compare(e1.getDate(), e2.getDate());
        private final Comparator<Note> COMPARE_BY_DATE_SORT = (e1, e2) -> Long.compare(e2.getDate(), e1.getDate());


        public Comparator<Note> getComparator(String arg) {
            switch (arg) {
                case "DataSort":
                    return COMPARE_BY_DATE_SORT;
                case "TitleSort":
                    return COMPARE_BY_TITLE_SORT;
                case "TitleReserve":
                    return COMPARE_BY_TITLE_REVERSE;
                default:
                    return COMPARE_BY_DATE_REVERSE;
            }
        }
    }


}
