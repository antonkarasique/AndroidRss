package com.example.anton.rssparse2;


import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.anton.rssparse2.elements.Item;
import com.example.anton.rssparse2.fragments.Course;

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
    private Course course;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

//Menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
//Кнопка refresh в меню которая будет обновлять ленту добавляя новые новости в БД
            case R.id.refresh:

                //парсим RSS
                getPage = new GetPage();
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


 //Кнопка показать/спрятать фрагмент с курсом валют
            case R.id.hideCourse:
                if (item.getTitle().equals("Hide course")){
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.remove(course);
                    transaction.commit();
                    item.setTitle("Show course");
                } else {
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.add(R.id.course_fragment, course);
                    transaction.commit();
                    item.setTitle("Hide course");

                }

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Динамически добавляем фрагмент
        course = new Course();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.course_fragment, course);
        transaction.commit();



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
