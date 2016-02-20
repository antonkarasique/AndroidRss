package com.example.anton.rssparse2.elements;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Created by Anton on 18.02.2016.
 */
@Root(name = "enclosure", strict = false)
public class Enclosure {

    @Attribute
    String url;

    public Enclosure(String url) {
        this.url = url;
        /*this.length = length;
        this.type = type;*/
    }

    public Enclosure(){}


    public String getUrl() {
        return url;
    }

   /* @Attribute
    String length;

    @Attribute
    String type;*/

   /* public String getLength() {
        return length;
    }

    public String getType() {
        return type;
    }*/
}
