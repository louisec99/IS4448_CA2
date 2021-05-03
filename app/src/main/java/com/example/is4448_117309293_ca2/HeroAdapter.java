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
import java.util.List;

public class HeroAdapter extends RecyclerView.Adapter<HeroAdapter.HeroViewHolder> implements Filterable {

    private Context mContext;
    private final ArrayList<Hero> HeroList;
    private ArrayList<Hero> HeroListFull;


    public HeroAdapter(Context context, ArrayList<Hero> heroList){
        mContext = context;
        HeroList = heroList;
        HeroListFull = new ArrayList<>(HeroList);
    }


    public class HeroViewHolder extends RecyclerView.ViewHolder {

        public TextView tvHeroID, tvHeroName, tvHeroRealName, tvHeroRating, tvHeroTeam;

        public HeroViewHolder(@NonNull View itemView) {
            super(itemView);
          //  tvHeroID = itemView.findViewById(R.id.tvHeroID);
            tvHeroName = itemView.findViewById(R.id.tvHeroName);
            tvHeroRealName = itemView.findViewById(R.id.tvHeroRealName);
            tvHeroRating = itemView.findViewById(R.id.tvHeroRating);
            tvHeroTeam = itemView.findViewById(R.id.tvHeroTeam);
        }
    }


    @NonNull
    @Override
    public HeroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_hero_list_recyclerview, parent, false);
        HeroViewHolder heroViewHolder = new HeroViewHolder(view);
        return heroViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HeroViewHolder holder, int position) {

        Hero currentItem = HeroList.get(position);

      //  holder.tvHeroID.setText(String.valueOf(currentItem.getId()));
        holder.tvHeroName.setText(currentItem.getName());
        holder.tvHeroRealName.setText(currentItem.getRealname());
        holder.tvHeroRating.setText(String.valueOf(currentItem.getRating()));
        holder.tvHeroTeam.setText(currentItem.getTeamaffiliation());

    }

    @Override
    public int getItemCount() {
        return HeroList.size();
    }

    @Override
    public Filter getFilter() {
        return HeroListFilter;
    }


    private final Filter HeroListFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Hero> filteredList = new ArrayList<Hero>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(HeroListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Hero item : HeroListFull) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            HeroList.clear();
            HeroList.addAll((List) results.values);
            notifyDataSetChanged();
        }

    };

//Filtering Real Name

    public Filter getRealNameFilter() {
        return HeroListFilterRealName;
    }

    private final Filter HeroListFilterRealName = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Hero> filteredListRealName = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredListRealName.addAll(HeroListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Hero item : HeroListFull) {
                    if(item.getRealname().toLowerCase().contains(filterPattern)) {
                        filteredListRealName.add(item);

                    }
                }
            }
            FilterResults resultsRealName = new FilterResults();
            resultsRealName.values = filteredListRealName;
            return resultsRealName;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults resultsRealName) {
            HeroList.clear();
            HeroList.addAll((List) resultsRealName.values);
            notifyDataSetChanged();
        }

    };

//Filtering Team Affiliation

    public Filter getTeamAffiliationFilter() {
        return HeroListFilterTeamAffiliation;
    }

    private final Filter HeroListFilterTeamAffiliation = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Hero> filteredListTeamAffiliation = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredListTeamAffiliation.addAll(HeroListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Hero item : HeroListFull) {
                    if(item.getTeamaffiliation().toLowerCase().contains(filterPattern)) {
                        filteredListTeamAffiliation.add(item);
                    }
                }
            }
            FilterResults resultsTeamAff = new FilterResults();
            resultsTeamAff.values = filteredListTeamAffiliation;
            return resultsTeamAff;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults resultsTeamAff) {
            HeroList.clear();
            HeroList.addAll((List)resultsTeamAff.values);
            notifyDataSetChanged();
        }

    };

//    private Filter HeroListFilter = new Filter() {
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            List<Hero> filteredList = new ArrayList<>();
//
//            if (constraint == null || constraint.length() == 0) {
//                filteredList.addAll(HeroListFull);
//            } else {
//                String filterPattern = constraint.toString().toLowerCase().trim();
//
//                for (Hero item : HeroListFull) {
//                    if (item.getName().toLowerCase().contains(filterPattern)) {
//                        filteredList.add(item);
//
//                    }else if(item.getTeamaffiliation().toLowerCase().contains(filterPattern)) {
//                        filteredList.add(item);
//
//                    }else if (item.getRealname().toLowerCase().contains(filterPattern)) {
//                        filteredList.add(item);
//
//                        //add in search for ID and rating
//                    }
//                }
//            }
//
//            FilterResults results = new FilterResults();
//            results.values = filteredList;
//            return results;
//        }
//
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//            HeroList.clear();
//            HeroList.addAll((List) results.values);
//            notifyDataSetChanged();
//        }
//
//    };

}
