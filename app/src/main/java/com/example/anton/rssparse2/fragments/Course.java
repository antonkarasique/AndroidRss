package com.example.anton.rssparse2.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.anton.rssparse2.R;
import com.example.anton.rssparse2.fragments.JSONCourse.JSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Anton on 24.02.2016.
 */
public class Course extends Fragment {
    String result;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.course_fragment, container, false);

    }

    @Override
    public void onStart() {
        super.onStart();
        GetCourse getCourse = new GetCourse();

        try {
            getCourse.execute("https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=3");
            result = getCourse.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Gson gson = new GsonBuilder().create();
        JSON[] json = gson.fromJson(result, JSON[].class);

        TextView uah = (TextView) getActivity().findViewById(R.id.textViewUAH);
        TextView usd = (TextView) getActivity().findViewById(R.id.TextViewUSD);
        TextView eur = (TextView) getActivity().findViewById(R.id.textViewEUR);

        uah.setText("EUR=" + json[0].buy);
        usd.setText("RUR=" + json[1].buy);
        eur.setText("USD=" + json[2].buy);


    }

    class GetCourse extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            URL url = null;
            try {
                url = new URL(params[0]);

            StringBuilder sb = new StringBuilder();

            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(http.getInputStream()));
                char[] buf = new char[1000000];

                int r = 0;
                do {
                    if ((r = br.read(buf)) > 0)
                        sb.append(new String(buf, 0, r));
                } while (r > 0);
            } finally {
                http.disconnect();
            }
            return sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        }


}
