package com.example.shopping.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.shopping.R;
import com.example.shopping.adapter.ProductRecyclerViewAdapter;
import com.example.shopping.databinding.ProductCardBinding;
import com.example.shopping.model.Product;
import com.example.shopping.utility.AsyncResponse;
import com.example.shopping.utility.CustomOnClick;
import com.example.shopping.utility.Util;
import com.example.shopping.viewmodel.StoreViewModel;

import java.util.List;
import java.util.Objects;

public class StoreFragment extends Fragment implements ProductRecyclerViewAdapter.OnItemCLickListener, View.OnClickListener, AsyncResponse, CustomOnClick {

    private static final String TAG = "StoreFragment";

    //    private RecyclerView recyclerView;
    private ProductRecyclerViewAdapter recyclerViewAdapter;
    private StoreViewModel viewModel;
    private FragmentActivity fragmentActivity;
    private Context context;
    private View productCard;
    Product product;

    ImageView imgIsFavorite;
    private LinearLayout llProducts;

//    String[] categoryArray;

    public StoreFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_store, container, false);

        initializeViews(root);

//        setRecyclerView(new ArrayList<>());
        return root;
    }

    private void init() {
        Log.d(TAG, "init: ");

        fragmentActivity = this.requireActivity();
        context = getContext();

//        recyclerView = root.findViewById(R.id.rvProduct);
        viewModel = new ViewModelProvider(this).get(StoreViewModel.class);

        viewModel.getProductsApi();
    }

    private void initializeViews(View root) {
        llProducts = root.findViewById(R.id.llProducts);
        Button btn = root.findViewById(R.id.btn);
        btn.setOnClickListener(this);

        viewModel.getProducts().observe(getViewLifecycleOwner(), products -> {
//            recyclerViewAdapter.notifyDataSetChanged();
//            setRecyclerView(products);
            setProductGrid(products);

        });
    }

    private void setProductGrid(List<Product> products) {
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
            productCard.findViewById(R.id.imgIsFavorite).setVisibility(View.INVISIBLE);
            productCard.setLayoutParams(rootLayoutParams);
            ll.addView(productCard);

            //adding 2nd product in a row in ll
            product = products.get(productCounter + 1);
            binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.product_card, null, false);
            binding.setProduct(product);
            binding.setCustomClick(this);
            rootLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            rootLayoutParams.setMargins(10, 0, 0, 0);
            productCard = binding.getRoot();
            productCard.findViewById(R.id.imgIsFavorite).setVisibility(View.INVISIBLE);
            productCard.setLayoutParams(rootLayoutParams);
            ll.addView(productCard);

            //adding ll(1 row) in vertical linearLayout
            llProducts.addView(ll);
        }
    }

    @Override
    public void setCustomOnClickListener(View view, final Product product) {
        int id = view.getId();

        if (id == productCard.getId()) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("product", product);

            NavController navController = Navigation.findNavController(fragmentActivity, R.id.nav_host_fragment);
            navController.navigate(R.id.nav_product_detail, bundle);
        } else if (id == view.findViewById(R.id.imgIsFavorite).getId()) {

            imgIsFavorite = view.findViewById(R.id.imgIsFavorite);
            this.product = product;
            Log.d(TAG, "setCustomOnClick: imgIsFavorite Id: " + id + " Product: " + product.toString());

            viewModel.get(product.getId(), this);
        }
    }

    //returning the object from get(room) method
    @Override
    public void onAsyncProcessFinish(Object output) {
        Log.d(TAG, "onProcessFinish: ");

        Product product = (Product) output;

        if (product == null) {
            this.product.setFavorite(true);
            imgIsFavorite.setImageResource(R.drawable.ic_favorite);
            viewModel.insert(this.product);
        } else {
            if (product.isFavorite()) {
                imgIsFavorite.setImageResource(R.drawable.ic_not_favorite);
            } else {
                imgIsFavorite.setImageResource(R.drawable.ic_favorite);
            }
            //run insert/update db code
            product.setFavorite(!product.isFavorite());
            viewModel.insert(product);
        }
    }

    //recycler view item click
    //not in use
    @Override
    public void onProductClick(int position) {
        Log.d(TAG, "onItemClick: ");

        Log.d(TAG, "onItemClick: ");
        Product product = Objects.requireNonNull(viewModel.getProducts().getValue()).get(position);

        Bundle bundle = new Bundle();
        bundle.putSerializable("product", product);

        NavController navController = Navigation.findNavController(fragmentActivity, R.id.nav_host_fragment);
        navController.navigate(R.id.nav_product_detail, bundle);
    }

    //normal onClick
    //not in use
    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btn) {
            viewModel.getProductsByCategoryApi("jewelery");
        } else if (id == productCard.getId()) {
            v.getTag();
            Bundle bundle = new Bundle();
            bundle.putSerializable("product", product);

            NavController navController = Navigation.findNavController(fragmentActivity, R.id.nav_host_fragment);
            navController.navigate(R.id.nav_product_detail, bundle);
        }
    }

    /*private void setRecyclerView(List<Product> products) {
        Log.d(TAG, "setRecyclerView: ");

        recyclerViewAdapter = new ProductRecyclerViewAdapter(context, products, this);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }*/

    /*private void setSpnCategory() {
        String[] categoryArray = context.getResources().getStringArray(R.array.categoryArray);

        Spinner spnCategory = root.findViewById(R.id.spnCategory);
        ArrayAdapter<String> categoryArrayAdapter = new ArrayAdapter<>(fragmentActivity, android.R.layout.simple_spinner_item, categoryArray);
        categoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCategory.setOnItemSelectedListener(spinnerAdapterListener);
        spnCategory.setAdapter(categoryArrayAdapter);
    }*/

//    AdapterView.OnItemSelectedListener spinnerAdapterListener = new AdapterView.OnItemSelectedListener() {
//        @Override
//        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//            int viewId = parent.getId();
//
//            if (viewId == R.id.spnCategory) {
//                Util.showSnackBar(fragmentActivity, "spinner");
//            }
//        }
//
//        @Override
//        public void onNothingSelected(AdapterView<?> parent) {
//
//        }
//    };
}