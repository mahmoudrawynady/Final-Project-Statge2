package com.example.kashasha.theworld.UI;

/**
 * Created by PH-Data™ 01221240053 on 21/07/2017.
 */

public interface FragmentListener {
    void fillCountries(String response);
    void initiateAdapters(int type);
    void launchDetails(int idex);
    void showMessage();
    void stopProgress();

}
