package com.example.newstutby.models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * Created by Сергей on 19.04.2016.
 */

@Root(name = "rss", strict = false)
public class RSSfeed {
    @Attribute(name = "version")
    private String version;
    @Element(name = "channel")
    private Channel channel;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}

