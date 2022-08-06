package com.pasich.mynotes.utils.adapters;

import static com.pasich.mynotes.data.notes.Note.COMPARE_BY_DATE_REVERSE;
import static com.pasich.mynotes.data.notes.Note.COMPARE_BY_DATE_SORT;
import static com.pasich.mynotes.data.notes.Note.COMPARE_BY_TITLE_REVERSE;
import static com.pasich.mynotes.data.notes.Note.COMPARE_BY_TITLE_SORT;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.pasich.mynotes.data.notes.Note;
import com.pasich.mynotes.databinding.ItemNoteBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NotesAdapter extends ListAdapter<Note, NotesAdapter.ViewHolder> {

  private OnItemClickListener mOnItemClickListener;

  public NotesAdapter(@NonNull noteDiff diffCallback) {
    super(diffCallback);
  }

  public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.mOnItemClickListener = onItemClickListener;
  }

  @RequiresApi(api = Build.VERSION_CODES.N)
  public void sortList(String arg) {
    /* */
    sortByType(arg);
  }

  @RequiresApi(api = Build.VERSION_CODES.N)
  public void sortByType(String arg) {
    List<Note> sortedList = new ArrayList<>(getCurrentList());
    //  sortedList.sort(getArg(arg));
    Collections.sort(sortedList, getArg(arg));
    for (Note note : sortedList) {

      Log.wtf("pasic", "namenote: " + note.getTitle());
    }
  /*  List<Note> displayOrderList = new ArrayList<>(getCurrentList());
    for (int i = 0; i < sortedList.size(); ++i) {
      int toPos = sortedList.indexOf(displayOrderList.get(i));
      notifyItemMoved(i, toPos);
      listMoveTo(displayOrderList, i, toPos);
    }
   */
    submitList(sortedList);
    notifyDataSetChanged();

  }

  private void listMoveTo(List<Note> list, int fromPos, int toPos) {
    Note fromValue = list.get(fromPos);
    int delta = fromPos < toPos ? 1 : -1;
    for (int i = fromPos; i != toPos; i += delta) {
      list.set(i, list.get(i + delta));
    }
    list.set(toPos, fromValue);
  }


  private Comparator<Note> getArg(String arg) {

    Log.wtf("pasic", "getArg: " + arg);
    switch (arg) {
      case "DataSort":
        return COMPARE_BY_DATE_SORT;
      case "TitleSort":
        return COMPARE_BY_TITLE_SORT;
      case "TitleReserve":
        return COMPARE_BY_TITLE_REVERSE;
      default:
        return COMPARE_BY_DATE_REVERSE;
    }
  }


  @NonNull
  @Override
  public NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    ViewHolder view =
            new NotesAdapter.ViewHolder(
                    ItemNoteBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    if (mOnItemClickListener != null) {
      view.itemView.setOnClickListener(
          v -> mOnItemClickListener.onClick(view.getAdapterPosition()));

      view.itemView.setOnLongClickListener(
          v -> {
            mOnItemClickListener.onLongClick(view.getAdapterPosition());
            return false;
          });
    }

    return view;
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.ItemBinding.setNoteItem(getCurrentList().get(position));
  }

  @Override
  public void onBindViewHolder(
      @NonNull NotesAdapter.ViewHolder holder, int position, @NonNull List<Object> payloads) {
    if (payloads.isEmpty()) {
      super.onBindViewHolder(holder, position, payloads);
    } else {
      for (Object payload : payloads) {
        int PAYLOAD_BACKGROUND = 22;
        if (payload.equals(PAYLOAD_BACKGROUND)) {
          holder.ItemBinding.setCheckedItem(getCurrentList().get(position).getChecked());
        }
      }
    }
  }

  public interface OnItemClickListener {
    void onClick(int position);

    void onLongClick(int position);
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    ItemNoteBinding ItemBinding;

    ViewHolder(ItemNoteBinding binding) {
      super(binding.getRoot());
      ItemBinding = binding;
    }
  }

  public static class noteDiff extends DiffUtil.ItemCallback<Note> {

    @Override
    public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
      return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
      return oldItem.getTitle().equals(newItem.getTitle())
              && oldItem.getValue().equals(newItem.getValue())
              && oldItem.getTag().length() != newItem.getTag().length();
    }
  }
}
