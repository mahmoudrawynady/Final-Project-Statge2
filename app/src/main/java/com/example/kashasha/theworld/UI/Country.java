package com.example.kashasha.theworld.UI;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by PH-Data™ 01221240053 on 19/07/2017.
 */

public class Country implements Parcelable{
    private String name;
    private String capital;
    private String regions;
    private String subRegion;
    private String language;
    private String flagUrl;
    private String timeZone;
    private String currencyName;
    private String currencySymbol;
    private String callingCode;
    private double lat;
    private double lng;
    private double population;

    public Country(){}

    protected Country(Parcel in) {
        name = in.readString();
        capital = in.readString();
        regions = in.readString();
        subRegion = in.readString();
        language = in.readString();
        flagUrl = in.readString();
        timeZone = in.readString();
        currencyName = in.readString();
        currencySymbol = in.readString();
        callingCode = in.readString();
        lat = in.readDouble();
        lng = in.readDouble();
        population = in.readDouble();
    }

    public static final Creator<Country> CREATOR = new Creator<Country>() {
        @Override
        public Country createFromParcel(Parcel in) {
            return new Country(in);
        }

        @Override
        public Country[] newArray(int size) {
            return new Country[size];
        }
    };

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getCapital() {
        return capital;
    }

    public void setRegions(String regions) {
        this.regions = regions;
    }

    public String getRegions() {
        return regions;
    }

    public void setSubRegion(String subRegion) {
        this.subRegion = subRegion;
    }

    public String getSubRegion() {
        return subRegion;
    }

    public void setCallingCode(String callingCode) {
        this.callingCode = callingCode;
    }

    public String getCallingCode() {
        return callingCode;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setFlagUrl(String flagUrl) {
        this.flagUrl = flagUrl;
    }
    public String getFlagUrl() {
        return flagUrl;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLat() {
        return lat;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLng() {
        return lng;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setPopulation(double population) {
        this.population = population;
    }

    public double getPopulation() {
        return population;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(capital);
        dest.writeString(regions);
        dest.writeString(subRegion);
        dest.writeString(language);
        dest.writeString(flagUrl);
        dest.writeString(timeZone);
        dest.writeString(currencyName);
        dest.writeString(currencySymbol);
        dest.writeString(callingCode);
        dest.writeDouble(lat);
        dest.writeDouble(lng);
        dest.writeDouble(population);
    }
}
