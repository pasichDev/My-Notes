package com.pasich.mynotes.Adapters.SourceNoteList;

public class SourceListContent {

    /**
     * source - содержимое ресурса
     * type - тип ресурса возможные варианты, url, tel, mail.
     */
    private String source, type;

    public SourceListContent(String source, String type){
        this.source = source;
        this.type = type;
    }

    public String getSource(){ return this.source; }

    public String getType(){ return this.type; }

    public void setSouceList(String source) {
        this.source = source;
    }

    public void setTypeList(String type) {
        this.type = type;
    }




}