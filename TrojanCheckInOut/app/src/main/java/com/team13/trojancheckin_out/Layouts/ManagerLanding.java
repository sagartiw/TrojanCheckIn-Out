package com.team13.trojancheckin_out.Layouts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.team13.trojancheckin_out.R;


import java.util.ArrayList;
import java.util.List;

public class ManagerLanding extends AppCompatActivity {

    //a list to store all the products
    List<Product> productList;

    //the recyclerview
    RecyclerView recyclerView;

    private Button Search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_landing);

        Search = (Button)findViewById(R.id.button5);

        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManagerLanding.this, SearchStudent2.class);
                startActivity(intent);
            }
        });

        //getting the recyclerview from xml
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the productlist
        productList = new ArrayList<>();


        //adding some items to our list
        productList.add(
                new Product(
                        1,
                        "SAL",
                        14,
                        30
                ));

        productList.add(
                new Product(
                        1,
                        "RTH",
                        47,
                        150
                ));

        productList.add(
                new Product(
                        1,
                        "JFF",
                        432,
                        500
                ));

        productList.add(
                new Product(
                        1,
                        "WPH",
                        69,
                        420
                ));

        //creating recyclerview adapter
        ProductAdapter adapter = new ProductAdapter(this, productList);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final PopupMenu menu = new PopupMenu(this, fab);
        menu.getMenu().add("Student View");
        menu.getMenu().add("Sign Out");
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                // insert your code here
                Log.d("menu title: ", item.getTitle().toString());
                if(item.getTitle().toString().equals("Student View")){
                    Intent intent = new Intent(ManagerLanding.this, StudentLanding.class);
                    startActivity(intent);
                }
                if(item.getTitle().toString().equals("Sign Out")){
                    Intent intent = new Intent(ManagerLanding.this, Startup.class);
                    startActivity(intent);
                }
                return true; }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu.show();
            }
        });
    }
}