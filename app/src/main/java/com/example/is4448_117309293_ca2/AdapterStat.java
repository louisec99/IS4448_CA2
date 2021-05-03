package com.example.is4448_117309293_ca2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterStat extends RecyclerView.Adapter<AdapterStat.HolderStat> implements Filterable {

    private Context context;
    public ArrayList<ModelStat> statArrayList, filterList;
    private FilterStat filter;

    public AdapterStat(Context context, ArrayList<ModelStat> statArrayList) {
        this.context = context;
        this.statArrayList = statArrayList;
        this.filterList = statArrayList;
    }


    @NonNull
    @Override
    public HolderStat onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate row_stat layout
        View view = LayoutInflater.from(context). inflate(R.layout.row_stat, parent, false);
        return new HolderStat(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderStat holder, int position) {

        //get data
        ModelStat modelStat = statArrayList.get(position);

        String country = modelStat.getCountry();
        String totalConfirmed = modelStat.getTotalConfirmed();
        String totalDeaths = modelStat.getTotalDeaths();
        String totalRecovered = modelStat.getTotalRecovered();
        String newConfirmed = modelStat.getNewConfirmed();
        String newDeaths = modelStat.getNewDeaths();
        String newRecovered = modelStat.getNewRecovered();

        //set data

        holder.tvCountry2.setText(country);
        holder.tvTodayRecovered2.setText(newRecovered);
        holder.tvTodayDeaths2.setText(newDeaths);
        holder.tvTodayCases2.setText(newConfirmed);
        holder.tvRecovered2.setText(totalRecovered);
        holder.tvDeaths2.setText(totalDeaths);
        holder.tvCases2.setText(totalConfirmed);

    }

    @Override
    public int getItemCount() {
        return statArrayList.size();
    }

    @Override
    public Filter getFilter() {
        if (filter == null){
            filter = new FilterStat(this, filterList);

        }
        return filter;
    }


    //view holder class
    class HolderStat extends RecyclerView.ViewHolder{

        //UI Views
        TextView tvCountry2, tvCases2, tvTodayCases2, tvDeaths2, tvTodayDeaths2, tvRecovered2, tvTodayRecovered2;

        public HolderStat(@NonNull View itemView) {
            super(itemView);


            //initiate UI views
            tvCountry2 = itemView.findViewById(R.id.tvCountry2);
            tvCases2 = itemView.findViewById(R.id.tvCases2);
            tvTodayCases2 = itemView.findViewById(R.id.tvTodayCases2);
            tvDeaths2 = itemView.findViewById(R.id.tvDeaths2);
            tvTodayDeaths2 = itemView.findViewById(R.id.tvTodayDeaths2);
            tvRecovered2 = itemView.findViewById(R.id.tvRecovered2);
            tvTodayRecovered2 = itemView.findViewById(R.id.tvTodayRecovered2);


        }
    }
}
