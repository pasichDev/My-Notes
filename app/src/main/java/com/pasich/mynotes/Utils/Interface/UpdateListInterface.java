package com.pasich.mynotes.Utils.Interface;

/**
 * interface that is called after the dialog closes
 */
public interface UpdateListInterface {
  void RestartListView(String tagName);

    void RemoveItem(int position);

}
