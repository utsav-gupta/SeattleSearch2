package com.myapplication.view;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import java.text.MessageFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.myapplication.MyApplication;
import com.myapplication.R;

public class DetailScreenActivity extends AppCompatActivity {

    public TextView nameView;
    public  TextView catView;
    public  TextView distView;
    public ImageView iconView;
    public ToggleButton favView;
    public TextView urlView;
    public String id;
    String staticMapUrl = "https://maps.googleapis.com/maps/api/staticmap?center={0}&zoom=12&size=200x200&key=AIzaSyCDyXSGEpTyf_dAo3kOnbvWRVsP3aisggQ&markers={1}&markers={2}";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


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
        String marker1 = "label:Seattle%7C" +MyApplication.SEATTLE_LOC.getLatitude()+","+MyApplication.SEATTLE_LOC.getLongitude();
        String marker2 = "label:"+i.getStringExtra("name").charAt(0)+"%7C" +i.getStringExtra("latitude")+","+i.getStringExtra("longitude");
        final String finalMapUrl = MessageFormat.format(staticMapUrl, MyApplication.SEATTLE_LOC.getLatitude()+"%2c%20"+MyApplication.SEATTLE_LOC.getLongitude(),marker1,marker2);

        new AsyncTask(){
            Drawable d=null;
            @Override
            protected Void doInBackground(Object... objects) {
                d = new BitmapDrawable(getResources(), MyApplication.imageLoader.loadImageSync(finalMapUrl));

                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                CollapsingToolbarLayout toolbarView = findViewById(R.id.toolbar_layout);
                if(d!=null)
                toolbarView.setBackground(d);
            }
        }.execute();

    }
}
