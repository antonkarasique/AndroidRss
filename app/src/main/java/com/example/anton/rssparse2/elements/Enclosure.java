package com.example.anton.rssparse2.elements;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Created by Anton on 18.02.2016.
 */
@Root(name = "enclosure")
public class Enclosure {

    @Attribute
    String url;

    @Attribute
    String length;

    public Enclosure(String url, String length, String type) {
        this.url = url;
        this.length = length;
        this.type = type;
    }

    public Enclosure(){}

    @Attribute
    String type;

    public String getUrl() {
        return url;
    }

    public String getLength() {
        return length;
    }

    public String getType() {
        return type;
    }
}
