package com.example.anton.rssparse2.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.anton.rssparse2.ItemNews;
import com.example.anton.rssparse2.R;
import com.example.anton.rssparse2.Sql;
import com.example.anton.rssparse2.adapter.NewsAdapter;
import com.example.anton.rssparse2.elements.Enclosure;
import com.example.anton.rssparse2.elements.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anton on 24.02.2016.
 */
public class News extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.news_fragment, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();


//тут будут новости вытаскиваемые из БД
        final List<Item> items = new ArrayList<>();

        Sql sql = new Sql(getActivity().getApplicationContext(), "rss.db", null, 1);
        SQLiteDatabase SQLDb = sql.getWritableDatabase();

//Вытаскиваем записи из БД в обратном порядке(с конца)
        String query = "SELECT * FROM news ORDER BY "+ Sql._ID +" DESC";
        Cursor cursor = SQLDb.rawQuery(query, null); /*=SQLDb.query("news", new String[]{ Sql.LINK_COLUMN, Sql.DESC_COLUMN, Sql.IMAGE_COLUMN, Sql.DATE_COLUMN, Sql.TITLE_COLUMN},
                                                                     null, null, null, null, null);*/

        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndex(Sql.TITLE_COLUMN));
            String desc = cursor.getString(cursor.getColumnIndex(Sql.DESC_COLUMN));
            String date = cursor.getString(cursor.getColumnIndex(Sql.DATE_COLUMN));
            String image = cursor.getString(cursor.getColumnIndex(Sql.IMAGE_COLUMN));
            String link = cursor.getString(cursor.getColumnIndex(Sql.LINK_COLUMN));
            Enclosure enclosure = new Enclosure(image);
            Item item = new Item(title, link, desc, enclosure, date);
            items.add(item);
        }
        cursor.close();

//передаем массив новостей в адаптер
        if (items != null) {
            NewsAdapter na = new NewsAdapter(getActivity().getApplicationContext(), items);
            ListView listView = (ListView) getActivity().findViewById(R.id.listView);
            listView.setAdapter(na);

//Открываем новость по клику в списке
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Item item = items.get(position);
                    String url = item.getLink();
                    Intent intent = new Intent(getActivity().getApplicationContext(), ItemNews.class);
                    intent.putExtra("url", url);
                    startActivity(intent);
                }
            });
        }

    }
}

