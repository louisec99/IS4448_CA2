package com.example.is4448_117309293_ca2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Covid19Activity extends AppCompatActivity {
    //
//    TextView responseView;
//    ProgressBar progressBar;
    static final String API_URL = "https://api.covid19api.com/live/country/ireland";

    // private static final String STATS_URL = "https://api.covid19api.com/summary";

    private TextView tvCountryName, tvConfirmedStat, tvDeathsStat, tvRecoveredStat, tvActiveStat, tvDateStat;
  //  private ProgressBar progressBar;
    private Button btnList;
    SimpleArcLoader simpleArcLoader;
    ScrollView scrollView;
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid19);

//
//        responseView = (TextView) findViewById(R.id.responseView);
//        progressBar = (ProgressBar) findViewById(R.id.progressBar);
//
//        Button queryButton = (Button) findViewById(R.id.queryButton);
//        queryButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new RetrieveFeedTask().execute();
//            }
//        });

        tvCountryName = findViewById(R.id.tvCountryName);
        tvConfirmedStat = findViewById(R.id.tvConfirmedStat);
        tvDeathsStat = findViewById(R.id.tvDeathsStat);
        tvRecoveredStat = findViewById(R.id.tvRecoveredStat);
        tvActiveStat = findViewById(R.id.tvActiveStat);
        tvDateStat = findViewById(R.id.tvDateStat);


//        tvCasesList = findViewById(R.id.tvCasesList);
//        tvRecoveredList = findViewById(R.id.tvRecoveredList);
//        tvCriticalList = findViewById(R.id.tvCriticalList);
//        tvActiveList = findViewById(R.id.tvActiveList);
//        tvTodayCasesList = findViewById(R.id.tvTodayCasesList);
//        tvTotalDeathsList = findViewById(R.id.tvTotalDeathsList);
//        tvTodayDeathsList = findViewById(R.id.tvTodayDeathsList);
//        tvAffectedCountriesList = findViewById(R.id.tvAffectedCountriesList);


      //  progressBar = findViewById(R.id.progressBar);
        simpleArcLoader = findViewById(R.id.loader);
        scrollView = findViewById(R.id.scrollStats);

        pieChart = findViewById(R.id.piechart);
        btnList = findViewById(R.id.btnList);


        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Covid19Activity.this, CountryListActivity.class);
                startActivity(intent);
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
                //    case R.id.navHome:
//                        Intent intent1 = new Intent(Covid19Activity.this, MainActivity.class);
//                        startActivity(intent1);
//                        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
//                        break;
                    case R.id.navHero:
                        Intent intent2 = new Intent(Covid19Activity.this, MainActivity.class);
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

       // progressBar.setVisibility(View.INVISIBLE);
        loadCovidData();

    }

    private void loadCovidData() {

     //   progressBar.setVisibility(View.VISIBLE);
        simpleArcLoader.start();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //response received,handle response
                handleResponse(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //some error occurs, hide porgress and show error message
             //   progressBar.setVisibility(View.GONE);
                Toast.makeText(Covid19Activity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        //add request to queue
        RequestQueue requestQueue = Volley.newRequestQueue(Covid19Activity.this);
        requestQueue.add(stringRequest);
    }

    private void handleResponse(String response) {
        try {

            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String country = jsonObject.getString("Country");
                String confirmed = jsonObject.getString("Confirmed");
                String deaths = jsonObject.getString("Deaths");
                String recovered = jsonObject.getString("Recovered");
                String active = jsonObject.getString("Active");
                String date = jsonObject.getString("Date");
                tvCountryName.setText(country);
                tvConfirmedStat.setText(confirmed);
                tvDeathsStat.setText(deaths);
                tvRecoveredStat.setText(recovered);
                tvActiveStat.setText(active);
                tvDateStat.setText(date);

                pieChart.addPieSlice(new PieModel("Confirmed Cases",Integer.parseInt(tvConfirmedStat.getText().toString()), Color.parseColor("#2f435d")));
                pieChart.addPieSlice(new PieModel("Recovered Cases",Integer.parseInt(tvRecoveredStat.getText().toString()), Color.parseColor("#00c2cb")));
                pieChart.addPieSlice(new PieModel("Total No. Deaths",Integer.parseInt(tvDeathsStat.getText().toString()), Color.parseColor("#527096")));
                pieChart.addPieSlice(new PieModel("Active Cases",Integer.parseInt(tvConfirmedStat.getText().toString()), Color.parseColor("#ffde59")));
                pieChart.startAnimation();


                simpleArcLoader.stop();
                simpleArcLoader.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);



            }

         //  progressBar.setVisibility(View.GONE);

        } catch (Exception e) {
            e.printStackTrace();
         //  progressBar.setVisibility(View.GONE);
            simpleArcLoader.stop();
            simpleArcLoader.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }
    }


}

