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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.shopping.R;
import com.example.shopping.databinding.FragmentProductDetailBinding;
import com.example.shopping.model.Product;
import com.example.shopping.viewmodel.ProductDetailViewModel;
import com.example.shopping.viewmodel.StoreViewModel;

import java.util.List;

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
        product = getArguments().getParcelable("product");
        binding.setProduct(product);

        return binding.getRoot();
    }

    private void init(View view) {

        context = requireContext();
        viewModel = new ViewModelProvider(this).get(ProductDetailViewModel.class);

        viewModel.getAll().observe(getViewLifecycleOwner(), products -> {

            Toast.makeText(context, "all Products Changes", Toast.LENGTH_SHORT).show();
        });

        Button btnAddToCart = view.findViewById(R.id.btnAddToCart);
        Button btnAddToCart2 = view.findViewById(R.id.btnAddToCart2);
        btnAddToCart.setOnClickListener(this);
        btnAddToCart2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddToCart:
                Toast.makeText(context, "Add to Cart Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnAddToCart2:
                viewModel.insert(product);
                break;
            default:
                Log.e(TAG, "onClick: didn't specify case");
                break;
        }
    }
}
