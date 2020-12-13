package com.example.shopping.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.shopping.R;
import com.example.shopping.databinding.ProductCardBinding;
import com.example.shopping.model.Product;
import com.example.shopping.utility.CustomOnClick;
import com.example.shopping.viewmodel.FavoritesViewModel;

import java.util.List;

public class FavoritesFragment extends Fragment implements CustomOnClick {
    private static final String TAG = "FavoritesFragment";

    private FavoritesViewModel viewModel;
    private FragmentActivity fragmentActivity;
    private Context context;
    private View productCard;
    Product product;
    ProgressBar progressBar;

    ImageView imgIsFavorite;
    private LinearLayout llProducts;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_favorites, container, false);
        initializeViews(root);
        return root;
    }

    private void init() {
        Log.d(TAG, "init: ");

        fragmentActivity = this.requireActivity();
        context = getContext();
        viewModel = new ViewModelProvider(this).get(FavoritesViewModel.class);
    }

    private void initializeViews(View root) {
        llProducts = root.findViewById(R.id.llProducts);
        progressBar = root.findViewById(R.id.progressBar);

        viewModel.getFavorites().observe(getViewLifecycleOwner(), products -> {
            llProducts.removeAllViews();
            displayProducts(products);
            progressBar.setVisibility(View.INVISIBLE);
        });
    }

    private void displayProducts(List<Product> products) {
        LinearLayout ll;

        ProductCardBinding binding;
        LinearLayout.LayoutParams rootLayoutParams;
        LinearLayout.LayoutParams llLayoutParams;

        for (int productCounter = 0; productCounter < products.size(); productCounter += 2) {

            //creating linearLayout for each row(2 products)
            ll = new LinearLayout(context);
            ll.setOrientation(LinearLayout.HORIZONTAL);
            llLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 2);
            llLayoutParams.setMargins(0, 0, 0, 20);
            ll.setLayoutParams(llLayoutParams);

            //adding 1st product in a row in ll
            product = products.get(productCounter);
            binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.product_card, null, false);
            binding.setProduct(product);
            binding.setCustomClick(this);
            rootLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            rootLayoutParams.setMargins(0, 0, 10, 0);
            productCard = binding.getRoot();
            productCard.setLayoutParams(rootLayoutParams);
            ll.addView(productCard);

            Log.d(TAG, "Favorites: setProductGrid: products.size: " + products.size() + " productCounter: " + productCounter);
            //adding 2nd product in a row in ll
            if (productCounter + 1 < products.size()) {
                product = products.get(productCounter + 1);
                binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.product_card, null, false);
                binding.setProduct(product);
                binding.setCustomClick(this);
                rootLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                rootLayoutParams.setMargins(10, 0, 0, 0);
                productCard = binding.getRoot();
                productCard.setLayoutParams(rootLayoutParams);
                ll.addView(productCard);
            }

            //adding ll(1 row) in vertical linearLayout
            llProducts.addView(ll);
        }
    }

    @Override
    public void setCustomOnClickListener(View view, Product product) {
        int id = view.getId();

        if (id == productCard.getId()) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("product", product);

            NavController navController = Navigation.findNavController(fragmentActivity, R.id.nav_host_fragment);
            navController.navigate(R.id.nav_product_detail, bundle);
        } else if (id == view.findViewById(R.id.imgIsFavorite).getId()) {
//            imgIsFavorite = view.findViewById(R.id.imgIsFavorite);
            product.setFavorite(false);
            viewModel.insert(product);

//            imgIsFavorite.setImageResource(R.drawable.ic_not_favorite);
            Log.d(TAG, "setCustomOnClick: imgIsFavorite Id: " + id + " Product: " + product.toString());
        }
    }
}
