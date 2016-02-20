package com.example.anton.rssparse2.elements;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Anton on 17.02.2016.
 */
@Root(name = "item")
public class Item {

    @Element
    String title;

    @Element
    String link;

    @Element
    String description;

    @Element
    Enclosure enclosure;

    public Item(String title, String link, String description, Enclosure enclosure, String pubDate) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.enclosure = enclosure;
        this.pubDate = pubDate;
    }

    public Item(){}

    public String getPubDate() {
        return pubDate;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public Enclosure getEnclosure() {
        return enclosure;
    }

    @Element(name = "pubDate")
    String pubDate;



}
