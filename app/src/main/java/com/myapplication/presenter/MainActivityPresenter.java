package com.myapplication.presenter;

import com.myapplication.contracts.MainActivityContract;
import com.myapplication.contracts.MainActivityContract.View;
import com.myapplication.models.Venue;
import com.myapplication.repository.ResultRepository;

import java.util.List;

public class MainActivityPresenter implements MainActivityContract.Presenter, MainActivityContract.RepoListener {

    public final View view;
    private ResultRepository repo ;

    public MainActivityPresenter(MainActivityContract.View view) {
        this.view = view;
    }


    @Override
    public void start() {
        repo = new ResultRepository(this);
    }

    @Override
    public void fetchResults(String s) {
        repo.fetchResultsFromWeb(s);
    }

    @Override
    public void onResponse(List<Venue> venues) {
        view.populateList(venues);
    }
}
