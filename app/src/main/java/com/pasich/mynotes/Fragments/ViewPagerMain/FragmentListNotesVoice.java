package com.pasich.mynotes.Fragments.ViewPagerMain;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.pasich.mynotes.R;

public class FragmentListNotesVoice extends Fragment {

  @Override
  public void setUserVisibleHint(boolean isVisibleToUser) {
    super.setUserVisibleHint(isVisibleToUser);
    if (isVisibleToUser && isResumed()) {
      getActivity().findViewById(R.id.newNotesButton).setVisibility(View.GONE);
    }
  }

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_list_voice_notes, container, false);
    return view;
  }
}
