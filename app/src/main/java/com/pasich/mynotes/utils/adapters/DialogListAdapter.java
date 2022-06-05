package com.pasich.mynotes.utils.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.pasich.mynotes.databinding.ListDialogBinding;
import com.pasich.mynotes.models.adapter.ChoiceModel;

import java.util.ArrayList;

public class DialogListAdapter extends BaseAdapter {

  private final ArrayList<ChoiceModel> listNotes;
  private LayoutInflater LayoutInflater;

  public DialogListAdapter(ArrayList<ChoiceModel> list) {
    this.listNotes = list;
  }

  @Override
  public int getCount() {
    return listNotes.size();
  }

  @Override
  public ChoiceModel getItem(int i) {
    return listNotes != null ? listNotes.get(i) : null;
  }

  @Override
  public long getItemId(int i) {
    return 0;
  }

  public View getView(int position, View convertView, ViewGroup parent) {
    View result = convertView;
    ListDialogBinding binding;
    if (result == null) {
      if (LayoutInflater == null) {
        LayoutInflater =
            (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      }
      binding = ListDialogBinding.inflate(LayoutInflater, parent, false);
      result = binding.getRoot();
      result.setTag(binding);
    } else {
      binding = (ListDialogBinding) result.getTag();
    }

    binding.setChoiceModel(getItem(position));
    return result;
  }
}
