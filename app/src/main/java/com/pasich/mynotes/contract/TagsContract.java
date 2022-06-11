package com.pasich.mynotes.contract;

import com.pasich.mynotes.base.interfaces.MyPresenter;
import com.pasich.mynotes.base.interfaces.MyView;

public interface TagsContract<V extends MyView> {

  interface view extends MyView {}

  interface presenter extends MyPresenter<view> {
    void clickTag();

    void longClickTag();
  }
}
