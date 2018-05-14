package com.myapplication.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response {

    private List<Venue> venues;

    public List<Venue> getVenues() {
        return venues;
    }

    public void setVenues(List<Venue> venues) {
        this.venues = venues;
    }



}
