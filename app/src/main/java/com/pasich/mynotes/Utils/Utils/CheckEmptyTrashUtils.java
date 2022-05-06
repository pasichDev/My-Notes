package com.pasich.mynotes.Utils.Utils;

import android.app.Activity;
import android.view.View;

import com.pasich.mynotes.R;

public class CheckEmptyTrashUtils {

  /**
   * Method that handles displaying a message about an empty note
   **/
  public static void checkCountListTrash(Activity activity) {
    activity.findViewById(R.id.emptyTrash).setVisibility(View.VISIBLE);
    activity.findViewById(R.id.imageEmptyTrash).setVisibility(View.VISIBLE);
  }
}
