package com.example.berclazmayskiseller.ui.products;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.example.berclazmayskiseller.R;
import com.example.berclazmayskiseller.ui.home.HomeFragment;

public class ProductsFragment extends Fragment implements View.OnClickListener {

//    private ProductsViewModel productsViewModel;
//
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        productsViewModel =
//                ViewModelProviders.of(this).get(ProductsViewModel.class);
//        View root = inflater.inflate(R.layout.fragment_products, container, false);
//        final TextView textView = root.findViewById(R.id.text_products);
//        productsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
//
//        return root;
//    }

    Button myButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_products, container, false);

        View myView = inflater.inflate(R.layout.fragment_products, container, false);
        myButton = (Button) myView.findViewById(R.id.button_displayProducts);
        myButton.setOnClickListener(this);
        return myView;
    }

//    @Override
//    public void onClick(View v) {
//        Toast.makeText(getActivity(),"Products displayed !",Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(getActivity(), DisplayProductsActivity.class);
//        startActivity(intent);
//    }

    @Override
    public void onClick(View v) {
        HomeFragment fragment2 = new HomeFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment2);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
