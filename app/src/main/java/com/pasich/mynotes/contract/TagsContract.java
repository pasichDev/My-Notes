package com.pasich.mynotes.contract;

import com.pasich.mynotes.base.MyPresenter;
import com.pasich.mynotes.base.MyView;

public interface TagsContract<V extends MyView> {

  interface view extends MyView {}

  interface presenter extends MyPresenter<view> {}
}
