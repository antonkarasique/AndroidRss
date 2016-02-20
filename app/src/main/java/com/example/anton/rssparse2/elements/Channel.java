package com.example.anton.rssparse2.elements;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by Anton on 17.02.2016.
 */
@Root(name = "channel")
public class Channel {

    @Element(name = "title")
    String title;

    @Element(name = "link")
    String link;

    @Element(name = "description")
    String description;

    @Element(name = "lastBuildDate")
    String lastBuildDate;

    @Element(name = "ttl")
    String ttl;

    @Element(name = "image")
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
    }

    public List<Item> getItems() {
        return items;
    }

    @ElementList(name = "item", inline = true, required = false)//inline = true
    List<Item> items;
}
