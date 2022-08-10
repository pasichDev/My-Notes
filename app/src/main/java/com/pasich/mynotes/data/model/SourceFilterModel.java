package com.pasich.mynotes.data.model;

public class SourceFilterModel {


    private String name, type;
    private boolean selected;

    public SourceFilterModel(String name, String type, boolean selected) {
        this.name = name;
        this.type = type;
        this.selected = selected;
    }

    public SourceFilterModel(String name, String type) {
        this.name = name;
        this.type = type;
        this.selected = false;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean getSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
