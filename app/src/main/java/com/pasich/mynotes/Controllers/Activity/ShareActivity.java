package com.pasich.mynotes.Controllers.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.pasich.mynotes.R;

/** An activity that is a gateway to save a note via the save button */
public class ShareActivity extends AppCompatActivity {

  protected ActivityResultLauncher<Intent> startActivity =
      registerForActivityResult(
          new ActivityResultContracts.StartActivityForResult(),
          result -> {
            if (result.getData() != null) finish();
          });

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (getIntent().getType().equals("text/plain")) {
      startActivity.launch(
          new Intent(this, NoteActivity.class)
              .putExtra("NewNote", true)
              .putExtra("shareText", handleSendText()));
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
