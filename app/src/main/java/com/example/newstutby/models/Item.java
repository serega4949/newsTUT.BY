package com.example.newstutby.models;

import org.simpleframework.xml.Element;

/**
 * Created by Сергей on 19.04.2016.
 */
public class Item {
    @Element(name = "title")
    private String title;
    @Element(name = "pubDate")
    private String pubDate;
    @Element(name = "enclosure")
    private Enclosure enclosure;
    //надо иконку
    @Element(name = "guid")
    private String urlDetail;

    @Element(name = "description")
    private String description;

    @Element(name = "link")
    private String link;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public Enclosure getEnclosure() {
        return enclosure;
    }

    public void setEnclosure(Enclosure enclosure) {
        this.enclosure = enclosure;
    }

    public String getUrlDetail() {
        return urlDetail;
    }

    public void setUrlDetail(String urlDetail) {
        this.urlDetail = urlDetail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
