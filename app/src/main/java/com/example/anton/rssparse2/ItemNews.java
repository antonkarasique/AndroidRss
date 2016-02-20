package com.example.anton.rssparse2;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

//парсим новость со страницы и выводим текст в новом Activity
public class ItemNews extends AppCompatActivity {

    Document document;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

//передаем URL в AsyncTask и получаем документ с новостью
        GetNews getNews = new GetNews();
        getNews.execute(getIntent().getStringExtra("url"));

        try {
          document = getNews.get(); // получаем jsoup.nodes.Document с новостью

        } catch (Exception e) {
            e.printStackTrace();
        }
//получаем первый класс с именем text в котором содержится тело новости
        Element newsText = document.getElementsByClass("text").first();

//передаем в TextView  title и саму новость
        TextView newsTitle = (TextView) findViewById(R.id.textViewTitleNews);
        newsTitle.setText(document.title());

        TextView mainBody = (TextView) findViewById(R.id.textViewNewsText);
        mainBody.setText(newsText.text());

    }
}

class GetNews extends AsyncTask<String, String, Document> {

    @Override
    protected Document doInBackground(String... url) {
        try {

            String pageURL = new String(url[0]);
            Document doc = Jsoup.connect(pageURL).get();
            return doc;

        } catch (Exception e) {
        }
        return null;
    }
}
