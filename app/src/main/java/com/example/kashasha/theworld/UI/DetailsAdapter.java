package com.example.kashasha.theworld.UI;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kashasha.theworld.R;

import java.util.List;

/**
 * Created by PH-Data™ 01221240053 on 21/07/2017.
 */

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.DetailsViewHolder> {
    private Country country;
    private String[] detailsLabels;
    private Context context;
    private String []countryData;

    DetailsAdapter(Context context, String[] detailsLabels, Country country) {
        this.detailsLabels = detailsLabels;
        this.country = country;
        this.context = context;
        fillCountryData(country);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public DetailsViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int i) {
        View layout;
        layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.details_item, viewGroup, false);
        DetailsViewHolder detailsViewHolder = new DetailsViewHolder(layout);
        return detailsViewHolder;
    }

    @Override
    public void onBindViewHolder(DetailsViewHolder detailsViewHolder, int i) {
        detailsViewHolder.key.setText(detailsLabels[i]);
        detailsViewHolder.value.setText(countryData[i]);
    }


    @Override
    public int getItemCount() {
        return 7;
    }


    public class DetailsViewHolder extends RecyclerView.ViewHolder {
        TextView key,value;
        ImageView flag;

        DetailsViewHolder(View itemView) {
            super(itemView);
            key= (TextView) itemView.findViewById(R.id.key);
            value= (TextView) itemView.findViewById(R.id.value);
            flag= (ImageView) itemView.findViewById(R.id.flag);

        }

    }
    public void fillCountryData(Country country){
        countryData=new String[7];
        countryData[0]=country.getCapital();
        countryData[1]=country.getRegions();
        countryData[2]=""+country.getPopulation();
        countryData[3]=country.getLanguage();
        countryData[4]=country.getTimeZone();
        countryData[5]=country.getCurrencyName();
        countryData[6]=country.getCallingCode();







    }


}