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
import androidx.recyclerview.widget.PagerSnapHelper;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.QueryProductDetailsParams;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.transition.platform.MaterialFade;
import com.google.common.collect.ImmutableList;
import com.pasich.mynotes.BuildConfig;
import com.pasich.mynotes.R;
import com.pasich.mynotes.base.activity.BaseActivity;
import com.pasich.mynotes.databinding.ActivityAboutBinding;
import com.pasich.mynotes.utils.adapters.productAdapter.ProductBillingAdapter;
import com.pasich.mynotes.utils.constants.SnackBarInfo;
import com.pasich.mynotes.utils.recycler.LinePagerIndicatorDecoration;
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
    public ProductBillingAdapter productBillingAdapter;
    @Inject
    public LinearLayoutManager mLinearLayoutManager;
    @Named("NotesItemSpaceDecoration")
    @Inject
    public SpacesItemDecoration itemDecorationNotes;
    private BillingClient billingClient;

    private HashMap<String, Integer> getProductsLocal() {
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("one_coffee", R.drawable.ic_cup_off_coffee);
        hashMap.put("two_coffee", R.drawable.ic_glass_off_coffee);
        hashMap.put("server_month", R.drawable.ic_month_server);
        hashMap.put("server_6month", R.drawable.ic_6_month_server);
        hashMap.put("new_func", R.drawable.ic_hot_heart);
        return hashMap;
    }

    private PurchasesUpdatedListener getPurchasesUpdatedListener() {
        return (billingResult, purchases) -> {
            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && purchases != null) {
                onInfoSnack(R.string.byyOk, null, SnackBarInfo.Success, Snackbar.LENGTH_LONG);
            } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
                onInfoSnack(R.string.byyCancel, null, SnackBarInfo.Info, Snackbar.LENGTH_LONG);
            } else {
                onInfoSnack(R.string.byyError, null, SnackBarInfo.Error, Snackbar.LENGTH_LONG);
            }
        };
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
        billingClient = BillingClient.newBuilder(this).setListener(getPurchasesUpdatedListener()).enablePendingPurchases().build();
        initActivity();

    }


    private void startLoadingProducts() {
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    initialListProduct();
                } else {
                    onInfoSnack(R.string.errorConnectedNetwork, null, SnackBarInfo.Error, Snackbar.LENGTH_LONG);
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
                Log.wtf(TAG, "disconected");
            }
        });

        initListeners();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (billingClient.isReady()) {
            billingClient.endConnection();
        }

    }

    private void initialListProduct() {
        ArrayList<QueryProductDetailsParams.Product> products = new ArrayList<>();
        for (Map.Entry<String, Integer> product : getProductsLocal().entrySet()) {
            products.add(QueryProductDetailsParams.Product.newBuilder().setProductId(product.getKey()).setProductType(BillingClient.ProductType.INAPP).build());
        }

        billingClient.queryProductDetailsAsync(QueryProductDetailsParams.newBuilder().setProductList(products).build(), (billingResult, productDetailsList) -> {
            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {

                productBillingAdapter.setProductsBilling(getProductsLocal());
                runOnUiThread(() -> productBillingAdapter.setDefaultProduct(productDetailsList));
            } else {
                onInfoSnack(R.string.errorLoadingsProduct, null, SnackBarInfo.Error, Snackbar.LENGTH_LONG);
            }
        });

    }

    private void initCoffeeList() {
        binding.coffeeDev.addItemDecoration(new LinePagerIndicatorDecoration());
        binding.coffeeDev.setLayoutManager(mLinearLayoutManager);
        binding.coffeeDev.setAdapter(productBillingAdapter);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(binding.coffeeDev);
    }

    private void byyProduct(ProductDetails productDetails) {
        billingClient.launchBillingFlow(this, BillingFlowParams.newBuilder().setProductDetailsParamsList(ImmutableList.of(BillingFlowParams.ProductDetailsParams.newBuilder().setProductDetails(productDetails).build())).build());

    }

    @Override
    public void initListeners() {
        productBillingAdapter.setProductClickListener(position -> byyProduct(productBillingAdapter.getData().get(position)));

    }

    private void initActivity() {
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        binding.versionApp.setText(getString(R.string.versionAndCodeApp, BuildConfig.VERSION_NAME));
        initCoffeeList();
        startLoadingProducts();
    }

    public void sendEmail() {
        Intent intent = new Intent(Intent.ACTION_SENDTO).setData(Uri.parse("mailto:pasichDev@outlook.com"));
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