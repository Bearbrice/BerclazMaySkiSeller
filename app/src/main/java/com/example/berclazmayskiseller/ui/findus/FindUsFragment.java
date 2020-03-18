package com.example.berclazmayskiseller.ui.findus;

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
import com.example.berclazmayskiseller.ui.findus.map.AnzereFragment;
import com.example.berclazmayskiseller.ui.findus.map.VerbierFragment;

public class FindUsFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_findus, container, false);

        Button button_verbier = (Button) view.findViewById(R.id.button_verbier);
        Button button_anzere = (Button) view.findViewById(R.id.button_anzere);

        button_verbier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment(new VerbierFragment(), false, "one");
            }
        });

        button_anzere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment(new AnzereFragment(), false, "one");
            }
        });

        return view;
    }

    public void addFragment(Fragment fragment, boolean addToBackStack, String tag) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();

        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.replace(R.id.container_frame_back, fragment, tag);
        ft.commitAllowingStateLoss();
    }
}
