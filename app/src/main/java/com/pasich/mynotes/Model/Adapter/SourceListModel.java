package com.pasich.mynotes.Model.Adapter;

public class SourceListModel {

  private final String source;
  private final String type;

  public SourceListModel(String source, String type) {
    this.source = source;
    this.type = type;
  }

  public String getSource() {
    return this.source;
  }

  public String getType() {
    return this.type;
  }
}
