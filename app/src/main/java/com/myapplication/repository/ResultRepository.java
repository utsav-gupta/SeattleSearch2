package com.myapplication.repository;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.myapplication.MyApplication;
import com.myapplication.contracts.MainActivityContract;
import com.myapplication.models.Data;
import com.myapplication.models.Venue;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.MessageFormat;

public class ResultRepository {

    private static final String API_ENDPOINT = "https://api.foursquare.com/v2/venues/search?near=\"Seattle,+WA\"&query=\"{0}\"&client_id=O4BA3BS351PB4TKH005JNL0DLKNJQQGL4VFUP421I0FTQVP5&client_secret=VBF2AJYVAHLGVRUD0JBCTXUSQLGA5WSU5NGVLCRDIQ0F04YV&v=20180401&limit=20" ;
    MainActivityContract.RepoListener repoListener;

    public ResultRepository(MainActivityContract.RepoListener repoListener) {

        this.repoListener = repoListener;
    }

    public void fetchResultsFromWeb(final String query){

        new AsyncTask<Void, Void, Void>() {
            private  String response = "";

            @Override
            protected Void doInBackground(Void... voids) {

                HttpURLConnection urlConnection = null;
                String temp;
                URL url = null;
                JSONObject object = null;
                InputStream inStream = null;
                try {
                    String urlEndpoint = MessageFormat.format(API_ENDPOINT,query);
                    url = new URL(urlEndpoint);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setDoOutput(true);
                    urlConnection.setDoInput(true);
                    urlConnection.connect();
                    inStream = urlConnection.getInputStream();
                    BufferedReader bReader = new BufferedReader(new InputStreamReader(inStream));

                    while ((temp = bReader.readLine()) != null) {
                        response += temp;
                    }
                    //object = (JSONObject) new JSONTokener(response).nextValue();
                } catch (Exception e) {

                } finally {
                    if (inStream != null) {
                        try {
                            // this will close the bReader as well
                            inStream.close();
                        } catch (IOException ignored) {
                        }
                    }
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }

                    return null;
                }
            }


            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Data data = new Gson().fromJson(response, Data.class);
                repoListener.onResponse(data.getResponse().getVenues());
            }
        }.execute();

    }

    public void fetchResultsfromStub() {

        String string = getDataFromStub();
        Data data = new Gson().fromJson(string, Data.class);
        repoListener.onResponse(data.getResponse().getVenues());

    }

    private String getDataFromStub() {

        String myJson = null;
        InputStream is = null;
        try {
            is = MyApplication.getContext().getAssets().open("response.json");
            int size = 0;

            size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            myJson = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return myJson;
    }
}
