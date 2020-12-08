package com.example.shopping.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.shopping.R;
import com.example.shopping.databinding.FragmentProductDetailBinding;
import com.example.shopping.model.Product;
import com.example.shopping.utility.AsyncResponse;
import com.example.shopping.utility.Util;
import com.example.shopping.viewmodel.ProductDetailViewModel;
import com.google.android.material.snackbar.Snackbar;

public class ProductDetailFragment extends Fragment implements View.OnClickListener, AsyncResponse {
    private static final String TAG = "ProductDetailFragment";

    private Context context = null;
    private Product product = null;
    private ProductDetailViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentProductDetailBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_detail, container, false);
        init(binding.getRoot());

        assert getArguments() != null;
        product = (Product) getArguments().getSerializable("product");
        binding.setProduct(product);

        return binding.getRoot();
    }

    private void init(View view) {
        Log.d(TAG, "init: ");

        context = requireContext();
        viewModel = new ViewModelProvider(this).get(ProductDetailViewModel.class);

        Button btnAddToCart = view.findViewById(R.id.btnAddToCart);
        Button btnAddToCart2 = view.findViewById(R.id.btnAddToCart2);
        btnAddToCart.setOnClickListener(this);
        btnAddToCart2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: ");

        int id = v.getId();

        if (id == R.id.btnAddToCart) {
            //check for already inCart item
            viewModel.get(product.getId(), this);
        } else if (id == R.id.btnAddToCart2) {
            product.setPurchased(true);
            viewModel.insert(product);
        }
    }

    @Override
    public void onProcessFinish(Object output) {
        Log.d(TAG, "onProcessFinish: ");

        Product product = (Product) output;

        if (product != null) {
            Util.showSnackBar(this.requireActivity(), "Item already in Cart");
        } else {
            viewModel.insert(this.product);
        }
    }
}
