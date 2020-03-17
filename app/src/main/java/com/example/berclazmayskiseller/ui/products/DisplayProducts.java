package com.example.berclazmayskiseller.ui.products;

import android.os.Bundle;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.berclazmayskiseller.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DisplayProducts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_products);
        //BottomNavigationView navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_products, R.id.navigation_myReservations, R.id.navigation_findUs, R.id.navigation_settings)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navView, navController);

        //Source : https://www.youtube.com/watch?v=TY-2Cx4IW9A
        Bundle bundle = getIntent().getExtras();

        if(bundle!=null){
            if(bundle.getString("some") != null){
                Toast.makeText(getApplicationContext(), "data:" + bundle.getString("some"), Toast.LENGTH_SHORT);
            }
        }


    }
}
