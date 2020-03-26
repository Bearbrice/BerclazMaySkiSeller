package com.example.berclazmayskiseller.ui.products;

import android.content.Intent;
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
import com.google.android.gms.maps.SupportMapFragment;

public class ProductsFragment extends Fragment {

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_products, container, false);
//        addFragment(new SearchProductFragment(), false, "one");
        addFragment(new DisplayProductsFragment(), false, "one");

        return view;
    }

    //Method to replace fragment
    public void addFragment(Fragment fragment, boolean addToBackStack, String tag) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();

        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.replace(R.id.container_productsSearch, fragment, tag);
        ft.commitAllowingStateLoss();
    }
}
