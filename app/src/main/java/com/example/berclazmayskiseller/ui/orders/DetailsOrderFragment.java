package com.example.berclazmayskiseller.ui.orders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.berclazmayskiseller.R;
import com.example.berclazmayskiseller.db.entity.OrderEntity;
import com.example.berclazmayskiseller.db.entity.ProductEntity;
import com.example.berclazmayskiseller.db.repository.ProductRepository;
import com.example.berclazmayskiseller.db.util.OnAsyncEventListener;
import com.example.berclazmayskiseller.viewmodel.OrderViewModel;
import com.example.berclazmayskiseller.viewmodel.ProductIdViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.example.berclazmayskiseller.ui.AddFragment.addFragment;

public class DetailsOrderFragment extends Fragment {

    private static final String TAG = "DetailsOrderFragment";

    private static final int CREATE_CLIENT = 0;
    private static final int EDIT_CLIENT = 1;
    private static final int DELETE_CLIENT = 2;

    private Toast statusToast;

    private boolean isEditable;

    private EditText etIdOrder;
    private EditText etOrderDate;
    private EditText etClientEmail;
    private EditText etProductId;
    private EditText etProductInfo;

    private OrderViewModel viewModel;

    private OrderEntity order;

    private ProductIdViewModel productIdViewModel;
    private ProductEntity product;
    private ProductRepository productRepository;

    private Button button_delete;

    private List<OrderEntity> orders = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /* Constructor */
    public DetailsOrderFragment(OrderEntity orderEntity) {
        order = orderEntity;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_orders_details, container, false);
        initiateView(view);

        //Back button
        ImageButton imageButton_backToSearch = (ImageButton) view.findViewById(R.id.imageButton_backToSearch);
        imageButton_backToSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment(new DisplayOrdersFragment(), getActivity(), R.id.container_orders, false, "one");
            }
        });

        /* Initialize button */
        button_delete = view.findViewById(R.id.button_delete_order);

        /* Give an action to the button */
        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle(getString(R.string.order_delete));
                alertDialog.setCancelable(false);
                alertDialog.setMessage(getString(R.string.delete_msg));
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.order_delete), (dialog, which) -> {
                    viewModel.deleteOrder(order, new OnAsyncEventListener() {
                        @Override
                        public void onSuccess() {
                            Log.d(TAG, "deleteClient: success");
//                            getActivity().onBackPressed();
                            addFragment(new DisplayOrdersFragment(), getActivity(), R.id.container_orders, false, "one");
                        }

                        @Override
                        public void onFailure(Exception e) {
                            Log.d(TAG, "deleteClient: failure", e);
                        }
                    });
                });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.action_cancel), (dialog, which) -> alertDialog.dismiss());
                alertDialog.show();
            }
        });

        String idOrder = getActivity().getIntent().getStringExtra("idOrder");

        /* Search in db for the order */
        OrderViewModel.Factory factory = new OrderViewModel.Factory(getActivity().getApplication(), idOrder);
        viewModel = ViewModelProviders.of(this, factory).get(OrderViewModel.class);
        viewModel.getOrder().observe(this, orderEntity -> {
            if (orderEntity != null) {
                order = orderEntity;
                updateContent();
            }
            updateContent();
        });

        /* Search in db for the product */
        ProductIdViewModel.Factory factory2 = new ProductIdViewModel.Factory(getActivity().getApplication(), order.getProduct_id());
        productIdViewModel = ViewModelProviders.of(this, factory2).get(ProductIdViewModel.class);
        productIdViewModel.getProduct().observe(this, productEntity -> {
            if (productEntity != null) {
                product = productEntity;
                updateTV(product.getProductName());
            }
        });

        return view;
    }

    /* Initialize element through view*/
    private void initiateView(View view) {
        isEditable = false;
        etIdOrder = view.findViewById(R.id.id_order);
        etOrderDate = view.findViewById(R.id.order_date);
        etClientEmail = view.findViewById(R.id.client_email);
        etProductId = view.findViewById(R.id.product_id);
        etProductInfo = view.findViewById(R.id.product_infos);

        etIdOrder.setFocusable(false);
        etIdOrder.setEnabled(false);
        etOrderDate.setFocusable(false);
        etOrderDate.setEnabled(false);
        etClientEmail.setFocusable(false);
        etClientEmail.setEnabled(false);
        etProductId.setFocusable(false);
        etProductId.setEnabled(false);
        etProductInfo.setFocusable(false);
        etProductInfo.setEnabled(false);
    }

    private void updateTV(String txt) {
        etProductInfo.setText(txt);
    }

    private void updateContent() {
        if (order != null) {
            etIdOrder.setText(Integer.toString(order.getIdOrder()));
            etOrderDate.setText(order.getOrderDate());
            etClientEmail.setText(order.getClientEmail());
            etProductId.setText(Integer.toString(order.getProduct_id()));
        }
    }


}
