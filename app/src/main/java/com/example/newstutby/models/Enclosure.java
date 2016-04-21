package com.example.newstutby.models;

import org.simpleframework.xml.Attribute;

/**
 * Created by Сергей on 19.04.2016.
 */
public class Enclosure {
    @Attribute(name = "url")
    private String urlImage;

    @Attribute(name = "type")
    private String type;

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
