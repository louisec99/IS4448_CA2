package com.example.is4448_117309293_ca2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;


    //defining views
    EditText editTextHeroId, editTextName, editTextRealname, etSearchRV;
    RatingBar ratingBar;
    Spinner spinnerTeam;
    ProgressBar progressBar;
    RecyclerView mRecyclerView;
    ListView listView;
    Button buttonAddUpdate;
    FloatingActionButton fab_add;


    ArrayList<Hero> mHeroList;
    HeroAdapter mHeroAdapter;

    // List<Hero> heroList;

    boolean isUpdating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        editTextHeroId = (EditText) findViewById(R.id.editTextHeroId);
//        editTextName = (EditText) findViewById(R.id.editTextName);
//        editTextRealname = (EditText) findViewById(R.id.editTextRealname);
//        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
//        spinnerTeam = (Spinner) findViewById(R.id.spinnerTeamAffiliation);
//        buttonAddUpdate = (Button) findViewById(R.id.buttonAddUpdate);


        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mHeroList = new ArrayList<Hero>();
        mHeroAdapter = new HeroAdapter(getApplicationContext(), mHeroList);


        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setAdapter(mHeroAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // etSearchRV = findViewById(R.id.etSearchRV);


        fab_add = findViewById(R.id.fab_add);
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddHeroDialog();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.navHome:
//                        Intent intent1 = new Intent(MainActivity.this, MainActivity.class);
//                        startActivity(intent1);
//                        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left);
//                        break;
                    case R.id.navHero:
                        // Intent intent2 = new Intent(MainActivity.this, HeroActivity.class);
                        // startActivity(intent2);

                        // overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        //  finish();
                        break;
                    case R.id.navCovid19:
                        Intent intent3 = new Intent(MainActivity.this, Covid19Activity.class);
                        startActivity(intent3);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                }
                return true;
            }

        });

        mHeroAdapter.notifyDataSetChanged();
        readHeroes();
        mHeroList.clear();


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                Hero listItem = mHeroList.get(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setTitle("Delete " + listItem.getName())
                        .setMessage("Are you sure you want to delete it?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                deleteHero(listItem.getId());
                                mHeroAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //  mHeroList.clear();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

                mHeroAdapter.notifyItemRemoved(position);
                mHeroAdapter.notifyItemRangeChanged(position, mHeroList.size());

            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.swipedelete))
                        .addSwipeLeftActionIcon(R.drawable.ic_delete_white)
                        .create()
                        .decorate();

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }).attachToRecyclerView(mRecyclerView);
        readHeroes();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                int position = viewHolder.getAdapterPosition();

                openUpdateHeroDialog(mHeroList.get(position).getId(), mHeroList.get(position).getName(), mHeroList.get(position).getRealname(), mHeroList.get(position).getRating(), mHeroList.get(position).getTeamaffiliation());
                mHeroList.clear();

            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeRightBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.swipeedit))
                        .addSwipeRightActionIcon(R.drawable.ic_edit_white)
                        .create()
                        .decorate();
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }).attachToRecyclerView(mRecyclerView);
        readHeroes();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu2) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu2);
        MenuItem searchItem = menu2.findItem(R.id.action_search);
        android.widget.SearchView searchview = (android.widget.SearchView) searchItem.getActionView();
        searchview.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchview.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mHeroAdapter = new HeroAdapter(getApplicationContext(), mHeroList);
                mHeroAdapter.getFilter().filter(newText);
                mHeroAdapter.notifyDataSetChanged();
                return false;
            }
        });

        return true;

    }

    private void readHeroes() {
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_READ_HEROES, null, CODE_GET_REQUEST);
        request.execute();
    }

    private void openUpdateHeroDialog(int id, String name, String realname, int rating, String teamaffiliation) {
        UpdateHeroDialogFragment updateHeroDialogFragment = new UpdateHeroDialogFragment();
        updateHeroDialogFragment.show(getSupportFragmentManager(), "Update Hero");

    }

    private void openAddHeroDialog() {
        AddHeroDialogFragment addHeroDialogFragment = new AddHeroDialogFragment();
        addHeroDialogFragment.show(getSupportFragmentManager(), "Add Hero");

    }
//
//    private void createHero() {
//        String name = editTextName.getText().toString().trim();
//        String realname = editTextRealname.getText().toString().trim();
//
//        int rating = (int) ratingBar.getRating();
//
//        String team = spinnerTeam.getSelectedItem().toString();
//
//
//        //validating the inputs
//        if (TextUtils.isEmpty(name)) {
//            editTextName.setError("Please enter name");
//            editTextName.requestFocus();
//            return;
//        }
//
//        if (TextUtils.isEmpty(realname)) {
//            editTextRealname.setError("Please enter real name");
//            editTextRealname.requestFocus();
//            return;
//        }
//
//        //if validation passes
//
//        HashMap<String, String> params = new HashMap<>();
//        params.put("name", name);
//        params.put("realname", realname);
//        params.put("rating", String.valueOf(rating));
//        params.put("teamaffiliation", team);
//
//
//        //Calling the create hero API
//        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_CREATE_HERO, params, CODE_POST_REQUEST);
//        request.execute();
//    }
//
//

    private void updateHero() {
        String id = editTextHeroId.getText().toString();
        String name = editTextName.getText().toString().trim();
        String realname = editTextRealname.getText().toString().trim();

        int rating = (int) ratingBar.getRating();

        String team = spinnerTeam.getSelectedItem().toString();

        if (TextUtils.isEmpty(name)) {
            editTextName.setError("Please enter name");
            editTextName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(realname)) {
            editTextRealname.setError("Please enter real name");
            editTextRealname.requestFocus();
            return;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        params.put("name", name);
        params.put("realname", realname);
        params.put("rating", String.valueOf(rating));
        params.put("teamaffiliation", team);

        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_UPDATE_HERO, params, CODE_POST_REQUEST);
        request.execute();

        buttonAddUpdate.setText("Add");

        editTextName.setText("");
        editTextRealname.setText("");
        ratingBar.setRating(0);
        spinnerTeam.setSelection(0);

        isUpdating = false;
    }

    private void refreshHeroList(JSONArray heroes) throws JSONException {
        mHeroList.clear();

        for (int i = 0; i < heroes.length(); i++) {

            JSONObject obj = heroes.getJSONObject(i);

            mHeroList.add(new Hero(
                    obj.getInt("id"),
                    obj.getString("name"),
                    obj.getString("realname"),
                    obj.getInt("rating"),
                    obj.getString("teamaffiliation")
            ));
        }

        //creating the adapter and setting it to the listview
        // HeroAdapter adapter = new HeroAdapter(heroList);
        //  listView.setAdapter(adapter);
        mHeroAdapter.notifyDataSetChanged();

    }

    private void deleteHero(int id) {
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_DELETE_HERO + id, null, CODE_GET_REQUEST);
        request.execute();
    }

    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {

        String url;

        HashMap<String, String> params;

        int requestCode;

        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(GONE);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    refreshHeroList(object.getJSONArray("heroes"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            if (requestCode == CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);

            if (requestCode == CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);

            return null;
        }
    }


}