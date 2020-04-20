package com.example.berclazmayskiseller.ui.products;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.berclazmayskiseller.R;
import com.example.berclazmayskiseller.adapter.RecyclerAdapter;
import com.example.berclazmayskiseller.database.entity.ProductEntity;
import com.example.berclazmayskiseller.util.RecyclerViewItemClickListener;
import com.example.berclazmayskiseller.viewmodel.ProductListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static com.example.berclazmayskiseller.ui.AddFragment.addFragment;

public class DisplayProductsFragment extends Fragment {

    private static final String TAG = "DisplayProductsFragment";

    private List<ProductEntity> products;
    private RecyclerAdapter recyclerAdapter;
    private ProductListViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_products_list, container, false);

        // 1. get a reference to recyclerView
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.clientsRecyclerView);
        // 2. set layoutManger
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Divider between the products
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        products = new ArrayList<>();
        recyclerAdapter = new RecyclerAdapter(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Log.d(TAG, "clicked position:" + position);
                Log.d(TAG, "clicked on: " + products.get(position).toString());

                addFragment(new DetailsProductFragment(products.get(position)), getActivity(), R.id.container_products, false, "one");
            }

            @Override
            public void onItemLongClick(View v, int position) {
                Log.d(TAG, "longClicked position:" + position);
                Log.d(TAG, "longClicked on: " + products.get(position).toString());
            }
        });

        /* Programming the floating button to add a new product */
        FloatingActionButton fab = view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       addFragment(new DetailsProductFragment(null), getActivity(), R.id.container_products, false, "one");
                                   }
                               }
        );

        /* Search for a product in the DB */
        ProductListViewModel.Factory factory = new ProductListViewModel.Factory(getActivity().getApplication());
        viewModel = ViewModelProviders.of(this, factory).get(ProductListViewModel.class);
        viewModel.getProducts().observe(this, productEntities -> {
            if (productEntities != null) {
                products = productEntities;
                recyclerAdapter.setData(products);
            }
        });

        recyclerView.setAdapter(recyclerAdapter);

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
