package com.example.kashasha.theworld.Remote;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.kashasha.theworld.UI.ActivityListener;
import com.example.kashasha.theworld.UI.FragmentListener;
import com.example.kashasha.theworld.UI.MainActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CountriesTask extends AsyncTask<String, String, String> {
    private FragmentListener listener;
    private int type;

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public CountriesTask(FragmentListener listener) {
        this.listener = listener;
    }



    @Override
    protected void onPostExecute(String countriesResponse) {
        super.onPostExecute(countriesResponse);
        listener.stopProgress();
        if (countriesResponse == null) {
            listener.showMessage();
            return;

        }
        listener.fillCountries(countriesResponse);
        listener.initiateAdapters(type);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /********************************************************************************/
    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        StringBuffer buffer = new StringBuffer();
        String movietJsonStr = null;

        try {
            URL url = new URL(strings[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                return null;
            }
            movietJsonStr = buffer.toString();


        } catch (IOException e) {

            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                }

            }
        }

        return movietJsonStr;

    }

}

