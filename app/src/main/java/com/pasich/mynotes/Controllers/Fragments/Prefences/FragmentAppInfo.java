package com.pasich.mynotes.Controllers.Fragments.Prefences;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.pasich.mynotes.BuildConfig;
import com.pasich.mynotes.Controllers.Dialogs.WhatUpdateDialog;
import com.pasich.mynotes.R;

public class FragmentAppInfo extends PreferenceFragmentCompat {
  @Override
  public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
    setPreferencesFromResource(R.xml.appinfo_preferences, rootKey);
    String versionName = BuildConfig.VERSION_NAME;
    Preference verPrefence = findPreference("verPrefence");
    assert verPrefence != null;
    verPrefence.setTitle("My Notes (v" + versionName + ")");

    Preference ratings = findPreference("ratingApp");
    Preference whatsUpdateKey = findPreference("whatsUpdate");

    assert ratings != null;
    ratings.setOnPreferenceClickListener(
        preference -> {
          final Uri uri = Uri.parse("market://details?id=" + requireContext().getPackageName());
          final Intent rateAppIntent = new Intent(Intent.ACTION_VIEW, uri);
          if (requireContext().getPackageManager().queryIntentActivities(rateAppIntent, 0).size() > 0) {
            startActivity(rateAppIntent);
          } else {
            Toast.makeText(getContext(), getString(R.string.notFoundPlayMarket), Toast.LENGTH_SHORT)
                .show();
          }
          return true;
        });
    assert whatsUpdateKey != null;
    whatsUpdateKey.setOnPreferenceClickListener(
        preference -> {
          new WhatUpdateDialog().show(getParentFragmentManager(), "WhatUpdateDialog");
          return true;
        });
  }
}
