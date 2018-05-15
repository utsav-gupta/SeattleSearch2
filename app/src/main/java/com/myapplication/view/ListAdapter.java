package com.myapplication.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.myapplication.MyApplication;
import com.myapplication.R;
import com.myapplication.models.Icon;
import com.myapplication.models.Venue;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    private final Activity parentAcivity;
    private ImageLoader imageLoader;
    private List<Venue> venueList = new ArrayList<Venue>();

    public ListAdapter(Activity activity){

        this.parentAcivity = activity;

    }

    class ListViewHolder extends RecyclerView.ViewHolder{


        public  TextView nameView;
        public  TextView catView;
        public  TextView distView;
        public ImageView iconView;
        public ToggleButton favView;
        public String id;
        public int position;


        public ListViewHolder(View itemView) {
            super(itemView);

            nameView = itemView.findViewById(R.id.name);
            catView = itemView.findViewById(R.id.category);
            iconView = itemView.findViewById(R.id.icon);
            distView = itemView.findViewById(R.id.distance);
            favView = itemView.findViewById(R.id.fav_btn);
            favView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MyApplication.addToFav(id);
                }
            });
            imageLoader = MyApplication.imageLoader;
        }

    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.venue_list_item,parent, false);
        final ListViewHolder lvh = new ListViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Venue selectedVenue = venueList.get(lvh.position);
                Intent i = new Intent(parentAcivity,DetailScreenActivity.class);
                i.putExtra("name", selectedVenue.getName());
                if(!selectedVenue.getCategories().isEmpty()) {
                    i.putExtra("icon_url", selectedVenue.getCategories().get(0).getIcon().getPrefix() + selectedVenue.getCategories().get(0).getIcon().getSuffix());
                    i.putExtra("category", selectedVenue.getCategories().get(0).getName());
                }
                i.putExtra("dist_from_seattle", selectedVenue.getDistFromSeattle());
                i.putExtra("url", selectedVenue.getUrl());
                i.putExtra("id", selectedVenue.getId());
                i.putExtra("latitude", String.valueOf(selectedVenue.getLocation().getLat()));
                i.putExtra("longitude", String.valueOf(selectedVenue.getLocation().getLng()));
                parentAcivity.startActivity(i);
            }
        });
        return lvh;
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        Venue venue = venueList.get(position);
        holder.nameView.setText(venue.getName());
        if(!venue.getCategories().isEmpty()) {
            holder.catView.setText(venue.getCategories().get(0).getName());
            Icon icon = venue.getCategories().get(0).getIcon();
            imageLoader.displayImage(icon.getPrefix() + icon.getSuffix(), holder.iconView);
        }
        holder.distView.setText(String.valueOf(venue.getDistFromSeattle())+" kms from Seattle");
        holder.favView.setChecked(MyApplication.isFav(venue.getId()));
        holder.id = venue.getId();
        holder.position=position;

    }

    @Override
    public int getItemCount() {
        return venueList.size();
    }

    public void setList(List<Venue> venues){
        this.venueList = venues;
    }

}
