package com.pasich.mynotes.utils.adapters.cofeeAdapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.pasich.mynotes.R;
import com.pasich.mynotes.data.database.model.Coffee;
import com.pasich.mynotes.databinding.ItemCoffeeBinding;

import javax.inject.Inject;


public class CoffeeAdapter extends ListAdapter<Coffee, CoffeeAdapter.ViewHolder> {

    private CoffeeClickListener coffeeClickListener;

    @Inject
    public CoffeeAdapter(@NonNull DiffUtil.ItemCallback<Coffee> diffCallback) {
        super(diffCallback);
    }

    public void setOnItemClickListener(CoffeeClickListener coffeeClickListener) {
        this.coffeeClickListener = coffeeClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder view = new ViewHolder(ItemCoffeeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

        if (coffeeClickListener != null) {
            view.itemView.setOnClickListener(v -> coffeeClickListener.byy(view.getAdapterPosition()));


        }
        return view;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Coffee coffee = getItem(position);

        holder.ItemBinding.imageCoffee.setImageDrawable(coffee.getImage());
        holder.ItemBinding.titleCoffee.setText(coffee.getTitle());
        holder.ItemBinding.priceCoffee.setText(String.valueOf(holder.itemView.getContext().getString(R.string.coffeePrice, coffee.getPrice())));
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemCoffeeBinding ItemBinding;

        ViewHolder(ItemCoffeeBinding binding) {
            super(binding.getRoot());
            ItemBinding = binding;
        }
    }


}
