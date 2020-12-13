package com.example.shopping.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.shopping.R;
import com.example.shopping.databinding.FragmentProductDetailBinding;
import com.example.shopping.model.Product;
import com.example.shopping.utility.AsyncResponse;
import com.example.shopping.utility.CustomOnClick;
import com.example.shopping.viewmodel.ProductDetailViewModel;

public class ProductDetailFragment extends Fragment implements View.OnClickListener, CustomOnClick, AsyncResponse {
    private static final String TAG = "ProductDetailFragment";

    private Context context = null;
    private Product product = null;
    private ProductDetailViewModel viewModel;
    private ImageView imgIsFavorite;
    private boolean isSettingFavorite;
    private FragmentProductDetailBinding binding;
    private ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_detail, container, false);
        initializeViews(binding);

        return binding.getRoot();
    }

    private void init() {
        Log.d(TAG, "init: ");

        context = requireContext();
        viewModel = new ViewModelProvider(this).get(ProductDetailViewModel.class);

        assert getArguments() != null;
        product = (Product) getArguments().getSerializable("product");
        viewModel.get(product.getId(), this);
    }

    private void initializeViews(FragmentProductDetailBinding binding) {
        imgIsFavorite = binding.getRoot().findViewById(R.id.imgIsFavorite);
        progressBar = binding.getRoot().findViewById(R.id.progressBar);
        Button btnAddToCart = binding.getRoot().findViewById(R.id.btnAddToCart);
        btnAddToCart.setOnClickListener(this);

        binding.setCustomClick(this);
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: ");

        int id = v.getId();

        if (id == R.id.btnAddToCart) {
            //check for already inCart item
            viewModel.get(product.getId(), this);
        }
    }

    @Override
    public void setCustomOnClickListener(View view, Product product) {
        Log.d(TAG, "setCustomOnClickListener: ");

        int id = view.getId();
        if (id == view.findViewById(R.id.imgIsFavorite).getId()) {

            isSettingFavorite = true;
            imgIsFavorite = view.findViewById(R.id.imgIsFavorite);
            this.product = product;
            Log.d(TAG, "setCustomOnClick: imgIsFavorite Id: " + id + " Product: " + product.toString());

            viewModel.get(product.getId(), this);
        }
    }

    @Override
    public void onAsyncProcessFinish(Object output) {
        Log.d(TAG, "onProcessFinish: ");

        Product product = (Product) output;

        if (isSettingFavorite) {
            if (product != null && product.isFavorite()) {

                imgIsFavorite.setImageResource(R.drawable.ic_not_favorite);
                product.setFavorite(false);
                viewModel.insert(product);
            } else {
                imgIsFavorite.setImageResource(R.drawable.ic_favorite);
                this.product.setFavorite(true);
                viewModel.insert(this.product);
            }
        } else if (!isSettingFavorite && product != null && product.isFavorite()) {
            this.product = product;
            binding.setProduct(product);
            progressBar.setVisibility(View.INVISIBLE);
        } else {
            binding.setProduct(this.product);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
