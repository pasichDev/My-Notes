package com.pasich.mynotes.utils.recyclerView;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
  private final int space;

  public SpacesItemDecoration(int space) {
    this.space = space;
  }

  @Override
  public void getItemOffsets(
      Rect outRect,
      @NonNull View view,
      @NonNull RecyclerView parent,
      @NonNull RecyclerView.State state) {
    outRect.bottom = space;
    outRect.left = space;
    outRect.top = space;
    outRect.right = space;
  }
}
