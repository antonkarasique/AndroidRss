package com.example.anton.rssparse2.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anton.rssparse2.News;
import com.example.anton.rssparse2.R;
import com.example.anton.rssparse2.Sql;
import com.example.anton.rssparse2.elements.Item;
import com.squareup.picasso.Picasso;

import java.util.List;

//

public class NewsAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    List<Item> newsList;

    public NewsAdapter(Context context, List<Item> items) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        newsList = items;
    }

    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.news_adapter, parent, false);
        }

        TextView title = (TextView) view.findViewById(R.id.textViewTitle);
        TextView body = (TextView) view.findViewById(R.id.textViewDescription);
        TextView date = (TextView) view.findViewById(R.id.textViewPubDate);
        ImageView image = (ImageView) view.findViewById(R.id.imageView);

        Picasso.with(parent.getContext())
                .load(newsList.get(position).getEnclosure().getUrl())
                .resize(200, 150)
                .into(image);

        title.setText(newsList.get(position).getTitle());
        body.setText(newsList.get(position).getDescription());
        date.setText(newsList.get(position).getPubDate());


        return view;
    }

}
