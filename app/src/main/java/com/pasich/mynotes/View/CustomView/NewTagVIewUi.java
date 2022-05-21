package com.pasich.mynotes.View.CustomView;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pasich.mynotes.R;

public class NewTagVIewUi {

  private final Button saveButton;
  private final TextView errorMessage;
  private final EditText inputTag;
  protected View inputLayoutUI;
  protected int inputLayout = R.layout.new_tag_layout;

  public NewTagVIewUi(LayoutInflater inflater) {
    this.inputLayoutUI = inflater.inflate(this.inputLayout, null);
    this.saveButton = inputLayoutUI.findViewById(R.id.saveTag);
    this.errorMessage = inputLayoutUI.findViewById(R.id.errorText);
    this.inputTag = inputLayoutUI.findViewById(R.id.inputNameTag);
  }

  /**
   * The method that returns save Button
   *
   * @return - save Button
   */
  public final Button getSaveButton() {
    return this.saveButton;
  }

  public final EditText getInputTag() {
    return this.inputTag;
  }

  public final TextView getErrorMessage() {
    return this.errorMessage;
  }

  public final View getInputLayoutUI() {
    return this.inputLayoutUI;
  }

  public void setInputNormal() {
    inputTag.setBackgroundResource(R.drawable.background_normal_input);
    inputTag.setPadding(20, 20, 20, 20);
  }

  public void setInputError() {
    inputTag.setBackgroundResource(R.drawable.background_normal_error);
    inputTag.setPadding(20, 20, 20, 20);
  }

  /**
   * Метод который реализовует валидацию названия метки которую вводит пользователь Одно правило, не
   * больше 20 символов
   *
   * @param lenght - названия метки
   */
  public void validateText(int lenght) {
    if (lenght == 21) {
      getErrorMessage().setVisibility(View.VISIBLE);
      getSaveButton().setEnabled(false);
      setInputError();
    } else if (lenght == 20) {
      getErrorMessage().setVisibility(View.GONE);
      getSaveButton().setEnabled(true);
      setInputNormal();
    } else if (lenght < 3) getSaveButton().setEnabled(false);
    else {
      getSaveButton().setEnabled(true);
    }
  }
}
