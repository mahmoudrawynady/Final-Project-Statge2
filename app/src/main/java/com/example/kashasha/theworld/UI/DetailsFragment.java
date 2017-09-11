package com.example.kashasha.theworld.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import com.example.kashasha.theworld.R;


import org.json.JSONException;
import org.json.JSONObject;


public class DetailsFragment extends Fragment {
    private DetailsAdapter detailsAdapter;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView detailsRecyclerView;
    private ImageView flag;
    private Country country;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_fragment, container, false);
        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getBundleExtra(getString(R.string.bundle_key));
        country = (Country) (bundle.getParcelable(getString(R.string.extra_key)));
        collapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar_layout);
        collapsingToolbarLayout.setTitle(country.getName());
        flag = (ImageView) view.findViewById(R.id.flag);
        CountriesUtitlities.loadSvgImage(flag, getContext(), country.getFlagUrl());
        linearLayoutManager = new LinearLayoutManager(getActivity());
        detailsAdapter = new DetailsAdapter(getActivity(), getResources().getStringArray(R.array.detailsLabels), country);
        detailsRecyclerView = (RecyclerView) view.findViewById(R.id.detailsRecycler);
        detailsRecyclerView.setLayoutManager(linearLayoutManager);
        detailsRecyclerView.setAdapter(detailsAdapter);
        setHasOptionsMenu(true);
        return view;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.place) {
            sendLocationDataToMap();

            return true;
        } else if (item.getItemId() == R.id.share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, createShareData());
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public String createShareData() {
        String data = country.getName() +
                "\n" + country.getRegions() +
                "\n" + country.getCapital() +
                "\n" + country.getLanguage();
        return data;
    }

    public void sendLocationDataToMap() {
        Intent intent = new Intent(getActivity(), MapsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putDouble(getString(R.string.lat), country.getLat());
        bundle.putDouble(getString(R.string.lng), country.getLng());
        bundle.putString(getString(R.string.name), country.getName());
        intent.putExtra(getString(R.string.map_data), bundle);
        startActivity(intent);


    }

}
