package com.pasich.mynotes.ui.view.customView.dialog;

import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class BaseView extends TitleDialogView {

  public int TEXT_MESSAGE_SIZE = 17;
  public LinearLayout.LayoutParams LP_DEFAULT = getLayoutParamDefault();
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
   * Метод который возвращает родительський контейнер в него мы и будем добавлять все следующие
   * MyView
   *
   * @return - container
   */
  public final LinearLayout getRootContainer() {
    return this.rootContainer;
  }

  /**
   * Метод который добавляет MyView в rootContainer
   *
   * @param view - MyView add
   */
  public void addView(android.view.View view) {

    getRootContainer().addView(view);
  }

  public void addView(android.view.View view, LinearLayout.LayoutParams lp) {
    getRootContainer().addView(view, lp);
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
  private LinearLayout.LayoutParams getLayoutParamDefault() {
    LinearLayout.LayoutParams LP_DEFAULT;
    LP_DEFAULT =
        new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    LP_DEFAULT.setMargins(60, 10, 60, 20);
    return LP_DEFAULT;
  }
}
