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
import com.example.shopping.viewmodel.ProductDetailViewModel;

public class ProductDetailFragment extends Fragment implements View.OnClickListener {
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

        viewModel.getAll().observe(getViewLifecycleOwner(), products -> {
            Toast.makeText(context, "all Products Changes", Toast.LENGTH_SHORT).show();
            Toast.makeText(context, "all Products Changes", Toast.LENGTH_SHORT).show();
        });

        Button btnAddToCart = view.findViewById(R.id.btnAddToCart);
        Button btnAddToCart2 = view.findViewById(R.id.btnAddToCart2);
        btnAddToCart.setOnClickListener(this);
        btnAddToCart2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: ");

        int id = v.getId();

        if (id == R.id.btnAddToCart){
            //check for already inCart item

            Toast.makeText(context, "Item already in Cart", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.btnAddToCart2){
            product.setPurchased(true);
            viewModel.insert(product);
        }

    }
}
