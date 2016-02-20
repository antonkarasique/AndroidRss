package com.example.anton.rssparse2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.anton.rssparse2.adapter.NewsAdapter;
import com.example.anton.rssparse2.elements.Enclosure;
import com.example.anton.rssparse2.elements.Item;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GetPage getPage;
    private News news;
    private Sql sql;
    private SQLiteDatabase SQLDb;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //Кнопка refresh в меню которая будет обновлять ленту добавляя новые новости в БД
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.refresh:

                //парсим RSS
                Serializer serializer = new Persister();
                getPage.execute("http://news.liga.net/all/rss.xml"); //получаем rss.xml через AsyncTask

                try {
                    news = serializer.read(News.class, getPage.get()); //десереализируем
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //Заносим все новости в List
               List<Item> items = new ArrayList<>();
                for (Item i : news.channel.getItems()) {
                    items.add(0, i);

                }

                //Добавляем в БД title, описание, дату, ссылку на новость и ссылку на картинку из List<Item> items

                for (Item i: items){
                        sql = new Sql(this, "rss.db", null, 1);
                        SQLDb = sql.getWritableDatabase();

                        ContentValues newValues = new ContentValues();
                        newValues.put(Sql.TITLE_COLUMN, i.getTitle());
                        newValues.put(Sql.DESC_COLUMN, i.getDescription());
                        newValues.put(Sql.IMAGE_COLUMN, i.getEnclosure().getUrl());
                        newValues.put(Sql.LINK_COLUMN, i.getLink());
                        newValues.put(Sql.DATE_COLUMN, i.getPubDate());

                        //вставляем полученные данные в таблицу news

                        SQLDb.insert("news", null, newValues);
                        SQLDb.close();
                }

                //Рефрешим наш активити с новыми новостями
                Intent intent = getIntent();
                finish();
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getPage = new GetPage();
        news = new News();

//тут будут новости вытаскиваемые из БД
        final List<Item> items = new ArrayList<>();

        sql = new Sql(this, "rss.db", null, 1);
        SQLDb = sql.getWritableDatabase();

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
            NewsAdapter na = new NewsAdapter(getApplicationContext(), items);
            ListView listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(na);

//Открываем новость по клику в списке
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Item item = items.get(position);
                    String url = item.getLink();
                    Intent intent = new Intent(getApplicationContext(), ItemNews.class);
                    intent.putExtra("url", url);
                    startActivity(intent);
                }
            });
        }

    }
}

class GetPage extends AsyncTask<String, String, String> {
    @Override
    protected String doInBackground(String... url) {
        StringBuilder sb = new StringBuilder();
        try {
            URL pageURL = new URL(url[0]);
            String inputLine;
            URLConnection uc = pageURL.openConnection();
            BufferedReader buff = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            while ((inputLine = buff.readLine()) != null) {
                sb.append(inputLine);
            }
            buff.close();
        }
        catch (Exception e) {
        }
        return sb.toString();
    }
}
