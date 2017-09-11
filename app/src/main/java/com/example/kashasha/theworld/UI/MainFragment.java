package com.example.kashasha.theworld.UI;

import android.content.ContentValues;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.kashasha.theworld.R;
import com.example.kashasha.theworld.Remote.CountriesTask;
import com.example.kashasha.theworld.data.DataReference;
import com.example.kashasha.theworld.data.TaskContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainFragment extends Fragment implements FragmentListener,  LoaderManager.LoaderCallbacks<Cursor>{
    private GridLayoutManager recyclerLayoutManager;
    private CountyAdapter countyAdapter;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private RecyclerView recyclerView;
    private Menu menu;
    private ArrayList<Country> Africa;
    private ArrayList<Country> Asia;
    private ArrayList<Country> Americas;
    private ArrayList<Country> Europe;
    private ArrayList<Country> Oceania;
    private CountriesTask countriesTask;
    private String[] regions;
    private ActivityListener activityListener;
    boolean dataBaseEnabled;
    private int type;
    private ImageView kontinentalImge;
    private int ImageDrawableId;
    public final static int LOADER_ID=101;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.countriesRecycler);
        kontinentalImge = (ImageView) view.findViewById(R.id.kontinental);
        collapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar_layout);
        countriesTask = new CountriesTask(this);
        ImageDrawableId = R.drawable.africa;
        this.initiateRegionsLists();
        regions = getResources().getStringArray(R.array.regions);
        SharedPreferences pref = getActivity().getSharedPreferences(getString(R.string.preference_key), 0);
        dataBaseEnabled = pref.getBoolean(getString(R.string.data_enabled), false);
        int kontinentalId = pref.getInt(getString(R.string.id), R.drawable.africa);
        String title = pref.getString(getString(R.string.title), getString(DataReference.AFRICA_ID));
        collapsingToolbarLayout.setTitle(title);
        kontinentalImge.setImageResource(kontinentalId);
        type = pref.getInt(getString(R.string.type), 0);
        if (dataBaseEnabled && !CountriesUtitlities.checkConnection(getContext())) {
            Toast.makeText(getContext(), getString(R.string.connection_message), Toast.LENGTH_LONG).show();
            getActivity().getSupportLoaderManager().initLoader(LOADER_ID,null,this);

        } else {
            if(savedInstanceState!=null){
                type=savedInstanceState.getInt(getString(R.string.type));
                ImageDrawableId=savedInstanceState.getInt(getString(R.string.id));
                title=savedInstanceState.getString(getString(R.string.title));
                collapsingToolbarLayout.setTitle(title);
                Africa=savedInstanceState.getParcelableArrayList(getString(R.string.africa));
                Asia=savedInstanceState.getParcelableArrayList(getString(R.string.asia));
                Europe=savedInstanceState.getParcelableArrayList(getString(R.string.europe));
                Americas=savedInstanceState.getParcelableArrayList(getString(R.string.americas));
                Oceania=savedInstanceState.getParcelableArrayList(getString(R.string.oceania));
                kontinentalImge.setImageResource(ImageDrawableId);
                Log.e("rawymahmoud",""+type);
                initiateAdapters(type);
            }
            else {

                countriesTask.setType(type);
                countriesTask.execute(regions[type]);
            }
        }
        this.setHasOptionsMenu(true);
        return view;
    }

    public void getDataFromCursor(Cursor cursor) {
        while (cursor.moveToNext()) {
            Country country = new Country();
            String region = getStringFromCursor(cursor, TaskContract.TaskEntry.REGION);
            String name = getStringFromCursor(cursor, TaskContract.TaskEntry.NAME);
            String capital = getStringFromCursor(cursor, TaskContract.TaskEntry.CAPITAL);
            String subRegion = getStringFromCursor(cursor, TaskContract.TaskEntry.SUB_REGION);
            String language = getStringFromCursor(cursor, TaskContract.TaskEntry.LANGUAGE);
            String timeZone = getStringFromCursor(cursor, TaskContract.TaskEntry.TIME_ZONE);
            String callingCode = getStringFromCursor(cursor, TaskContract.TaskEntry.CAIIING_CODE);
            String flag = getStringFromCursor(cursor, TaskContract.TaskEntry.FLAG);
            String currencyNam = getStringFromCursor(cursor, TaskContract.TaskEntry.CURRENCY_NAME);
            String currencySymbol = getStringFromCursor(cursor, TaskContract.TaskEntry.CURRENCY_SYNBOL);
            double population = getDoubleFromCursor(cursor, TaskContract.TaskEntry.PUPULATION);
            double lat = getDoubleFromCursor(cursor, TaskContract.TaskEntry.LAT);
            double lng = getDoubleFromCursor(cursor, TaskContract.TaskEntry.LANG);
            country.setName(name);
            country.setTimeZone(timeZone);
            country.setLanguage(language);
            country.setLng(lng);
            country.setLat(lat);
            country.setFlagUrl(flag);
            country.setPopulation(population);
            country.setCapital(capital);
            country.setCallingCode(callingCode);
            country.setSubRegion(subRegion);
            country.setCurrencyName(currencyNam);
            country.setCurrencySymbol(currencySymbol);
            country.setRegions(region);
            initiateRegionsLists();
            fillDataToRegions(country);


        }
        cursor.close();

    }


    public String getStringFromCursor(Cursor cursor, String columne) {

        return cursor.getString(cursor.getColumnIndex(columne));
    }

    public double getDoubleFromCursor(Cursor cursor, String columne) {

        return cursor.getDouble(cursor.getColumnIndex(columne));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.africa) {
            collapsingToolbarLayout.setTitle(getString(DataReference.AFRICA_ID));
            makeActionOnItem(0, item);
            ImageDrawableId = R.drawable.africa;
            kontinentalImge.setImageResource(R.drawable.africa);
        }
        if (item.getItemId() == R.id.asia) {
            collapsingToolbarLayout.setTitle(getString(DataReference.ASIA_ID));

            makeActionOnItem(1, item);
            ImageDrawableId = R.drawable.asia;
            kontinentalImge.setImageResource(ImageDrawableId);

        } else if (item.getItemId() == R.id.europe) {
            collapsingToolbarLayout.setTitle(getString(DataReference.EUROPE_ID));

            makeActionOnItem(2, item);
            ImageDrawableId = R.drawable.europe;

            kontinentalImge.setImageResource(ImageDrawableId);


        } else if (item.getItemId() == R.id.americas) {
            collapsingToolbarLayout.setTitle(getString(DataReference.AMERICA_ID));

            makeActionOnItem(3, item);
            ImageDrawableId = R.drawable.americas;

            kontinentalImge.setImageResource(ImageDrawableId);

        } else if (item.getItemId() == R.id.oceania) {
            collapsingToolbarLayout.setTitle(getString(DataReference.OCEANIA_ID));
            makeActionOnItem(4, item);
            ImageDrawableId = R.drawable.oceania;
            kontinentalImge.setImageResource(ImageDrawableId);

        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        countriesTask.cancel(true);
        outState.putInt(getString(R.string.type),countriesTask.getType());
        outState.putInt(getString(R.string.id),ImageDrawableId);
        outState.putString(getString(R.string.title),collapsingToolbarLayout.getTitle().toString());
        outState.putParcelableArrayList(getString(R.string.africa),Africa);
        outState.putParcelableArrayList(getString(R.string.asia),Asia);
        outState.putParcelableArrayList(getString(R.string.europe),Europe);
        outState.putParcelableArrayList(getString(R.string.americas),Americas);
        outState.putParcelableArrayList(getString(R.string.oceania),Oceania);
        Log.e("hhhhh","rawyrawy");

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        this.menu = menu;
        markeMenuItem(menu);
        super.onPrepareOptionsMenu(menu);
    }

    public void makeActionOnItem(int index, MenuItem menuItem) {
        if (CountriesUtitlities.checkConnection(getContext())) {
            countriesTask = new CountriesTask(this);
            countriesTask.setType(index);
            countriesTask.execute(regions[index]);
        } else if (dataBaseEnabled) {
            changeAdapterData(index);
            countriesTask.setType(index);
        }


        menuItem.setChecked(true);


    }

    @Override
    public void onStop() {
        super.onStop();
        fillDataBase(Africa);
        fillDataBase(Asia);
        fillDataBase(Europe);
        fillDataBase(Americas);
        fillDataBase(Oceania);
    }

    public void initiateRegionsLists() {
        Africa = new ArrayList<>();
        Asia = new ArrayList<>();
        Europe = new ArrayList<>();
        Americas = new ArrayList<>();
        Oceania = new ArrayList<>();
    }


    public void parseDataToViews(String response) {

        try {
            JSONArray CountriesArray = new JSONArray(response);
            for (int i = 0; i < CountriesArray.length(); i++) {
                JSONObject jsonObject = CountriesArray.getJSONObject(i);
                String name = jsonObject.getString(DataReference.NAME);

                String capital = jsonObject.getString(DataReference.CAPITAL);
                String region = jsonObject.getString(DataReference.REGION);
                String subRegion = jsonObject.getString(DataReference.SUB_REGION);
                String callingcode = jsonObject.getString(DataReference.CALLING_CODES);
                String flag = jsonObject.getString(DataReference.FLAG);
                JSONArray latlng = jsonObject.getJSONArray(DataReference.LATLNG);
                JSONArray timeZoneArray = jsonObject.getJSONArray(DataReference.TIME_ZONES);
                String timeZone = timeZoneArray.getString(0);
                double lat = latlng.getDouble(0);
                double lang = latlng.getDouble(1);
                double population = jsonObject.getDouble(DataReference.POPULATION);
                JSONArray languageArray = jsonObject.getJSONArray(DataReference.LANGUAGES);
                JSONObject languageObject = languageArray.getJSONObject(0);
                String language = languageObject.getString(DataReference.NATIVE_NAME);
                JSONArray currenciesArray = jsonObject.getJSONArray(DataReference.CURRENCIES);
                JSONObject currencyObject = currenciesArray.getJSONObject(0);
                String currencySymbol = currencyObject.getString(DataReference.SYMBOL);
                String currencyName = currencyObject.getString(DataReference.NAME);
                Country country = new Country();
                country.setName(name);
                country.setCapital(capital);
                country.setRegions(region);
                country.setSubRegion(subRegion);
                country.setCallingCode(callingcode);
                country.setCurrencyName(currencyName);
                country.setTimeZone(timeZone);
                country.setFlagUrl(flag);
                country.setPopulation(population);
                country.setCurrencySymbol(currencySymbol);
                country.setLat(lat);
                country.setLng(lang);
                country.setLanguage(language);
                fillDataToRegions(country);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void fillDataBase(ArrayList<Country> countries) {
        ContentValues values = new ContentValues();
        SharedPreferences pref = getActivity().getSharedPreferences(getString(R.string.preference_key), 0);
        SharedPreferences.Editor save = pref.edit();
        save.putInt(getString(R.string.type), countriesTask.getType());
        save.putInt(getString(R.string.id), ImageDrawableId);
        save.putString(getString(R.string.title), collapsingToolbarLayout.getTitle().toString());
        if (countries.size() != 0) {
            save.putBoolean(getString(R.string.data_enabled), true);
            for (int i = 0; i < countries.size(); i++) {
                Country country = countries.get(i);
                values.put(TaskContract.TaskEntry.NAME, country.getName());
                values.put(TaskContract.TaskEntry.CAPITAL, country.getCapital());
                values.put(TaskContract.TaskEntry.REGION, country.getRegions());
                values.put(TaskContract.TaskEntry.SUB_REGION, country.getSubRegion());
                values.put(TaskContract.TaskEntry.CAIIING_CODE, country.getCallingCode());
                values.put(TaskContract.TaskEntry.CURRENCY_NAME, country.getCurrencyName());
                values.put(TaskContract.TaskEntry.CURRENCY_SYNBOL, country.getCurrencySymbol());
                values.put(TaskContract.TaskEntry.LAT, country.getLat());
                values.put(TaskContract.TaskEntry.LANG, country.getLng());
                values.put(TaskContract.TaskEntry.FLAG, country.getFlagUrl());
                values.put(TaskContract.TaskEntry.PUPULATION, country.getPopulation());
                values.put(TaskContract.TaskEntry.TIME_ZONE, country.getTimeZone());
                values.put(TaskContract.TaskEntry.LANGUAGE, country.getLanguage());
                getActivity().getContentResolver().insert(TaskContract.TaskEntry.CONTENT_URI, values);
            }
        }
        save.commit();

    }

    @Override
    public void fillCountries(String response) {
        parseDataToViews(response);


    }

    public String[] getProjection() {
        String[] projection = {
                TaskContract.TaskEntry.NAME,
                TaskContract.TaskEntry.CAPITAL,
                TaskContract.TaskEntry.REGION,
                TaskContract.TaskEntry.SUB_REGION,
                TaskContract.TaskEntry.FLAG,
                TaskContract.TaskEntry.LANGUAGE,
                TaskContract.TaskEntry.CURRENCY_NAME,
                TaskContract.TaskEntry.CURRENCY_SYNBOL,
                TaskContract.TaskEntry.TIME_ZONE,
                TaskContract.TaskEntry.CAIIING_CODE,
                TaskContract.TaskEntry.PUPULATION,
                TaskContract.TaskEntry.LAT,
                TaskContract.TaskEntry.LANG
        };
        return projection;
    }

    public void fillDataToRegions(Country country) {
        if (country.getRegions().equals(getString(R.string.africa))) {
            this.Africa.add(country);
        } else if (country.getRegions().equals(getString(R.string.asia)))
            this.Asia.add(country);
        else if (country.getRegions().equals(getString(R.string.europe)))
            this.Europe.add(country);
        else if (country.getRegions().equals(getString(R.string.americas)))
            this.Americas.add(country);
        else if (country.getRegions().equals(getString(R.string.oceania)))
            this.Oceania.add(country);

    }

    @Override
    public void initiateAdapters(int type) {
        recyclerLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(recyclerLayoutManager);
        changeAdapterData(type);

    }

    public void changeAdapterData(int type) {
        if (type == 0) {
            countyAdapter = new CountyAdapter(getActivity(), Africa, this);
            recyclerView.setAdapter(countyAdapter);
        } else if (type == 1) {
            if (Asia.size() > 0)
                updateAdapter(Asia);
        } else if (type == 2) {
            if (Europe.size() > 0) {
                updateAdapter(Europe);
            }
        } else if (type == 3) {
            if (Americas.size() > 0)
                updateAdapter(Americas);

        } else if (type == 4) {
            if (Oceania.size() > 0)
                updateAdapter(Oceania);
        }


    }


    public void updateAdapter(ArrayList<Country> countries) {
        if (countyAdapter == null)
            countyAdapter = new CountyAdapter(getContext(), countries, this);
        countyAdapter.setCountries(countries);
        recyclerView.setAdapter(countyAdapter);


    }

    @Override
    public void launchDetails(int index) {
        int type = countriesTask.getType();
        putDataToDetails(type, index);
    }

    public void putDataToDetails(int type, int index) {
        Bundle bundle = new Bundle();
        if (type == 0)
            bundle.putParcelable(getString(R.string.extra_key), Africa.get(index));
        else if (type == 1)
            bundle.putParcelable(getString(R.string.extra_key), Asia.get(index));
        else if (type == 2)
            bundle.putParcelable(getString(R.string.extra_key), Europe.get(index));
        else if (type == 3)
            bundle.putParcelable(getString(R.string.extra_key), Americas.get(index));
        else
            bundle.putParcelable(getString(R.string.extra_key), Oceania.get(index));
        if (activityListener.checkToPane())
            activityListener.sendDataToMain(bundle);
        else {

            Intent intent = new Intent(getActivity(), CountryDetails.class);
            intent.putExtra(getString(R.string.bundle_key), bundle);
            startActivity(intent);

        }




    }

    public void markeMenuItem(Menu menu) {
        if (type == 0)
            menu.findItem(R.id.africa).setChecked(true);
        else if (type == 1)
            menu.findItem(R.id.asia).setChecked(true);
        else if (type == 2)
            menu.findItem(R.id.europe).setChecked(true);

        else if (type == 3)
            menu.findItem(R.id.americas).setChecked(true);
        else
            menu.findItem(R.id.oceania).setChecked(true);


    }

    public void setActivityListener(ActivityListener activityListener) {
        this.activityListener = activityListener;
    }




    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        CursorLoader cursorLoader=new CursorLoader(getContext(),TaskContract.TaskEntry.CONTENT_URI,getProjection(),null,null,null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor cursor) {
        getDataFromCursor(cursor);
        initiateAdapters(type);
        stopProgress();
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {

    }

    @Override
    public void showMessage() {
        Toast.makeText(getContext(),getString(R.string.connection_problem),Toast.LENGTH_LONG).show();
    }

    @Override
    public void stopProgress() {
        activityListener.stopProgress();
    }
}




