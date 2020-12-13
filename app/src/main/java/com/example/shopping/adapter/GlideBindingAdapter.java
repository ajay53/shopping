package com.example.shopping.adapter;

import android.content.Context;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.example.shopping.R;

public class GlideBindingAdapter {

    @BindingAdapter("imageUrl")
    public static void setImageResource(ImageView view, String url) {

        Context context = view.getContext();

        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.ic_menu_store)
                .into(view);
    }

    @BindingAdapter("isFavorite")
    public static void setIsFavorite(ImageView view, boolean isFavorite) {

        Context context = view.getContext();

        if (isFavorite) {
            Glide.with(context)
                    .load(R.drawable.ic_favorite)
                    .placeholder(R.drawable.ic_menu_store)
                    .into(view);
        } else {
            Glide.with(context)
                    .load(R.drawable.ic_not_favorite)
                    .placeholder(R.drawable.ic_menu_store)
                    .into(view);
        }
    }
}
