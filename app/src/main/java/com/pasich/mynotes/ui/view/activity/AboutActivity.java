package com.pasich.mynotes.ui.view.activity;

import static android.content.ContentValues.TAG;
import static com.pasich.mynotes.utils.constants.ContactLink.LINK_APP_SITE;
import static com.pasich.mynotes.utils.constants.ContactLink.LINK_MONOBANK_DONATE;
import static com.pasich.mynotes.utils.constants.ContactLink.LINK_PRIVACY_POLICY;
import static com.pasich.mynotes.utils.constants.ContactLink.LINK_TELEGRAM_DEVELOP;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.QueryProductDetailsParams;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.transition.platform.MaterialFade;
import com.google.common.collect.ImmutableList;
import com.pasich.mynotes.BuildConfig;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.activity.BaseActivity;
import com.pasich.mynotes.databinding.ActivityAboutBinding;
import com.pasich.mynotes.utils.adapters.productAdapter.ProductBindingAdapter;
import com.pasich.mynotes.utils.constants.SnackBarInfo;
import com.pasich.mynotes.utils.recycler.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AboutActivity extends BaseActivity {

    public ActivityAboutBinding binding;
    @Inject
    public ProductBindingAdapter productBindingAdapter;
    @Inject
    public LinearLayoutManager mLinearLayoutManager;
    @Named("NotesItemSpaceDecoration")
    @Inject
    public SpacesItemDecoration itemDecorationNotes;

    private BillingClient billingClient;

    private HashMap<String, Integer> getProductsLocal() {
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("small_coffee", R.drawable.ic_glass_off_coffee);
        hashMap.put("medium_cofee", R.drawable.ic_pack_off_coffee);
        return hashMap;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        selectTheme();
        binding = ActivityAboutBinding.inflate(getLayoutInflater());
        getWindow().setEnterTransition(new MaterialFade().addTarget(binding.activityAbout));
        getWindow().setAllowEnterTransitionOverlap(true);
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        binding.setActivity(this);
        initActivity();


        billingClient = BillingClient.newBuilder(this)
                .setListener((billingResult, purchases) -> {
                    if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK
                            && purchases != null) {
                        onInfoSnack(R.string.byyOk, null, SnackBarInfo.Success, Snackbar.LENGTH_LONG);
                    } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
                        onInfoSnack(R.string.byyCancel, null, SnackBarInfo.Info, Snackbar.LENGTH_LONG);
                    } else {
                        onInfoSnack(R.string.byyError, null, SnackBarInfo.Error, Snackbar.LENGTH_LONG);
                    }
                })
                .enablePendingPurchases()
                .build();


        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    // The BillingClient is ready. You can query purchases here.
                    Log.wtf(TAG, "onBillingSetupFinished: connected ");
                    initialListProduct();
                } else {
                    //ошибка сети показать сообщение
                    Log.wtf(TAG, "no conected");
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
                Log.wtf(TAG, "disconected");
            }
        });


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (billingClient.isReady()) {
            Log.d(TAG, "BillingClient can only be used once -- closing connection");
            // BillingClient can only be used once.
            // After calling endConnection(), we must create a new BillingClient.
            billingClient.endConnection();
        }
    }

    private void initialListProduct() {

        ImmutableList<QueryProductDetailsParams.Product> productsList;
        ArrayList<QueryProductDetailsParams.Product> products = new ArrayList<>();
        for (Map.Entry<String, Integer> product : getProductsLocal().entrySet()) {
            products.add(QueryProductDetailsParams.Product.newBuilder()
                    .setProductId(product.getKey())
                    .setProductType(BillingClient.ProductType.INAPP)
                    .build());
        }

        productsList = ImmutableList.copyOf(products);
        QueryProductDetailsParams queryProductDetailsParams = QueryProductDetailsParams.newBuilder()
                .setProductList(productsList).build();
        billingClient.queryProductDetailsAsync(
                queryProductDetailsParams,
                (billingResult, productDetailsList) -> {
                    if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                        Log.wtf(TAG, "initialListProduct: " + productDetailsList.size());
                        productBindingAdapter.submitList(productDetailsList);
                        Log.wtf(TAG, "initialListProduct: " + productBindingAdapter.getItemCount());

                    }

                }
        );
    }


    private void initListCoffee() {
        //  listCoffee.add(new Coffee().create(getString(R.string.cupCoffee), AppCompatResources.getDrawable(this, R.drawable.ic_cup_off_coffee), 0.50));
        //  listCoffee.add(new Coffee().create(getString(R.string.glassCoffee), AppCompatResources.getDrawable(this, R.drawable.ic_glass_off_coffee), 1));
        //   listCoffee.add(new Coffee().create(getString(R.string.packCoffee), AppCompatResources.getDrawable(this, R.drawable.ic_pack_off_coffee), 5));
        //    listCoffee.add(new Coffee().create(getString(R.string.makerCoffee), AppCompatResources.getDrawable(this, R.drawable.ic_maker_coffee), 20));


    }


    private void initCoffeeList() {
        binding.coffeeDev.addItemDecoration(itemDecorationNotes);
        binding.coffeeDev.setLayoutManager(mLinearLayoutManager);
        binding.coffeeDev.setAdapter(productBindingAdapter);
        productBindingAdapter.setProductsBilling(getProductsLocal());
    }

    private void byyProduct(ProductDetails productDetails) {
        billingClient.launchBillingFlow(this, BillingFlowParams.newBuilder()
                .setProductDetailsParamsList(ImmutableList.of(
                        BillingFlowParams.ProductDetailsParams.newBuilder()
                                .setProductDetails(productDetails)
                                .build()
                ))
                .build());

    }

    @Override
    public void initListeners() {
        productBindingAdapter.setOnItemClickListener(position -> byyProduct(productBindingAdapter.getCurrentList().get(position)));

    }

    private void initActivity() {
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        binding.versionApp.setText(getString(R.string.versionAndCodeApp, BuildConfig.VERSION_NAME));
        initCoffeeList();
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
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(LINK_APP_SITE)));
    }

    public void policyOpen() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(LINK_PRIVACY_POLICY)));
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
        supportFinishAfterTransition();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            supportFinishAfterTransition();
        }

        return true;
    }


}