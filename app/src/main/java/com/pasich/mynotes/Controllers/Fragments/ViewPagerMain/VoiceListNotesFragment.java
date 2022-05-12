package com.pasich.mynotes.Controllers.Fragments.ViewPagerMain;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.pasich.mynotes.R;
import com.pasich.mynotes.Utils.Check.CheckFolderUtils;

public class VoiceListNotesFragment extends Fragment {

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_list_voice_notes, container, false);
    new CheckFolderUtils().checkSystemFolder(requireContext().getFilesDir());
    return view;
  }

}
