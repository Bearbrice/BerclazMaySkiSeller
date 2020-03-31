package com.example.berclazmayskiseller.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.berclazmayskiseller.R;

import static com.example.berclazmayskiseller.ui.AddFragment.addFragment;

public class HomeFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //Set the buttons
        Button button_aurelien = view.findViewById(R.id.button_aurelien);
        Button button_us = view.findViewById(R.id.button_us);
        Button button_brice = view.findViewById(R.id.button_brice);

        //Set the buttons colors
//        button_aurelien.setBackgroundColor(65535);
//        button_us.setBackgroundColor(Color.WHITE);
//        button_brice.setBackgroundColor(Color.WHITE);

        //Set the button and fragment opened once connected
        button_us.setEnabled(false);
        addFragment(new UsFragment(), getActivity(), R.id.container_creators, false, "one");

        //Button listeners
        button_aurelien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_aurelien.setEnabled(false);
                button_brice.setEnabled(true);
                button_us.setEnabled(true);
                addFragment(new AurelienFragment(), getActivity(), R.id.container_creators, false, "one");
            }
        });

        //Button listener
        button_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_us.setEnabled(false);
                button_aurelien.setEnabled(true);
                button_brice.setEnabled(true);
                addFragment(new UsFragment(), getActivity(), R.id.container_creators, false, "one");
            }
        });

        //Button listener
        button_brice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_brice.setEnabled(false);
                button_us.setEnabled(true);
                button_aurelien.setEnabled(true);
                addFragment(new BriceFragment(), getActivity(), R.id.container_creators, false, "one");
            }
        });

        return view;
    }
}
