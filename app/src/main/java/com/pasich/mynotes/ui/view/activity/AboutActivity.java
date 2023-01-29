package com.pasich.mynotes.ui.view.activity;

import static com.pasich.mynotes.utils.constants.LinkConstants.LINK_MONOBANK_DONATE;
import static com.pasich.mynotes.utils.constants.LinkConstants.LINK_TELEGRAM_DEVELOP;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.pasich.mynotes.BuildConfig;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.activity.BaseActivity;
import com.pasich.mynotes.data.model.Coffee;
import com.pasich.mynotes.databinding.ActivityAboutBinding;
import com.pasich.mynotes.utils.adapters.cofeeAdapter.CoffeeAdapter;
import com.pasich.mynotes.utils.recycler.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AboutActivity extends BaseActivity {

    public ActivityAboutBinding binding;
    @Inject
    public CoffeeAdapter coffeeAdapter;
    @Inject
    public LinearLayoutManager mLinearLayoutManager;
    @Named("NotesItemSpaceDecoration")
    @Inject
    public SpacesItemDecoration itemDecorationNotes;
    private final ArrayList<Coffee> listCoffee = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.setActivity(this);
        initActivity();
    }


    private void initListCoffee() {
        listCoffee.add(new Coffee().create(getString(R.string.cupCoffee), AppCompatResources.getDrawable(this, R.drawable.ic_cup_off_coffee), 0.25));
        listCoffee.add(new Coffee().create(getString(R.string.glassCoffee), AppCompatResources.getDrawable(this, R.drawable.ic_glass_off_coffee), 1));
        listCoffee.add(new Coffee().create(getString(R.string.packCoffee), AppCompatResources.getDrawable(this, R.drawable.ic_pack_off_coffee), 5));
        listCoffee.add(new Coffee().create(getString(R.string.makerCoffee), AppCompatResources.getDrawable(this, R.drawable.ic_maker_coffee), 20));
        initCoffeeList();
    }


    private void initCoffeeList() {
        binding.coffeeDev.addItemDecoration(itemDecorationNotes);
        binding.coffeeDev.setLayoutManager(mLinearLayoutManager);
        binding.coffeeDev.setAdapter(coffeeAdapter);
        coffeeAdapter.submitList(listCoffee);
    }


    @Override
    public void initListeners() {
        coffeeAdapter.setOnItemClickListener(position -> byyCoffee(coffeeAdapter.getCurrentList().get(position)));
    }

    private void initActivity() {
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        binding.versionApp.setText(getString(R.string.versionAndCodeApp, BuildConfig.VERSION_NAME));
        initListCoffee();
        initListeners();
    }

    public void sendEmail() {
        Intent intent = new Intent(Intent.ACTION_SENDTO)
                .setData(Uri.parse("mailto:pasichDev@outlook.com"));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    public void sendTelegram() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(LINK_TELEGRAM_DEVELOP)));
    }

    public void sendMonoBank() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(LINK_MONOBANK_DONATE)));
    }

    public void shareApp() {
        Intent.createChooser(new Intent("android.intent.action.SEND").setType("plain/text").putExtra("android.intent.extra.TEXT", getString(R.string.shareAppText)), getString(R.string.share));
    }


    public void openRatingGooglePlay() {
        final Intent rateAppIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName()));
        if (getPackageManager().queryIntentActivities(rateAppIntent, 0).size() > 0) {
            startActivity(rateAppIntent);
        } else {
            Toast.makeText(this, getString(R.string.notFoundPlayMarket), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_toolbar, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return true;
    }


    private void byyCoffee(Coffee coffee) {
        Toast.makeText(this, coffee.getTitle(), Toast.LENGTH_LONG).show();
    }

}