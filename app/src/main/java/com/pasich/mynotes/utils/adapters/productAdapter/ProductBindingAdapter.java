package com.pasich.mynotes.utils.adapters.productAdapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.android.billingclient.api.ProductDetails;
import com.pasich.mynotes.databinding.ItemProductBinding;

import java.util.HashMap;

import javax.inject.Inject;


public class ProductBindingAdapter extends ListAdapter<ProductDetails, ProductBindingAdapter.ViewHolder> {

    private ProductClickListener coffeeClickListener;
    private HashMap<String, Integer> productsBilling;

    @Inject
    public ProductBindingAdapter(@NonNull DiffUtil.ItemCallback<ProductDetails> diffCallback) {
        super(diffCallback);
    }

    public void setProductsBilling(HashMap<String, Integer> productsBilling) {
        this.productsBilling = productsBilling;
    }

    public void setOnItemClickListener(ProductClickListener coffeeClickListener) {
        this.coffeeClickListener = coffeeClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder view = new ViewHolder(ItemProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

        if (coffeeClickListener != null) {
            view.itemView.setOnClickListener(v -> coffeeClickListener.byy(view.getAdapterPosition()));


        }
        return view;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductDetails productDetails = getItem(position);
        holder.ItemBinding.setProduct(productDetails);
        if (productsBilling.size() >= 1) {
            holder.ItemBinding.imageCoffee.setImageDrawable(AppCompatResources.getDrawable(holder.itemView.getContext(), productsBilling.get(productDetails.getProductId())));
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
