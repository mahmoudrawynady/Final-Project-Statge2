package com.example.kashasha.theworld.UI;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.kashasha.theworld.R;


public class MainActivity extends AppCompatActivity implements ActivityListener {
    private ProgressBar progressBar;
    private Toolbar toolbar;
    private boolean toPanEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.par);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        FragmentManager manager = getSupportFragmentManager();
        Fragment detailsFragment = manager.findFragmentById(R.id.detaifragment);
        MainFragment mainFragment=(MainFragment) manager.findFragmentById(R.id.mainfragment);
        mainFragment.setActivityListener(this);

        if (detailsFragment != null) {
            toPanEnabled = true;
        }
        setSupportActionBar(toolbar);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

     @Override
    public  void stopProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public boolean checkToPane() {
        if (toPanEnabled)
            return true;
        return false;
    }

    @Override
    public void sendDataToMain(Bundle bundle) {
       getIntent().putExtra(getString(R.string.extra_key),bundle);
    }

}
