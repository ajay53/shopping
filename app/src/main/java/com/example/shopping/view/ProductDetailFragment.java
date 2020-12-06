package com.example.shopping.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.shopping.R;
import com.example.shopping.databinding.FragmentProductDetailBinding;
import com.example.shopping.model.Product;

public class ProductDetailFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "ProductDetailFragment";

    private Context context = null;
    private FragmentProductDetailBinding binding;
    private Button btnAddToCart = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_detail, container, false);
        init(binding.getRoot());

        Product product = getArguments().getParcelable("product");
        binding.setProduct(product);
        return binding.getRoot();
    }

    private void init(View view) {
        context = requireContext();

        btnAddToCart = view.findViewById(R.id.btnAddToCart);
        btnAddToCart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddToCart:
                Toast.makeText(context, "Add to Cart Clicked", Toast.LENGTH_SHORT).show();
                break;
            default:
                Log.e(TAG, "onClick: didn't specify case");
                break;
        }
    }
}
