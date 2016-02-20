package com.example.anton.rssparse2.elements;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by Anton on 17.02.2016.
 */
@Root(name = "channel", strict = false)
public class Channel {

    @ElementList(name = "item", inline = true, required = false)
    List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    /*@Element
    String title;

    @Element
    String link;

    @Element
    String description;

    @Element
    String lastBuildDate;

    @Element
    String ttl;

    @Element
    Img image;

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public String getLastBuildDate() {
        return lastBuildDate;
    }

    public String getTtl() {
        return ttl;
    }

    public Img getImage() {
        return image;
    }*/




}
