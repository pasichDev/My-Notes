package com.pasich.mynotes.utils.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.pasich.mynotes.R;
import com.pasich.mynotes.data.model.SourceModel;
import com.pasich.mynotes.databinding.ItemSourceNoteChoiceBinding;

import java.util.ArrayList;

public class NoteSourceAdapter extends BaseAdapter {

  private final ArrayList<SourceModel> listSource;
  private LayoutInflater LayoutInflater;

  public NoteSourceAdapter(ArrayList<SourceModel> list) {
    this.listSource = list;
  }

  @Override
  public int getCount() {
    return listSource.size();
  }

  @Override
  public SourceModel getItem(int i) {
    return listSource != null ? listSource.get(i) : null;
  }

  @Override
  public long getItemId(int i) {
    return 0;
  }

  public View getView(int position, View convertView, ViewGroup parent) {
    View result = convertView;
    ItemSourceNoteChoiceBinding binding;
    if (result == null) {
      if (LayoutInflater == null) {
        LayoutInflater =
                (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      }
      binding = ItemSourceNoteChoiceBinding.inflate(LayoutInflater, parent, false);
      result = binding.getRoot();
      result.setTag(binding);
    } else {
      binding = (ItemSourceNoteChoiceBinding) result.getTag();
    }

    switch (listSource.get(position).getType()) {
      case "Url":
        binding.imageSource.setVisibility(View.VISIBLE);
        binding.imageSource.setImageResource(R.drawable.ic_url);
        break;
      case "Tel":
        binding.imageSource.setVisibility(View.VISIBLE);
        binding.imageSource.setImageResource(R.drawable.ic_tel);
        break;
      case "Mail":
        binding.imageSource.setVisibility(View.VISIBLE);
        binding.imageSource.setImageResource(R.drawable.ic_mail);
        break;
    }

    binding.setChoiceModel(getItem(position));
    return result;
  }
}
