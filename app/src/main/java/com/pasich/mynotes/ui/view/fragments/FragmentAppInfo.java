package com.pasich.mynotes.ui.view.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.pasich.mynotes.BuildConfig;
import com.pasich.mynotes.R;
import com.pasich.mynotes.ui.view.dialogs.WhatUpdateDialog;

public class FragmentAppInfo extends PreferenceFragmentCompat {
  @Override
  public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
    setPreferencesFromResource(R.xml.appinfo_preferences, rootKey);
    final Preference verPreference = findPreference("verPreference");
    final Preference ratings = findPreference("ratingApp");
    final Preference whatsUpdateKey = findPreference("whatsUpdate");

    assert verPreference != null;
    verPreference.setTitle("My Notes (v" + BuildConfig.VERSION_NAME + ")");

    assert ratings != null;
    ratings.setOnPreferenceClickListener(
        preference -> {
          openPlayMarket();
          return true;
        });
    assert whatsUpdateKey != null;
    whatsUpdateKey.setOnPreferenceClickListener(
        preference -> {
          new WhatUpdateDialog().show(getParentFragmentManager(), "WhatUpdateDialog");
          return true;
        });
  }
  /** The method that implements open application pages in the play market */
  private void openPlayMarket() {
    final Intent rateAppIntent =
        new Intent(
            Intent.ACTION_VIEW,
            Uri.parse("market://details?id=" + requireContext().getPackageName()));
    if (requireContext().getPackageManager().queryIntentActivities(rateAppIntent, 0).size() > 0) {
      startActivity(rateAppIntent);
    } else {
      Toast.makeText(getContext(), getString(R.string.notFoundPlayMarket), Toast.LENGTH_SHORT)
          .show();
    }
  }
}
