package com.example.newstutby.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import java.util.ArrayList;

/**
 * Created by Сергей on 19.04.2016.
 */
public class Channel {
    @ElementList(inline = true, name = "item")
    private ArrayList<Item> channel;

    @Element(name = "lastBuildDate")
    private String lastBuildDate;

    public ArrayList<Item> getChannel() {
        return channel;
    }

    public void setChannel(ArrayList<Item> channel) {
        this.channel = channel;
    }

    public String getLastBuildDate() {
        return lastBuildDate;
    }

    public void setLastBuildDate(String lastBuildDate) {
        this.lastBuildDate = lastBuildDate;
    }
}
