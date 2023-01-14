package com.pasich.mynotes.ui.view.activity;

import static com.pasich.mynotes.utils.constants.LinkConstants.LINK_TELEGRAM_DEVELOP;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pasich.mynotes.BuildConfig;
import com.pasich.mynotes.R;
import com.pasich.mynotes.databinding.ActivityAboutBinding;

import java.util.Objects;

public class AboutActivity extends AppCompatActivity {

    private ActivityAboutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initActivity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.telegramSend.setOnClickListener(null);
        binding.emailSend.setOnClickListener(null);
        binding.shareApp.setOnClickListener(null);
        binding.ratingApp.setOnClickListener(null);
    }

    private void initListener() {
        binding.telegramSend.setOnClickListener(v ->
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(LINK_TELEGRAM_DEVELOP))));
        binding.emailSend.setOnClickListener(v -> sendEmail());
        binding.shareApp.setOnClickListener(v -> startActivity(Intent.createChooser(new Intent("android.intent.action.SEND").setType("plain/text").putExtra("android.intent.extra.TEXT", getString(R.string.shareAppText)), getString(R.string.share))));
        binding.ratingApp.setOnClickListener(v -> openIntentGooglePlay());
    }

    private void initActivity() {
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        binding.versionApp.setText(getString(R.string.versionAndCodeApp, BuildConfig.VERSION_NAME));
        initListener();
    }

    private void sendEmail() {
        Intent intent = new Intent(Intent.ACTION_SENDTO)
                .setData(Uri.parse("mailto:pasichDev@outlook.com"));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    private void openIntentGooglePlay() {
        final Intent rateAppIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName()));
        if (getPackageManager().queryIntentActivities(rateAppIntent, 0).size() > 0) {
            startActivity(rateAppIntent);
        } else {
            Toast.makeText(this, getString(R.string.notFoundPlayMarket), Toast.LENGTH_SHORT).show();
        }
    }

}