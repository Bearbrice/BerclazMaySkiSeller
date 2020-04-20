package com.example.berclazmayskiseller.ui.products;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.berclazmayskiseller.R;

import static com.example.berclazmayskiseller.ui.AddFragment.addFragment;

public class ProductsFragment extends Fragment {

  public View onCreateView(@NonNull final LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_products, container, false);
    addFragment(new DisplayProductsFragment(), getActivity(), R.id.container_products, false, "one");

    return view;
  }

}
