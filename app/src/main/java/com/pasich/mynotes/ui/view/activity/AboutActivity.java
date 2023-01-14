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
import androidx.recyclerview.widget.RecyclerView;

import com.pasich.mynotes.BuildConfig;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.activity.BaseActivity;
import com.pasich.mynotes.data.database.model.Coffee;
import com.pasich.mynotes.databinding.ActivityAboutBinding;
import com.pasich.mynotes.utils.adapters.cofeeAdapter.CoffeeAdapter;
import com.pasich.mynotes.utils.recycler.SpacesItemDecoration;
import com.pasich.mynotes.utils.recycler.diffutil.DiffUtiCoffee;

import java.util.ArrayList;
import java.util.Objects;

public class AboutActivity extends BaseActivity {

    private ActivityAboutBinding binding;
    private final ArrayList<Coffee> listCoffee = new ArrayList<>();

    private CoffeeAdapter coffeeAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
        binding.monobankDonat.setOnClickListener(null);
    }

    private void initListCoffee() {
        listCoffee.add(new Coffee().create(getString(R.string.cupCoffee), AppCompatResources.getDrawable(this, R.drawable.ic_cup_off_coffee), 0.25));
        listCoffee.add(new Coffee().create(getString(R.string.glassCoffee), AppCompatResources.getDrawable(this, R.drawable.ic_glass_off_coffee), 1));
        listCoffee.add(new Coffee().create(getString(R.string.packCoffee), AppCompatResources.getDrawable(this, R.drawable.ic_pack_off_coffee), 5));
        listCoffee.add(new Coffee().create(getString(R.string.makerCoffee), AppCompatResources.getDrawable(this, R.drawable.ic_maker_coffee), 20));
        initCoffeeList();
    }


    private void initCoffeeList() {
        coffeeAdapter = new CoffeeAdapter(new DiffUtiCoffee());
        binding.coffeeDev.addItemDecoration(new SpacesItemDecoration(15));
        binding.coffeeDev.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        binding.coffeeDev.setAdapter(coffeeAdapter);
        coffeeAdapter.submitList(listCoffee);
    }


    @Override
    public void initListeners() {
        coffeeAdapter.setOnItemClickListener(position -> byyCoffee(coffeeAdapter.getCurrentList().get(position)));
        binding.telegramSend.setOnClickListener(v ->
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(LINK_TELEGRAM_DEVELOP))));
        binding.emailSend.setOnClickListener(v -> sendEmail());
        binding.monobankDonat.setOnClickListener(v -> monoBankLink_Open());
        binding.shareApp.setOnClickListener(v -> startActivity(Intent.createChooser(new Intent("android.intent.action.SEND").setType("plain/text").putExtra("android.intent.extra.TEXT", getString(R.string.shareAppText)), getString(R.string.share))));
        binding.ratingApp.setOnClickListener(v -> openIntentGooglePlay());
    }

    private void initActivity() {
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        binding.versionApp.setText(getString(R.string.versionAndCodeApp, BuildConfig.VERSION_NAME));
        initListCoffee();
        initListeners();
    }

    private void sendEmail() {
        Intent intent = new Intent(Intent.ACTION_SENDTO)
                .setData(Uri.parse("mailto:pasichDev@outlook.com"));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    private void monoBankLink_Open() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(LINK_MONOBANK_DONATE)));
    }


    private void openIntentGooglePlay() {
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