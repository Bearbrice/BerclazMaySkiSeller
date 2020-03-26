package com.example.berclazmayskiseller.ui;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class AddFragment {

    public static void addFragment(Fragment fragment, FragmentActivity activity, int container_productsSearch, boolean addToBackStack, String tag) {
        FragmentManager manager = activity.getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();

        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.replace(container_productsSearch, fragment, tag);
        ft.commitAllowingStateLoss();
    }
}
