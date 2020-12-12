package com.example.shopping.utility;

import android.view.View;

import com.example.shopping.model.Product;

public interface CustomOnClick {
    void setCustomOnClickListener(View view, final Product product);
}
