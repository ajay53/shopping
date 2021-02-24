package com.example.shopping.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.shopping.R;
import com.example.shopping.databinding.FragmentProductDetailBinding;
import com.example.shopping.model.Product;
import com.example.shopping.utility.AsyncResponse;
import com.example.shopping.utility.CustomOnClick;
import com.example.shopping.utility.Util;
import com.example.shopping.viewmodel.ProductDetailViewModel;

public class ProductDetailFragment extends Fragment implements View.OnClickListener, CustomOnClick, AsyncResponse {
    private static final String TAG = "ProductDetailFragment";

    private Context context = null;
    private FragmentActivity fragmentActivity;
    private static Product product = null;
    private ProductDetailViewModel viewModel;
    private ImageView imgIsFavorite;
    private boolean isSettingFavorite;
    private boolean isAddingInCart;
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

        fragmentActivity = requireActivity();
        context = requireContext();
        viewModel = new ViewModelProvider(this).get(ProductDetailViewModel.class);

        assert getArguments() != null;
        product = (Product) getArguments().getSerializable("product");
    }

    private void initializeViews(FragmentProductDetailBinding binding) {
        imgIsFavorite = binding.getRoot().findViewById(R.id.imgIsFavorite);
        progressBar = binding.getRoot().findViewById(R.id.progressBar);
        Button btnAddToCart = binding.getRoot().findViewById(R.id.btnAddToCart);
        btnAddToCart.setOnClickListener(this);

        binding.setCustomClick(this);
        viewModel.get(product.getId(), this);
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: ");

        int id = v.getId();

        if (id == R.id.btnAddToCart) {
            //check for already inCart item
            isAddingInCart = true;
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
            ProductDetailFragment.product = product;
            Log.d(TAG, "setCustomOnClick: imgIsFavorite Id: " + id + " Product: " + product.toString());

            viewModel.get(product.getId(), this);
        }
    }

    @Override
    public void onAsyncProcessFinish(Object output) {
        Log.d(TAG, "onProcessFinish: ");

        Product product = (Product) output;

        new Handler(Looper.getMainLooper()).post(() -> {
            if (isSettingFavorite) {        //setting favorite
                if (product != null) {
                    imgIsFavorite.setImageResource(product.isFavorite() ? R.drawable.unlike : R.drawable.like);
                    product.setFavorite(!product.isFavorite());
                    ProductDetailFragment.product = product;
                    viewModel.insert(product);
                } else {
                    imgIsFavorite.setImageResource(R.drawable.like);
                    ProductDetailFragment.product.setFavorite(true);
                    viewModel.insert(ProductDetailFragment.product);
                }
                isSettingFavorite = false;
            } else if (isAddingInCart) {      //setting in cart
                if (product != null) {
                    if (product.isInCart()) {
                        ProductDetailFragment.product = product;
                        Util.showSnackBar(fragmentActivity, getString(R.string.itemAlreadyInCart));
                    } else {
                        product.setInCart(true);
                        ProductDetailFragment.product = product;
                        viewModel.insert(product);
                    }
                } else {
                    ProductDetailFragment.product.setInCart(true);
                    viewModel.insert(ProductDetailFragment.product);
                }
                isAddingInCart = false;
            } else {                        //setting product on page load
                ProductDetailFragment.product = product == null ? ProductDetailFragment.product : product;
                binding.setProduct(ProductDetailFragment.product);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

        /*if (isSettingFavorite) {        //setting favorite
            if (product != null) {
                imgIsFavorite.setImageResource(product.isFavorite() ? R.drawable.unlike : R.drawable.like);
                product.setFavorite(!product.isFavorite());
                this.product = product;
                viewModel.insert(product);
            } else {
                imgIsFavorite.setImageResource(R.drawable.like);
                this.product.setFavorite(true);
                viewModel.insert(this.product);
            }
            isSettingFavorite = false;
        } else if (isAddingInCart) {      //setting in cart
            if (product != null) {
                if (product.isInCart()) {
                    this.product = product;
                    Util.showSnackBar(fragmentActivity, getString(R.string.itemAlreadyInCart));
                } else {
                    product.setInCart(true);
                    this.product = product;
                    viewModel.insert(product);
                }
            } else {
                this.product.setInCart(true);
                viewModel.insert(this.product);
            }
            isAddingInCart = false;
        } else {                        //setting product on page load
            this.product = product == null ? this.product : product;
            binding.setProduct(this.product);
            progressBar.setVisibility(View.INVISIBLE);
        }*/
    }
}
