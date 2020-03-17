package com.example.berclazmayskiseller.ui.products;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.example.berclazmayskiseller.R;

public class ProductsFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //ORIGINAL DEV
//        View myView = inflater.inflate(R.layout.fragment_products, container, false);
//        myButton = (Button) myView.findViewById(R.id.button_displayProducts);
//        myButton.setOnClickListener(this);
        //return myView;

        //dev BBE //Source : https://www.youtube.com/watch?v=TY-2Cx4IW9A
        View view = inflater.inflate(R.layout.fragment_products, container, false);

        Button button_displayProducts = (Button) view.findViewById(R.id.button_displayProducts);
        button_displayProducts.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent in = new Intent(getActivity(), DisplayProducts.class);
                in.putExtra("some", "some data");
                startActivity(in);
            }
        });

        return view;
        //end dev bbe
    }

    //ORIGINAL
//    @Override
//    public void onClick(View v) {
//        HomeFragment homeFragment = new HomeFragment();
//        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.navigation_home, homeFragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
//    }
}
