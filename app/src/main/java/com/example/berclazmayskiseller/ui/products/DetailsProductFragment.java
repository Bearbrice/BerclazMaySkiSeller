package com.example.berclazmayskiseller.ui.products;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;


import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.berclazmayskiseller.db.util.OnAsyncEventListener;
import com.example.berclazmayskiseller.viewmodel.OrderViewModel;
import com.example.berclazmayskiseller.viewmodel.ProductViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.berclazmayskiseller.ui.AddFragment.addFragment;

public class DetailsProductFragment extends Fragment {

    private static final String TAG = "DetailsProductFragment";

    private static final int CREATE_CLIENT = 0;
    private static final int EDIT_CLIENT = 1;
    private static final int DELETE_CLIENT = 2;

    private Toast statusToast;

    private boolean isEditable;

    private EditText etProductName;
    private EditText etColor;
    private EditText etPrice;

    private ProductViewModel viewModel;
    private OrderViewModel orderViewModel;

    private ProductEntity product;
    private OrderEntity order;

    private Button button_add;
    private Button button_edit;
    private Button button_delete;
    //private Button button_edit_save;
    private Button button_place_order;

    private List<ProductEntity> products = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /* Constructor */
    public DetailsProductFragment(ProductEntity productEntity) {
        product = productEntity;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_product_details, container, false);
        initiateView(view);

        //Back button
        ImageButton imageButton_backToSearch = (ImageButton) view.findViewById(R.id.imageButton_backToSearch);
        imageButton_backToSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment(new DisplayProductsFragment(), getActivity(), R.id.container_products, false, "one");
            }
        });

        /* Initialize buttons */
        button_add = view.findViewById(R.id.button_add);
        button_edit = view.findViewById(R.id.button_edit);
        button_delete = view.findViewById(R.id.button_delete);
        button_place_order = view.findViewById(R.id.button_place_order);

        /* Give action to add button */
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createProduct(
                        etProductName.getText().toString(),
                        etColor.getText().toString(),
                        Double.parseDouble(etPrice.getText().toString())
                );
            }
        });

        /* Give action to edit button */
        button_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEditable) {
                    changeButtonEditableMode(true);
                    switchEditableMode();
                } else {
                    switchEditableMode();
                    changeButtonEditableMode(false);
                    button_edit.setText("Save changes");
                }
            }
        });

        /* Give action to delete button */
        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle(getString(R.string.product_delete));
                alertDialog.setCancelable(false);
                alertDialog.setMessage(getString(R.string.delete_msg));
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.product_delete), (dialog, which) -> {
                    viewModel.deleteProduct(product, new OnAsyncEventListener() {
                        @Override
                        public void onSuccess() {
                            Log.d(TAG, "deleteClient: success");
//                            getActivity().onBackPressed();
                            addFragment(new DisplayProductsFragment(), getActivity(), R.id.container_products, false, "one");
                        }

                        @Override
                        public void onFailure(Exception e) {
                            Log.d(TAG, "deleteClient: failure", e);
                            AlertDialog fail = new AlertDialog.Builder(getActivity()).create();
                            fail.setTitle(getString(R.string.product_error_delete));
                            fail.setCancelable(false);
                            fail.setMessage(getString(R.string.product_msg_error_delete));
                            fail.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.action_cancel), (dialog, which) -> alertDialog.dismiss());
                            fail.show();
                        }
                    });
                });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.action_cancel), (dialog, which) -> alertDialog.dismiss());
                alertDialog.show();
            }
        });

        /* Give action to place order button */
        button_place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createOrder();
            }
        });

        String productName = getActivity().getIntent().getStringExtra("productName");

        /* Search for the product in DB */
        ProductViewModel.Factory factory = new ProductViewModel.Factory(getActivity().getApplication(), productName);
        viewModel = ViewModelProviders.of(this, factory).get(ProductViewModel.class);
        viewModel.getProduct().observe(this, productEntity -> {
            if (productEntity != null) {
                product = productEntity;
                updateContent();
            }
            updateContent();
        });

        if (product != null) {
            getActivity().setTitle(R.string.title_fragment_details);
            changeButtonVisibility(false);
        } else {
            getActivity().setTitle(R.string.title_fragment_create);
            switchEditableMode();
            changeButtonVisibility(true);
        }

        return view;
    }

    /* Hide/show buttons */
    private void changeButtonVisibility(boolean visibility) {
        if (visibility) {
            button_add.setVisibility(View.VISIBLE);
            button_edit.setVisibility(View.GONE);
            button_delete.setVisibility(View.GONE);
            button_place_order.setVisibility(View.GONE);
        } else {
            button_add.setVisibility(View.GONE);
            button_edit.setVisibility(View.VISIBLE);
            button_delete.setVisibility(View.VISIBLE);
            button_place_order.setVisibility(View.VISIBLE);
        }
    }

    private void changeButtonEditableMode(boolean visibility) {
        if (visibility) {
            button_edit.setVisibility(View.GONE);
            button_delete.setVisibility(View.GONE);
        } else {
            button_edit.setVisibility(View.VISIBLE);
            button_delete.setVisibility(View.VISIBLE);
        }
    }

    /* Initialize elements through view */
    private void initiateView(View view) {
        isEditable = false;
        etProductName = view.findViewById(R.id.productName);
        etColor = view.findViewById(R.id.color);
        etPrice = view.findViewById(R.id.price);

        etProductName.setFocusable(false);
        etProductName.setEnabled(false);
        etColor.setFocusable(false);
        etColor.setEnabled(false);
        etPrice.setFocusable(false);
        etPrice.setEnabled(false);
    }

    private void switchEditableMode() {
        if (!isEditable) {
            etProductName.setFocusable(true);
            etProductName.setEnabled(true);
            etProductName.setFocusableInTouchMode(true);

            etColor.setFocusable(true);
            etColor.setEnabled(true);
            etColor.setFocusableInTouchMode(true);

            etPrice.setFocusable(true);
            etPrice.setEnabled(true);
            etPrice.setFocusableInTouchMode(true);
            etProductName.requestFocus();
        } else {
            saveChanges(
                    etProductName.getText().toString(),
                    etColor.getText().toString(),
                    Double.parseDouble(etPrice.getText().toString())
            );
            etProductName.setFocusable(false);
            etProductName.setEnabled(false);
            etColor.setFocusable(false);
            etColor.setEnabled(false);
            etPrice.setFocusable(false);
            etPrice.setEnabled(false);
        }
        isEditable = !isEditable;
    }

    /* Method to create a product */
    private void createProduct(String productName, String color, double price) {
        product = new ProductEntity();
        product.setPrice(price);
        product.setProductName(productName);
        product.setColor(color);

        viewModel.createProduct(product, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "createProduct: success");
                addFragment(new DisplayProductsFragment(), getActivity(), R.id.container_products, false, "one");
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "createProduct: failure", e);
            }
        });
    }

    /* Method to create an order from a product */
    private void createOrder() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String dateOrder = formatter.format(date);

        SharedPreferences sharedPref = getActivity().getSharedPreferences("email", Context.MODE_PRIVATE);
        String email = sharedPref.getString("emailSaved", "NotFound");

        order = new OrderEntity();
        order.setOrderDate(dateOrder);
        order.setProduct_id(product.getIdProduct());
        order.setClientEmail(email);

        OrderViewModel.Factory factory = new OrderViewModel.Factory(getActivity().getApplication(), email);
        orderViewModel = ViewModelProviders.of(this, factory).get(OrderViewModel.class);

        orderViewModel.createOrder(order, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "createOrder: success");
                statusToast = Toast.makeText(getActivity(), getString(R.string.order_created), Toast.LENGTH_LONG);
                statusToast.show();
                addFragment(new DisplayProductsFragment(), getActivity(), R.id.container_products, false, "one");
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "createProduct: failure", e);
            }
        });

    }

    /* Save modifications of a product */
    private void saveChanges(String productName, String color, double price) {
        product.setPrice(price);
        product.setProductName(productName);
        product.setColor(color);

        viewModel.updateProduct(product, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "updateProduct: success");
                setResponse(true);
                addFragment(new DisplayProductsFragment(), getActivity(), R.id.container_products, false, "one");
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "updateProduct: failure", e);
                setResponse(false);
            }
        });
    }

    private void setResponse(Boolean response) {
        if (response) {
            statusToast = Toast.makeText(getActivity(), getString(R.string.product_edited), Toast.LENGTH_LONG);
            statusToast.show();
        } else {
            statusToast = Toast.makeText(getActivity(), getString(R.string.action_error), Toast.LENGTH_LONG);
            statusToast.show();
        }
    }

    private void updateContent() {
        if (product != null) {
            etProductName.setText(product.getProductName());
            etColor.setText(product.getColor());
            etPrice.setText(new Double(product.getPrice()).toString());
        }
    }


}
