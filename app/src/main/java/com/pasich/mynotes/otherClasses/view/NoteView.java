package com.pasich.mynotes.otherClasses.view;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.pasich.mynotes.R;

public class NoteView {
 // public final Toolbar toolbar;
  public final EditText titleName, valueNote;
  public final TextView editDate;
  private final View view;
  public final ImageButton activatedButton, spechStart;

  public NoteView(View rootView) {
    this.view = rootView;
      //  this.toolbar = rootView.findViewById(R.id.toolbar_actionbar);
    this.titleName = rootView.findViewById(R.id.notesTitle);
    this.valueNote = rootView.findViewById(R.id.valueNote);
    this.editDate = rootView.findViewById(R.id.dataEditNote);
    this.activatedButton = rootView.findViewById(R.id.editActive);
    this.spechStart = rootView.findViewById(R.id.spechStart);
    initialization();
  }

  private void initialization() {
      //  setTextSize();
      // setStyleText();
  }


    /**
     * Method that changes the text style of the note editing window Bold,Italic,Normal,Italic-Bold
     */


}
