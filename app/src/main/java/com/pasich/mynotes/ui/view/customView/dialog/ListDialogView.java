package com.pasich.mynotes.ui.view.customView.dialog;

import android.view.LayoutInflater;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ListView;

import com.pasich.mynotes.R;

public class ListDialogView extends BaseView {

  private final ListView itemsView;

  public ListDialogView(LayoutInflater inflater) {
    super(inflater);
    this.itemsView = createListView();
  }

    /**
   * Метод который создает и настраивет itemsListView
   *
   * @return - itemsListView
   */
  private ListView createListView() {
    ListView lv = new ListView(getContextRoot());
    lv.setLayoutAnimation(
        new LayoutAnimationController(
            AnimationUtils.loadAnimation(lv.getContext(), R.anim.item_animation_dialog)));
    lv.setDivider(null);
    return lv;
  }

  /**
   * Метод который возвращает список для itemsListView
   *
   * @return - itemListVIew
   */
  public ListView getItemsView() {
    return this.itemsView;
  }
}
