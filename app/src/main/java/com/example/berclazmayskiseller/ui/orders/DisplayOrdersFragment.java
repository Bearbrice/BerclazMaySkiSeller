package com.example.berclazmayskiseller.ui.orders;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.berclazmayskiseller.R;
import com.example.berclazmayskiseller.adapter.RecyclerAdapter;
import com.example.berclazmayskiseller.database.entity.OrderEntity;
import com.example.berclazmayskiseller.util.RecyclerViewItemClickListener;
import com.example.berclazmayskiseller.viewmodel.OrderListViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.example.berclazmayskiseller.ui.AddFragment.addFragment;

public class DisplayOrdersFragment extends Fragment {

    private static final String TAG = "DisplayOrdersFragment";

    private List<OrderEntity> orders;
    private RecyclerAdapter recyclerAdapter;
    private OrderListViewModel viewModel;

    private Toast statusToast;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_orders_list, container, false);

        // 1. get a reference to recyclerView
        RecyclerView recyclerView = view.findViewById(R.id.ordersRecyclerView);
        // 2. set layoutManger
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Divider between the orders
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        orders = new ArrayList<>();
        recyclerAdapter = new RecyclerAdapter(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Log.d(TAG, "clicked position:" + position);
                Log.d(TAG, "clicked on: " + orders.get(position).toString());

                addFragment(new DetailsOrderFragment(orders.get(position)), getActivity(), R.id.container_orders, false, "one");
            }

            @Override
            public void onItemLongClick(View v, int position) {
                Log.d(TAG, "longClicked position:" + position);
                Log.d(TAG, "longClicked on: " + orders.get(position).toString());
            }
        });

        SharedPreferences sharedPref = getActivity().getSharedPreferences("token", Context.MODE_PRIVATE);
        String user = sharedPref.getString("tokenSaved", "NotFound");

        /* Search in db for the orders */
        OrderListViewModel.Factory factory = new OrderListViewModel.Factory(getActivity().getApplication(), user);
        viewModel = ViewModelProviders.of(this, factory).get(OrderListViewModel.class);
        viewModel.getOrders().observe(this, orderEntities -> {
            if (orderEntities != null) {
                orders = orderEntities;
                //If there is no order a toast appeared to tell the user it has not found something
                if(orders.isEmpty()){
                    statusToast = Toast.makeText(getActivity(), getString(R.string.order_message_empty), Toast.LENGTH_SHORT);
                    statusToast.setGravity(Gravity.CENTER, 0, 0);
                    statusToast.show();
                }
                recyclerAdapter.setData(orders);
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
