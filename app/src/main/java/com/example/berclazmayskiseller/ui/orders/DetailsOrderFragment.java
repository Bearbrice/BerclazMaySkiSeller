package com.example.berclazmayskiseller.ui.orders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.berclazmayskiseller.R;
import com.example.berclazmayskiseller.adapter.RecyclerAdapter;
import com.example.berclazmayskiseller.db.entity.OrderEntity;
import com.example.berclazmayskiseller.db.util.OnAsyncEventListener;
import com.example.berclazmayskiseller.db.util.RecyclerViewItemClickListener;
import com.example.berclazmayskiseller.ui.products.DisplayProductsFragment;
import com.example.berclazmayskiseller.viewmodel.OrderListViewModel;
import com.example.berclazmayskiseller.viewmodel.OrderViewModel;

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

    private OrderViewModel viewModel;

    private OrderEntity order;

    private Button button_delete;

    /* *****************************
     * METHODS OF THE CLASS
     * *************************** */
    private List<OrderEntity> orders = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public DetailsOrderFragment(OrderEntity orderEntity) {
        order = orderEntity;
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

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_order_details, container, false);
        initiateView(view);

        //Back button
        ImageButton imageButton_backToSearch = (ImageButton) view.findViewById(R.id.imageButton_backToSearch);
        imageButton_backToSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment(new DisplayProductsFragment(), getActivity(), R.id.container_products, false, "one");
            }
        });

        button_delete = view.findViewById(R.id.button_delete_order);

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
                            getActivity().onBackPressed();
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

        OrderViewModel.Factory factory = new OrderViewModel.Factory(getActivity().getApplication(), idOrder);
        viewModel = ViewModelProviders.of(this, factory).get(OrderViewModel.class);
        viewModel.getOrder().observe(this, orderEntity -> {
            if (orderEntity != null) {
                order = orderEntity;
                updateContent();
            }
            updateContent();
        });


            //switchEditableMode();



//        /*Tell the main activity to have settings*/
//        setHasOptionsMenu(true);

        return view;
    }


    private void initiateView(View view) {
        isEditable = false;
        etIdOrder = view.findViewById(R.id.id_order);
        etOrderDate = view.findViewById(R.id.order_date);
        etClientEmail = view.findViewById(R.id.client_email);
        etProductId = view.findViewById(R.id.product_id);

        etIdOrder.setFocusable(false);
        etIdOrder.setEnabled(false);
        etOrderDate.setFocusable(false);
        etOrderDate.setEnabled(false);
        etClientEmail.setFocusable(false);
        etClientEmail.setEnabled(false);
        etProductId.setFocusable(false);
        etProductId.setEnabled(false);
    }

//    private void switchEditableMode() {
//        if (!isEditable) {
//            etIdOrder.setFocusable(true);
//            etIdOrder.setEnabled(true);
//            etIdOrder.setFocusableInTouchMode(true);
//
//            etOrderDate.setFocusable(true);
//            etOrderDate.setEnabled(true);
//            etOrderDate.setFocusableInTouchMode(true);
//
//            etClientEmail.setFocusable(true);
//            etClientEmail.setEnabled(true);
//            etClientEmail.setFocusableInTouchMode(true);
//            etIdOrder.requestFocus();
//        } else {
//            saveChanges(
//                    etIdOrder.getText().toString(),
//                    etOrderDate.getText().toString(),
//                    Double.parseDouble(etClientEmail.getText().toString())
//            );
//            etIdOrder.setFocusable(false);
//            etIdOrder.setEnabled(false);
//            etOrderDate.setFocusable(false);
//            etOrderDate.setEnabled(false);
//            etClientEmail.setFocusable(false);
//            etClientEmail.setEnabled(false);
//        }
//        isEditable = !isEditable;
//    }

//    private void createOrder(String idOrder, String orderDate, double clientEmail) {
////        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(clientEmail).matches()) {
////            etClientEmail.setError(getString(R.string.error_invalid_clientEmail));
////            etClientEmail.requestFocus();
////            return;
////        }
//
//        order = new OrderEntity();
//        order.set
//
//
//        order.setClientEmail(clientEmail);
//        order.setIdOrder(idOrder);
//        order.setOrderDate(orderDate);
//
//        viewModel.createOrder(order, new OnAsyncEventListener() {
//            @Override
//            public void onSuccess() {
//                Log.d(TAG, "createOrder: success");
//                getActivity().onBackPressed();
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//                Log.d(TAG, "createOrder: failure", e);
//            }
//        });
//    }
//
//    private void saveChanges(String idOrder, String orderDate, double clientEmail) {
////        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(clientEmail).matches()) {
////            etClientEmail.setError(getString(R.string.error_invalid_clientEmail));
////            etClientEmail.requestFocus();
////            return;
////        }
//
//        order.setClientEmail(clientEmail);
//        order.setIdOrder(idOrder);
//        order.setOrderDate(orderDate);
//
//        viewModel.updateOrder(order, new OnAsyncEventListener() {
//            @Override
//            public void onSuccess() {
//                Log.d(TAG, "updateOrder: success");
//                setResponse(true);
//                getActivity().onBackPressed();
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//                Log.d(TAG, "updateOrder: failure", e);
//                setResponse(false);
//            }
//        });
//    }

//    private void setResponse(Boolean response) {
//        if (response) {
//            statusToast = Toast.makeText(getActivity(), getString(R.string.order_edited), Toast.LENGTH_LONG);
//            statusToast.show();
//        } else {
//            statusToast = Toast.makeText(getActivity(), getString(R.string.action_error), Toast.LENGTH_LONG);
//            statusToast.show();
//        }
//    }

    private void updateContent() {
        if (order != null) {
            etIdOrder.setText(Integer.toString(order.getIdOrder()));
            etOrderDate.setText(order.getOrderDate());
            etClientEmail.setText(order.getClientEmail());
            etProductId.setText(Integer.toString(order.getProduct_id()));
        }
    }


}
