package com.example.shopping.adapter;

import android.content.Context;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.example.shopping.R;

public class GlideBindingAdapter {

    @BindingAdapter("imageUrl")
    public static void setImageResource(ImageView view, String url){

        Context context = view.getContext();

        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.ic_dissatisfied)
                .into(view);
    }
}
