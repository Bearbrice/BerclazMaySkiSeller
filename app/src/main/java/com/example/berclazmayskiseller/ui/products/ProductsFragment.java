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

import static com.example.berclazmayskiseller.ui.AddFragment.addFragment;

public class ProductsFragment extends Fragment {

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_products, container, false);
        addFragment(new DisplayProductsFragment(), getActivity(), R.id.container_products, false, "one");

        return view;
    }

}
