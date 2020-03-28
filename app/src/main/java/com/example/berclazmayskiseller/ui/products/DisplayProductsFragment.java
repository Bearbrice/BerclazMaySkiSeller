package com.example.berclazmayskiseller.ui.products;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
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
import com.example.berclazmayskiseller.db.entity.ProductEntity;
import com.example.berclazmayskiseller.db.util.RecyclerViewItemClickListener;
import com.example.berclazmayskiseller.viewmodel.ProductListViewModel;

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

        View view = inflater.inflate(R.layout.fragment_temp_product_list, container, false);

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

                addFragment(new DetailsProductFragment(), getActivity(), R.id.container_products, false, "one");


//                Intent intent = new Intent(MainActivity.this, ClientDetails.class);
//                intent.setFlags(
//                        Intent.FLAG_ACTIVITY_NO_ANIMATION |
//                                Intent.FLAG_ACTIVITY_NO_HISTORY
//                );
//                intent.putExtra("clientEmail", products.get(position).getEmail());
//                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View v, int position) {
                Log.d(TAG, "longClicked position:" + position);
                Log.d(TAG, "longClicked on: " + products.get(position).toString());
            }
        });

//        FloatingActionButton fab = getActivity().findViewById(R.id.floatingActionButton);
//        fab.setOnClickListener(view -> {
//                    Intent intent = new Intent(MainActivity.this, ClientDetails.class);
//                    intent.setFlags(
//                            Intent.FLAG_ACTIVITY_NO_ANIMATION |
//                                    Intent.FLAG_ACTIVITY_NO_HISTORY
//                    );
//                    startActivity(intent);
//                }
//        );

        ProductListViewModel.Factory factory = new ProductListViewModel.Factory(getActivity().getApplication());
        viewModel = ViewModelProviders.of(this, factory).get(ProductListViewModel.class);
        viewModel.getProducts().observe(this, clientEntities -> {
            if (clientEntities != null) {
                products = clientEntities;
                recyclerAdapter.setData(products);
            }
        });

        recyclerView.setAdapter(recyclerAdapter);

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}
