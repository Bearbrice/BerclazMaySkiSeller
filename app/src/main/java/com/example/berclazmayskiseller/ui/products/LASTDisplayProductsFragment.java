package com.example.berclazmayskiseller.ui.products;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.berclazmayskiseller.R;

import static com.example.berclazmayskiseller.ui.AddFragment.addFragment;

public class LASTDisplayProductsFragment extends Fragment {

    public LASTDisplayProductsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_products_display, container, false);
        ImageButton imageButton_backToSearch = (ImageButton) view.findViewById(R.id.imageButton_backToSearch);
        imageButton_backToSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment(new DisplayProductsFragment(), getActivity(), R.id.container_products, false, "one");
            }
        });

        return view;
    }
}
