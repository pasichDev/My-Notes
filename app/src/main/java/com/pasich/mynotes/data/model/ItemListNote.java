package com.pasich.mynotes.data.model;

public class ItemListNote {

    private int id;
    private String value;
    private int idNote;
    private boolean isChecked;

    public ItemListNote(String value, int idNote) {
        this.idNote = idNote;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public int getIdNote() {
        return idNote;
    }

    public String getValue() {
        return value;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setIdNote(int idNote) {
        this.idNote = idNote;
    }
}
