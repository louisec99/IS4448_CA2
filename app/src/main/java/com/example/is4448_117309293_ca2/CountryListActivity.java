package com.example.is4448_117309293_ca2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CountryListActivity extends AppCompatActivity {

    //  Context context;
    private static final String STATS_URL = "https://api.covid19api.com/summary";


    private ProgressBar progressBar;
    private EditText etSearch;
    private ImageButton btnSort;
    private RecyclerView rvStats;
    ArrayList<ModelStat> statArrayList;
    AdapterStat adapterStat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_list);

        progressBar = findViewById(R.id.progressBar);
        etSearch = findViewById(R.id.etSearch);
        btnSort = findViewById(R.id.btnSort);
        rvStats = findViewById(R.id.rvStats);

        progressBar.setVisibility(View.GONE);

        loadStatsData();
        //search

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //calles as and when usr=e type or remove letter

                try {
                    adapterStat.getFilter().filter(s);

                } catch (Exception e) {
                    e.printStackTrace();

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //popup menu to show sorting options

        PopupMenu popupMenu = new PopupMenu(CountryListActivity.this, btnSort);
        popupMenu.getMenu().add(Menu.NONE, 0, 0, "Ascending");
        popupMenu.getMenu().add(Menu.NONE, 1, 1, "Descending");
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == 0) {
                    Collections.sort(statArrayList, new SortStatCountryAsc());
                    adapterStat.notifyDataSetChanged();

                } else if (id == 1) {
                    Collections.sort(statArrayList, new SortStatCountryDesc());
                    adapterStat.notifyDataSetChanged();

                }
                return false;
            }
        });

        //sort
        btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popupMenu.show();

            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.navHome:
//                        Intent intent1 = new Intent(Covid19Activity.this, MainActivity.class);
//                        startActivity(intent1);
//                        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
//                        break;
                    case R.id.navHero:
                        Intent intent2 = new Intent(CountryListActivity.this, MainActivity.class);
                        startActivity(intent2);

                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        finish();
                        break;
                    case R.id.navCovid19:
//                        Intent intent3 = new Intent(Covid19Activity.this, Covid19Activity.class);
//                        startActivity(intent3);
//                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                        break;
                }
                return true;
            }

        });


    }

    private void loadStatsData() {
        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, STATS_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                handleResponse(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //failed response
                progressBar.setVisibility(View.GONE);
                Toast.makeText(CountryListActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        //add request to queue
        RequestQueue requestQueue = Volley.newRequestQueue(CountryListActivity.this);
        requestQueue.add(stringRequest);
    }

    private void handleResponse(String response) {
        statArrayList = new ArrayList<>();
        statArrayList.clear();

        try {
            JSONObject jsonObject = new JSONObject(response);

            JSONArray jsonArray = jsonObject.getJSONArray("Countries");

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setDateFormat("dd/MM/yyyy hh:mm a");
            Gson gson = gsonBuilder.create();

            for (int i = 0; i < jsonArray.length(); i++) {
                ModelStat modelStat = gson.fromJson(jsonArray.getJSONObject(i).toString(), ModelStat.class);
                statArrayList.add(modelStat);

            }
            adapterStat = new AdapterStat(CountryListActivity.this, statArrayList);
            rvStats.setAdapter(adapterStat);

            progressBar.setVisibility(View.GONE);


        } catch (Exception e) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(CountryListActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }

    // sort countries as ascending order
    public class SortStatCountryAsc implements Comparator<ModelStat> {

        @Override
        public int compare(ModelStat left, ModelStat right) {
            return left.getCountry().compareTo(right.getCountry());
        }
    }

    //sort countries descending

    public class SortStatCountryDesc implements Comparator<ModelStat> {

        @Override
        public int compare(ModelStat left, ModelStat right) {
            return right.getCountry().compareTo(left.getCountry());
        }
    }


}