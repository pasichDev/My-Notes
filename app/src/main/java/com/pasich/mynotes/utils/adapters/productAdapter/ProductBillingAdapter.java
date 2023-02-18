package com.pasich.mynotes.utils.adapters.productAdapter;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.android.billingclient.api.ProductDetails;
import com.pasich.mynotes.databinding.ItemProductBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.scopes.ActivityScoped;


@ActivityScoped
public class ProductBillingAdapter extends RecyclerView.Adapter<ProductBillingAdapter.ViewHolder> {

    private List<ProductDetails> defaultProduct = new ArrayList<>();
    private ProductClickListener productClickListener;
    private HashMap<String, Integer> productsBilling;

    @Inject
    public ProductBillingAdapter() {
    }

    public void setProductClickListener(ProductClickListener productClickListener) {
        this.productClickListener = productClickListener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setDefaultProduct(List<ProductDetails> defaultProduct) {
        Collections.sort(defaultProduct, (e1, e2) -> Long.compare(e1.getOneTimePurchaseOfferDetails().getPriceAmountMicros(), e2.getOneTimePurchaseOfferDetails().getPriceAmountMicros()));
        this.defaultProduct = defaultProduct;
        notifyDataSetChanged();
    }

    public void setProductsBilling(HashMap<String, Integer> productsBilling) {
        this.productsBilling = productsBilling;
    }

    @Override
    public int getItemCount() {
        return (null != defaultProduct ? defaultProduct.size() : 0);
    }

    public List<ProductDetails> getData() {
        return this.defaultProduct;
    }


    @NonNull
    @Override
    public ProductBillingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder view = new ProductBillingAdapter.ViewHolder(ItemProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

        if (productClickListener != null) {
            view.itemView.setOnClickListener(v -> productClickListener.byy(view.getAdapterPosition()));
        }

        return view;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductDetails productDetails = getData().get(position);
        holder.ItemBinding.setProduct(productDetails);
        if (productsBilling.size() >= 1) {
            holder.ItemBinding.imageProduct.setImageDrawable(AppCompatResources.getDrawable(holder.itemView.getContext(), productsBilling.get(productDetails.getProductId())));
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemProductBinding ItemBinding;

        ViewHolder(ItemProductBinding binding) {
            super(binding.getRoot());
            ItemBinding = binding;
        }
    }
}
