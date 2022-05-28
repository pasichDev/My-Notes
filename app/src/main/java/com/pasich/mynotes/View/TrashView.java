package com.pasich.mynotes.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.pasich.mynotes.Utils.SpacesItemDecoration;
import com.pasich.mynotes.databinding.ActivityTrashBinding;

public class TrashView {

  private final ActivityTrashBinding binding;

  public TrashView(ActivityTrashBinding binding) {
    this.binding = binding;
    initialization();
  }

  private void initialization() {
    binding.ListTrash.addItemDecoration(new SpacesItemDecoration(25));
    binding.ListTrash.setLayoutManager(
        new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
  }
}
