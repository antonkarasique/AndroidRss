package com.example.anton.rssparse2.elements;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Anton on 17.02.2016.
 */
@Root(name = "image")
public class Img {

    @Element
    String title;

    @Element
    String url;

    @Element
    String link;

    public String getLink() {
        return link;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }


}
