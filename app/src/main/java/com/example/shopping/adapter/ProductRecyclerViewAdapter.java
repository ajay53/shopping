package com.example.shopping.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shopping.R;
import com.example.shopping.model.Product;
import com.example.shopping.utility.Util;

import java.util.List;

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.ViewHolder> {

    private final OnItemCLickListener onItemCLickListener;
    private final Context context;
    private final List<Product> products;

    public ProductRecyclerViewAdapter(Context context, List<Product> products, OnItemCLickListener onItemCLickListener) {
        this.context = context;
        this.products = products;
        this.onItemCLickListener = onItemCLickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.store_list_item, parent, false);
        return new ViewHolder(view, onItemCLickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        Glide.with(context)
                .asBitmap()
                .placeholder(R.drawable.ic_shopping_cart)
                .load(products.get(position).getUrl())
                .into(holder.imgProduct);

        holder.tvTitle.setText(products.get(position).getTitle());
        holder.tvPrice.setText(Util.getPriceFormattedString(products.get(position).getPrice()));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        OnItemCLickListener onItemCLickListener;
        public ImageView imgProduct;
        public TextView tvTitle;
        public TextView tvPrice;

        public ViewHolder(@NonNull View view, OnItemCLickListener onItemCLickListener) {
            super(view);
            imgProduct = view.findViewById(R.id.imgProduct);
            tvTitle = view.findViewById(R.id.tvTitle);
            tvPrice = view.findViewById(R.id.tvPrice);

            this.onItemCLickListener = onItemCLickListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemCLickListener.onProductClick(getAdapterPosition());
        }
    }

    public interface OnItemCLickListener {
        void onProductClick(int position);
    }
}