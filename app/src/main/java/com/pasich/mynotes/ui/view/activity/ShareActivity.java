package com.pasich.mynotes.ui.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pasich.mynotes.R;

/**
 * An activity that is a gateway to save a note via the save button
 */
public class ShareActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (getIntent().getType().equals("text/plain")) {
      startActivity(new Intent(this, NoteActivity.class).putExtra("NewNote", true).putExtra("tagNote", "").putExtra("shareText", handleSendText()));
      finish();
    } else {
      Toast.makeText(this, getString(R.string.notSupportedShare), Toast.LENGTH_LONG).show();
    }
  }

  /**
   * Returns the received text from the heap
   *
   * @return - String (TextData share)
   */
  private String handleSendText() {
    String sharedText = getIntent().getStringExtra(Intent.EXTRA_TEXT);
    return sharedText != null ? sharedText : "";
  }
}
