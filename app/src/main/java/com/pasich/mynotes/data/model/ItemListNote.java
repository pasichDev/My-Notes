package com.pasich.mynotes.data.model;

public class ItemListNote {

    private int id;
    private String value;
    private int idNote;
    private boolean isChecked;

    private boolean isTrash;

    private boolean isSystem;

    public ItemListNote(String value, int idNote, boolean isSystem) {
        this.idNote = idNote;
        this.value = value;
        this.isSystem = isSystem;
    }

    public ItemListNote(String value, int idNote) {
        this.idNote = idNote;
        this.value = value;
        this.isSystem = false;
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

    public boolean isTrash() {
        return isTrash;
    }

    public void setTrash(boolean trash) {
        isTrash = trash;
    }

    public boolean isSystem() {
        return isSystem;
    }

    public void setSystem(boolean system) {
        isSystem = system;
    }
}
