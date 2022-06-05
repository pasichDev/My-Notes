package com.pasich.mynotes.view;

import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.pasich.mynotes.view.custom.TitleDialog;

public class BaseView extends TitleDialog {

  protected LinearLayout rootContainer;

  public BaseView(LayoutInflater inflater) {
    super(inflater);
    this.rootContainer = new LinearLayout(getContextRoot());
    this.initRootContainer();
  }

  /** Method for setting up a class container */
  private void initRootContainer() {
    this.rootContainer.setOrientation(LinearLayout.VERTICAL);
  }

  /**
   * Метод который возвращает родительський контейнер в него мы и будем добавлять все следующие View
   *
   * @return - container
   */
  public final LinearLayout getRootContainer() {
    return this.rootContainer;
  }

  /**
   * Метод который реализовует добавления заголовка в диалог
   *
   * @param text - Dialog title
   */
  @Override
  public void addTitle(String text) {
    super.addTitle(text);
    getRootContainer().addView(getTitleView());
  }

  @Override
  public void setTitle(String text) {
    super.setTitle(text);
  }

  /**
   * Метод который возвращает созданные параметры LayoutParam
   *
   * @return - LayoutParam
   */
  public LinearLayout.LayoutParams getLayoutParamDefault() {
    LinearLayout.LayoutParams LP_DEFAULT;
    LP_DEFAULT =
        new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    LP_DEFAULT.setMargins(60, 10, 60, 20);
    return LP_DEFAULT;
  }
}
