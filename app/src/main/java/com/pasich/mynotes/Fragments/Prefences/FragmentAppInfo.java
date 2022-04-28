package com.pasich.mynotes.Fragments.Prefences;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.pasich.mynotes.BuildConfig;
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
          final Uri uri = Uri.parse("market://details?id=" + getContext().getPackageName());
          final Intent rateAppIntent = new Intent(Intent.ACTION_VIEW, uri);
          if (getContext().getPackageManager().queryIntentActivities(rateAppIntent, 0).size() > 0) {
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
          AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

          LinearLayout.LayoutParams lp =
              new LinearLayout.LayoutParams(
                  LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
          lp.setMargins(40, 5, 40, 20);

          LayoutInflater inflater = getLayoutInflater();
          LinearLayout container = new LinearLayout(getContext());
          container.setOrientation(LinearLayout.VERTICAL);
          View convertView = (View) inflater.inflate(R.layout.dialog_head_bar, null);
          TextView headText = convertView.findViewById(R.id.textViewHead);
          headText.setText(getString(R.string.app_name) + " " + versionName);
          TextView textView = new TextView(getContext());
          textView.setText(getString(R.string.updateNowM));
          container.addView(convertView);
          container.addView(textView, lp);
          builder.setView(container);
          builder.setNegativeButton(getString(R.string.cancel), null);
          builder.show();
          return true;
        });
  }
}
