package com.example.anton.rssparse2;

import com.example.anton.rssparse2.elements.Channel;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Anton on 17.02.2016.
 */

@Root(name = "rss")
public class News {

    @Attribute
    public String version;

    @Element
    public Channel channel;
}

