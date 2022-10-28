package com.pasich.mynotes.data.database.model;

public class Source {

    /**
     * source - содержимое ресурса type - тип ресурса возможные варианты, url, tel, mail.
     */
    private String source, type;

    public Source(String source, String type) {
        this.source = source;
        this.type = type;
    }

    public String getSource() {
        return this.source;
    }

    public String getType() {
        return this.type;
    }

    public void setSourceList(String source) {
        this.source = source;
    }

    public void setTypeList(String type) {
        this.type = type;
    }
}
