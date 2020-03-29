package com.example.berclazmayskiseller.ui.orders;

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

public class OrdersFragment extends Fragment {

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_orders, container, false);
//        addFragment(new SearchProductFragment(), false, "one");
        addFragment(new DisplayOrdersFragment(), getActivity(), R.id.container_orders, false, "one");

        return view;
    }

}
