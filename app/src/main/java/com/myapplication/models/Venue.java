package com.myapplication.models;


import android.os.Parcel;
import android.os.Parcelable;

import com.myapplication.MyApplication;

import java.util.List;

public class Venue{

    private String name;
    private List<Category> categories;
    private Location location;
    private boolean isFavorite;
    private String id;
    private String url ="";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isFavorite() {
        isFavorite = MyApplication.isFav(id);
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
        MyApplication.addToFav(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public float getDistFromSeattle(){

        android.location.Location l = new android.location.Location("this");
        l.setLatitude(location.getLat());
        l.setLongitude(location.getLng());
        return l.distanceTo(MyApplication.SEATTLE_LOC)/1000;

    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
