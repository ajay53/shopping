package com.example.shopping.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopping.R;
import com.example.shopping.adapter.ProductRecyclerViewAdapter;
import com.example.shopping.model.Product;
import com.example.shopping.viewmodel.StoreViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StoreFragment extends Fragment implements ProductRecyclerViewAdapter.OnItemCLickListener {

    private static final String TAG = "StoreFragment";

    private RecyclerView recyclerView;
    private ProductRecyclerViewAdapter recyclerViewAdapter;
    private StoreViewModel viewModel;
    private Context context;

    public StoreFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_store, container, false);

        init(root);

        setRecyclerView(new ArrayList<>());
        return root;
    }

    private void init(View root) {
        Log.d(TAG, "init: ");

        context = getContext();
        recyclerView = root.findViewById(R.id.rvProduct);
        viewModel = new ViewModelProvider(this).get(StoreViewModel.class);

        viewModel.getProducts().observe(getViewLifecycleOwner(), products -> {
            recyclerViewAdapter.notifyDataSetChanged();
            setRecyclerView(products);
        });

        viewModel.getProductsApi();
    }

    private void setRecyclerView(List<Product> products) {
        Log.d(TAG, "setRecyclerView: ");

        recyclerViewAdapter = new ProductRecyclerViewAdapter(context, products, this);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public void onItemClick(int position) {
        Log.d(TAG, "onItemClick: ");

        Log.d(TAG, "onItemClick: ");
        Product product = Objects.requireNonNull(viewModel.getProducts().getValue()).get(position);

        Bundle bundle = new Bundle();
//        bundle.putParcelable("product", product);
        bundle.putSerializable("product", product);

        NavController navController = Navigation.findNavController(this.requireActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.nav_product_detail, bundle);
    }
}