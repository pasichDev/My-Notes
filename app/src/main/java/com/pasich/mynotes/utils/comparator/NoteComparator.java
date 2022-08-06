package com.pasich.mynotes.utils.comparator;

import com.pasich.mynotes.data.notes.Note;

import java.util.Comparator;

public class NoteComparator {
    private final Comparator<Note> COMPARE_BY_TITLE_REVERSE =
            (e1, e2) -> e2.getTitle().toLowerCase().compareTo(e1.getTitle().toLowerCase());
    private final Comparator<Note> COMPARE_BY_TITLE_SORT =
            (e1, e2) -> e1.getTitle().toLowerCase().compareTo(e2.getTitle().toLowerCase());

    private final Comparator<Note> COMPARE_BY_DATE_REVERSE = (e1, e2) -> Math.toIntExact((long) (e1.getDate() - e2.getDate()));
    private final Comparator<Note> COMPARE_BY_DATE_SORT = (e1, e2) -> Math.toIntExact((long) (e2.getDate() - e1.getDate()));


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
