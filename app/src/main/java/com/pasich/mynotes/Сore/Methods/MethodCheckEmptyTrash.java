package com.pasich.mynotes.Сore.Methods;

import android.app.Activity;
import android.view.View;

import com.pasich.mynotes.R;

public class MethodCheckEmptyTrash {

  /** Метод который обрабатывает отображение сообщения про пустую заметку */
  public static void checkCountListTrashActivity(Activity activity) {
    activity.findViewById(R.id.emptyTrash).setVisibility(View.VISIBLE);
    activity.findViewById(R.id.imageEmptyTrash).setVisibility(View.VISIBLE);
  }
}
