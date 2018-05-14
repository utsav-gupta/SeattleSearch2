package com.myapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.myapplication.R;
import com.myapplication.contracts.MainActivityContract;
import com.myapplication.models.Venue;
import com.myapplication.presenter.MainActivityPresenter;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View{

    protected MainActivityContract.Presenter presenter;
    private View listView;
    private List<Venue> venueList = new ArrayList<Venue>();
    private ListAdapter listAdapter;
    private List<String> history = new ArrayList<String>();
    private FloatingActionButton fab;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, MapsActivity.class);
                Gson gson = new Gson();
                JsonElement element = gson.toJsonTree(venueList, new TypeToken<List<Venue>>() {}.getType());
                JsonArray jsonArray = element.getAsJsonArray();
                i.putExtra("venue_list",jsonArray.toString());
                startActivity(i);
            }
        });


        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, history);
        final AutoCompleteTextView textView = findViewById(R.id.search_view);
        textView.setAdapter(adapter);

        final Button searchBtn =findViewById(R.id.search_btn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchString = textView.getText().toString();
                if(!"".equals(searchString)) {
                    presenter.fetchResults(searchString);
                    history.add(searchString);
                    adapter.add(searchString);
                    adapter.notifyDataSetChanged();
                    textView.setText("");
                    venueList.clear();
                    listAdapter.notifyDataSetChanged();
                }
            }
        });

        listAdapter = new ListAdapter(this);

        RecyclerView listView = findViewById(R.id.lv);
        listView.setAdapter(listAdapter);
        listView.setLayoutManager(new LinearLayoutManager(this));


        presenter = new MainActivityPresenter(this);
        presenter.start();

    }

    @Override
    public void populateList(List<Venue> venues) {
        if(venues!=null && !venues.isEmpty()) {
            venueList = venues;
            listAdapter.setList(venues);
            listAdapter.notifyDataSetChanged();
            fab.setVisibility(View.VISIBLE);
        }
    }
}
