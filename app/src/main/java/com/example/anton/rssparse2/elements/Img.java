package com.example.anton.rssparse2.elements;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Anton on 17.02.2016.
 */
@Root(name = "image" , strict = false)
public class Img {

    @Element(name = "title")
    String title;

    @Element(name = "url")
    String url;

    public String getLink() {
        return link;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    @Element(name = "link")
    String link;
}
