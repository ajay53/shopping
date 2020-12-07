package com.example.shopping.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopping.R;
import com.example.shopping.adapter.ProductRecyclerViewAdapter;
import com.example.shopping.model.Product;
import com.example.shopping.utility.Util;
import com.example.shopping.viewmodel.CartViewModel;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment implements View.OnClickListener {

    private RecyclerView recyclerView = null;
    private ProductRecyclerViewAdapter recyclerViewAdapter = null;
    private Context context = null;
    private TextView tvTotalPrice = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_cart, container, false);
        init(root);

        setRecyclerView(new ArrayList<>());
        return root;
    }

    private void init(View root) {
        context = getContext();
        recyclerView = root.findViewById(R.id.rvCart);
        tvTotalPrice = root.findViewById(R.id.tvTotalPrice);
        Button btnBuyAll = root.findViewById(R.id.btnBuyAll);
        Button tvRemoveAllFromCart = root.findViewById(R.id.tvRemoveAllFromCart);

        btnBuyAll.setOnClickListener(this);
        tvRemoveAllFromCart.setOnClickListener(this);

        CartViewModel viewModel = new ViewModelProvider(this).get(CartViewModel.class);

        viewModel.getAll().observe(getViewLifecycleOwner(), products -> {
            recyclerViewAdapter.notifyDataSetChanged();
            if (!products.isEmpty()) {
                tvTotalPrice.setText(Util.getTotalFormattedString(viewModel.getTotal(products)));
            } else {
                tvTotalPrice.setText(context.getText(R.string.cartIsEmpty));
            }
            setRecyclerView(products);
        });
    }

    private void setRecyclerView(List<Product> products) {
        recyclerViewAdapter = new ProductRecyclerViewAdapter(context, products, null);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBuyAll:

                // do your code
                break;
            case R.id.tvRemoveAllFromCart:
                // do your code
                break;
            default:
                break;
        }
    }
}
