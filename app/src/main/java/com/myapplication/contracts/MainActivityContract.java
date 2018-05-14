package com.myapplication.contracts;

import com.myapplication.models.Venue;

import java.util.List;

public interface MainActivityContract {

    public interface View{

        void populateList(List<Venue> venues);
    }

    public interface Presenter{
        public void start();

        void fetchResults(String s);


    }

    public interface RepoListener {

        void onResponse(List<Venue> venues);
    }
}
