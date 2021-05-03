package com.example.is4448_117309293_ca2;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static android.view.View.GONE;


public class UpdateHeroDialogFragment extends DialogFragment {

    EditText editTextHeroId, editTextName, editTextRealname;
    RatingBar ratingBar;
    Spinner spinnerTeam;
    ProgressBar progressBar;
    Button buttonAddUpdate;
    ArrayList<Hero> mHeroList = new ArrayList<Hero>();
    HeroAdapter mHeroAdapter;
    boolean isUpdating = false;

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

   // final Hero hero = mHeroList.get(position);


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.update_hero_dialogbox, null);



      //  Hero hero = mHeroList.get(position);

        builder.setView(view)
                .setTitle("Update Hero")
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


//                        isUpdating = true;
//                        editTextHeroId.setText(String.valueOf(hero.getId()));
//                        editTextName.setText(hero.getName());
//                        editTextRealname.setText(hero.getRealname());
//                        ratingBar.setRating(hero.getRating());
//                        spinnerTeam.setSelection(((ArrayAdapter<String>) spinnerTeam.getAdapter()).getPosition(hero.getTeamaffiliation()));
//

                        updateHero();
                        mHeroAdapter.notifyDataSetChanged();
//                        final Hero hero = mHeroList.get(position);
//
//                        isUpdating = true;
//                        editTextHeroId.setText(String.valueOf(hero.getId()));
//                        editTextName.setText(hero.getName());
//                        editTextRealname.setText(hero.getRealname());
//                        ratingBar.setRating(hero.getRating());
//                        spinnerTeam.setSelection(((ArrayAdapter<String>) spinnerTeam.getAdapter()).getPosition(hero.getTeamaffiliation()));
//

//                        final Hero hero = mHeroList.get(position);
//                        //updateHero();
//
//                        isUpdating = true;
//                        editTextHeroId.setText(String.valueOf(hero.getId()));
//                        editTextName.setText(hero.getName());
//                        editTextRealname.setText(hero.getRealname());
//                        ratingBar.setRating(hero.getRating());
//                        spinnerTeam.setSelection(((ArrayAdapter<String>) spinnerTeam.getAdapter()).getPosition(hero.getTeamaffiliation()));
//                        buttonAddUpdate.setText("Update");
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

        editTextHeroId = (EditText) view.findViewById(R.id.editTextHeroId);
        editTextName = (EditText) view.findViewById(R.id.editTextName);
        editTextRealname = (EditText) view.findViewById(R.id.editTextRealname);
        ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        spinnerTeam = (Spinner) view.findViewById(R.id.spinnerTeamAffiliation);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mHeroAdapter = new HeroAdapter(getActivity().getApplicationContext(), mHeroList);

        readHeroes();
        mHeroList.clear();
        return builder.create();
    }

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

//        buttonAddUpdate.setText("Add");

        editTextName.setText("");
        editTextRealname.setText("");
        ratingBar.setRating(0);
        spinnerTeam.setSelection(0);

        isUpdating = false;
    }

    private void readHeroes() {
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_READ_HEROES, null, CODE_GET_REQUEST);
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
                    //  Toast.makeText(getActivity(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    refreshHeroList(object.getJSONArray("heroes"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
        }

        //the network operation will be performed in background
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

    private void refreshHeroList(JSONArray heroes) throws JSONException {
        //clearing previous heroes
        mHeroList.clear();

        for (int i = 0; i < heroes.length(); i++) {
            //getting each hero object
            JSONObject obj = heroes.getJSONObject(i);

            //adding the hero to the list
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
      //  Toast.makeText(getActivity().getApplicationContext(), "Hero updated", Toast.LENGTH_SHORT).show();
        mHeroAdapter.notifyDataSetChanged();
        mHeroList.clear();
    }

}
