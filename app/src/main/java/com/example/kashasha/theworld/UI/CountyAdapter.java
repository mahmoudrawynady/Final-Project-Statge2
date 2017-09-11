package com.example.kashasha.theworld.UI;

import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.net.sip.SipSession;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;


import java.io.InputStream;

import com.example.kashasha.theworld.R;
import com.example.kashasha.theworld.SVG.SvgDecoder;
import com.example.kashasha.theworld.SVG.SvgDrawableTranscoder;
import com.example.kashasha.theworld.SVG.SvgSoftwareLayerSetter;

import java.util.List;

/**
 * Created by PH-Data™ 01221240053 on 19/07/2017.
 */

public class CountyAdapter extends RecyclerView.Adapter<CountyAdapter.CountryViewHolder> {
    private List<Country> countries;
    private Context context;
    private FragmentListener listener;

    
    CountyAdapter( Context context,List<Country>countries,FragmentListener listener) {
      this.context=context;
      this.countries=countries;
      this.listener=listener;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public CountryViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int i) {
        View layout;
        layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.country_item, viewGroup, false);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.launchDetails(i);
            }
        });
        CountryViewHolder countryViewHolder = new CountryViewHolder(layout);
        return countryViewHolder;

    }

    @Override
    public void onBindViewHolder(CountyAdapter.CountryViewHolder countryViewHolder, int i) {
        Country country = countries.get(i);
        countryViewHolder.countryTitle.setText(country.getName());
        CountriesUtitlities.loadSvgImage(countryViewHolder.countryFlag,context,country.getFlagUrl());
           }



    @Override
    public int getItemCount() {
        return  countries.size();
    }


    public class CountryViewHolder extends RecyclerView.ViewHolder {
        ImageView countryFlag;
        TextView  countryTitle;

        CountryViewHolder(View itemView) {
            super(itemView);
            countryFlag= (ImageView) itemView.findViewById(R.id.flag);
            countryTitle= (TextView) itemView.findViewById(R.id.country_title);

        }

    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
        notifyDataSetChanged();
    }

    public List<Country> getCountries() {
        return countries;
    }
}