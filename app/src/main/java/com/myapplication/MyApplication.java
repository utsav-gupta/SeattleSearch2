package com.myapplication;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MyApplication extends Application {

    private static Context mContext ;
    public  static android.location.Location SEATTLE_LOC = new android.location.Location("seattle");
    private static Set<String> favList;
    public static  ImageLoader imageLoader;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        favList = mContext.getSharedPreferences("com.myapplication",MODE_PRIVATE).getStringSet("fav_list", null);
        SEATTLE_LOC.setLatitude(47.6062);
        SEATTLE_LOC.setLongitude(-122.3321);
        imageLoader= ImageLoader.getInstance();

        final DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnLoading(android.R.drawable.stat_sys_download)
                .showImageForEmptyUri(android.R.drawable.ic_dialog_alert)
                .showImageOnFail(android.R.drawable.stat_notify_error)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED) //filled width
                .build();

        final ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(getApplicationContext())
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);

        imageLoader.init(config);

    }

    public static Context getContext(){
        return mContext;
    }


    public static void addToFav(String id){

        if(favList==null)
            favList=new HashSet<String>();

        favList.add(id);
        mContext.getSharedPreferences("com.myapplication",MODE_PRIVATE).edit().putStringSet("fav_list", favList).commit();
    }

    public static boolean isFav(String id){
        if(favList==null)
            return false;
        else
            return favList.contains(id);
    }

}