package com.pasich.mynotes.view;

import android.graphics.Typeface;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import com.pasich.mynotes.R;

public class NoteView {
  public final Toolbar toolbar;
  public final EditText titleName, valueNote;
  public final TextView editDate;
  private final View view;
  public final ImageButton activatedButton, spechStart;

  public NoteView(View rootView) {
    this.view = rootView;
    this.toolbar = rootView.findViewById(R.id.toolbar_actionbar);
    this.titleName = rootView.findViewById(R.id.notesTitle);
    this.valueNote = rootView.findViewById(R.id.valueNote);
    this.editDate = rootView.findViewById(R.id.dataEditNote);
    this.activatedButton = rootView.findViewById(R.id.editActive);
    this.spechStart = rootView.findViewById(R.id.spechStart);
    initialization();
  }

  private void initialization() {
    setTextSize();
    setStyleText();
  }

  /** Method that changes the font size of the note editing window */
  private void setTextSize() {
    int sizeText =
        PreferenceManager.getDefaultSharedPreferences(view.getContext()).getInt("textSize", 16);
    sizeText = sizeText == 0 ? 20 : sizeText;
    valueNote.setTextSize(sizeText);
  }

  /**
   * Method that changes the text style of the note editing window Bold,Italic,Normal,Italic-Bold
   */
  private void setStyleText() {
    final String style =
        PreferenceManager.getDefaultSharedPreferences(view.getContext())
            .getString("textStyle", "normal");
    int styleSel;
    switch (style) {
      case "italic":
        styleSel = Typeface.ITALIC;
        break;
      case "bold":
        styleSel = Typeface.BOLD;
        break;
      case "bold-italic":
        styleSel = Typeface.BOLD_ITALIC;
        break;
      default:
        styleSel = Typeface.NORMAL;
        break;
    }
    valueNote.setTypeface(null, styleSel);
  }

  public void activatedActivity() {
    titleName.setFocusable(true);
    titleName.setFocusableInTouchMode(true);
    valueNote.setFocusable(true);
    valueNote.setFocusableInTouchMode(true);
    valueNote.requestFocus();
    activatedButton.setVisibility(View.GONE);
    spechStart.setVisibility(View.VISIBLE);
  }
}
