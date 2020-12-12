package com.example.shopping.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopping.R;
import com.example.shopping.adapter.ProductRecyclerViewAdapter;
import com.example.shopping.model.Product;
import com.example.shopping.utility.Util;
import com.example.shopping.viewmodel.CartViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CartFragment extends Fragment implements ProductRecyclerViewAdapter.OnItemCLickListener, View.OnClickListener {
    private static final String TAG = "CartFragment";

    CartViewModel viewModel = null;
    private RecyclerView recyclerView = null;
    private ProductRecyclerViewAdapter recyclerViewAdapter = null;
    private FragmentActivity fragmentActivity;
    private Context context = null;
    private TextView tvTotalPrice = null;
    private ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_cart, container, false);

        initializeViews(root);
        setRecyclerView(new ArrayList<>());
        return root;
    }

    private void init() {
        Log.d(TAG, "init: ");

        context = getContext();
        fragmentActivity = this.requireActivity();
        viewModel = new ViewModelProvider(this).get(CartViewModel.class);
    }

    private void initializeViews(View root) {
        Log.d(TAG, "initializeViews: ");

        recyclerView = root.findViewById(R.id.rvCart);
        tvTotalPrice = root.findViewById(R.id.tvTotalPrice);
        progressBar = root.findViewById(R.id.progressBar);
        Button btnBuyAll = root.findViewById(R.id.btnBuyAll);
        Button tvRemoveAllFromCart = root.findViewById(R.id.tvRemoveAllFromCart);

        btnBuyAll.setOnClickListener(this);
        tvRemoveAllFromCart.setOnClickListener(this);

        viewModel.getAllInCart().observe(getViewLifecycleOwner(), products -> {
            recyclerViewAdapter.notifyDataSetChanged();
            if (!products.isEmpty()) {
                tvTotalPrice.setText(Util.getTotalFormattedString(viewModel.getTotal(products)));
            } else {
                tvTotalPrice.setText(context.getText(R.string.cartIsEmpty));
            }
            setRecyclerView(products);
            progressBar.setVisibility(View.INVISIBLE);
        });
    }

    private void setRecyclerView(List<Product> products) {
        Log.d(TAG, "setRecyclerView: ");

        recyclerViewAdapter = new ProductRecyclerViewAdapter(context, products, this);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: ");

        int id = v.getId();

        if (id == R.id.btnBuyAll) {
            viewModel.deleteAll();
        } else if (id == R.id.tvRemoveAllFromCart) {
            viewModel.deleteAll();
        }
    }

    @Override
    public void onProductClick(int position) {
        Log.d(TAG, "onItemClick: ");

        Product product = Objects.requireNonNull(viewModel.getAllInCart().getValue()).get(position);

        Bundle bundle = new Bundle();
        bundle.putSerializable("product", product);

        NavController navController = Navigation.findNavController(fragmentActivity, R.id.nav_host_fragment);
        navController.navigate(R.id.nav_product_detail, bundle);
    }
}
