package com.myapplication.view;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.myapplication.MyApplication;
import com.myapplication.R;
import com.myapplication.models.Icon;
import com.myapplication.models.Venue;

public class DetailScreenActivity extends AppCompatActivity {

    public TextView nameView;
    public  TextView catView;
    public  TextView distView;
    public ImageView iconView;
    public ToggleButton favView;
    public TextView urlView;
    public String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Drawable d = new BitmapDrawable(getResources(), MyApplication.imageLoader.loadImageSync("https://maps.googleapis.com/maps/api/staticmap?center=40.714%2c%20-73.998&zoom=12&size=200x200&key=AIzaSyCDyXSGEpTyf_dAo3kOnbvWRVsP3aisggQ"));
        CollapsingToolbarLayout toolbarView = findViewById(R.id.toolbar_layout);
        toolbarView.setBackground(d);

        nameView = findViewById(R.id.name);
        catView =  findViewById(R.id.category);
        iconView = findViewById(R.id.icon);
        distView = findViewById(R.id.distance);
        favView =  findViewById(R.id.fav_btn);
        urlView = findViewById(R.id.url);
        urlView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(urlView.getText().toString()));
                startActivity(i);
            }
        });
        favView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApplication.addToFav(id);
            }
        });

        Intent i = getIntent();

        nameView.setText(i.getStringExtra("name"));
        catView.setText(i.getStringExtra("category"));
        MyApplication.imageLoader.displayImage(i.getStringExtra("icon_url"), iconView);
        distView.setText(String.valueOf(i.getFloatExtra("dist_from_seattle",0)+" kms from Seattle"));
        urlView.setText(i.getStringExtra("url"));
        favView.setChecked(MyApplication.isFav(i.getStringExtra("id")));


    }
}
