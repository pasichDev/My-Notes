package com.pasich.mynotes.view.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pasich.mynotes.databinding.HeadViewBinding;

/** A class that simplifies working with dialogs TitleDialogView uiDialog = new */
public class TitleDialogView {

  private final HeadViewBinding binding;
  private final Context context;

  public TitleDialogView(LayoutInflater inflater) {
    this.binding = HeadViewBinding.inflate(inflater, null, false);
    this.context = binding.getRoot().getContext();
  }

  /**
   * Контекст который мы получим из раздутого макета binding
   *
   * @return - context
   */
  public Context getContextRoot() {
    return this.context;
  }

  /**
   * Method for setting header text uiDialog.setHeadTextView(string);
   *
   * @param text - Dialog title
   */
  protected void addTitle(String text) {
    if (binding.HeadTextDialog.getParent() != null) {
      ((ViewGroup) binding.HeadTextDialog.getParent()).removeView(binding.HeadTextDialog);
    }
    this.binding.HeadTextDialog.setText(text);
  }

  /**
   * Метод который изменяет заголовок
   *
   * @param text - Dialog title
   */
  protected void setTitle(String text) {
    binding.HeadTextDialog.setText(text);
  }

  /**
   * Метод который возвращает заголовок
   *
   * @return - editText
   */
  protected TextView getTitleView() {
    return binding.HeadTextDialog;
  }
}
