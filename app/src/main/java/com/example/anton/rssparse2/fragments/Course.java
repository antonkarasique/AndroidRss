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
import java.net.URL;
import java.net.URLConnection;

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

        uah.setText(json[0].ccy+"=" + json[0].buy);
        usd.setText(json[1].ccy+"=" + json[1].buy);
        eur.setText(json[2].ccy+"=" + json[2].buy);

    }

    class GetCourse extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            StringBuilder sb = new StringBuilder();
            try {
                URL pageURL = new URL(params[0]);
                String inputLine;
                URLConnection uc = pageURL.openConnection();
                BufferedReader buff = new BufferedReader(new InputStreamReader(uc.getInputStream()));
                while ((inputLine = buff.readLine()) != null) {
                    sb.append(inputLine);
                }
                buff.close();
            } catch (Exception e) {
            }
            return sb.toString();
        }
    }
}
