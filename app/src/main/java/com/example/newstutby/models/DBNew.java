package com.example.newstutby.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Сергей on 19.04.2016.
 */
@DatabaseTable(tableName = "News")
public class DBNew {

    public DBNew() {
    }

    public DBNew(String title, String datePublication, String iconUrl, String imageUrl, String textNew, String link) {
        this.title = title;
        this.datePublication = datePublication;
        this.iconUrl = iconUrl;
        this.imageUrl = imageUrl;
        this.textNew = textNew;
        this.link = link;
    }

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String title;

    @DatabaseField
    private String datePublication;

    @DatabaseField
    private String iconUrl;

    @DatabaseField
    private String imageUrl;

    @DatabaseField
    private String textNew;

    @DatabaseField String link;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDatePublication() {
        return datePublication;
    }

    public void setDatePublication(String datePublication) {
        this.datePublication = datePublication;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTextNew() {
        return textNew;
    }

    public void setTextNew(String textNew) {
        this.textNew = textNew;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DBNew dbNew = (DBNew) o;

        if (title != null ? !title.equals(dbNew.title) : dbNew.title != null) return false;
        if (datePublication != null ? !datePublication.equals(dbNew.datePublication) : dbNew.datePublication != null)
            return false;
        if (iconUrl != null ? !iconUrl.equals(dbNew.iconUrl) : dbNew.iconUrl != null) return false;
        if (imageUrl != null ? !imageUrl.equals(dbNew.imageUrl) : dbNew.imageUrl != null)
            return false;
        if (textNew != null ? !textNew.equals(dbNew.textNew) : dbNew.textNew != null) return false;
        return !(link != null ? !link.equals(dbNew.link) : dbNew.link != null);

    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (datePublication != null ? datePublication.hashCode() : 0);
        result = 31 * result + (iconUrl != null ? iconUrl.hashCode() : 0);
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        result = 31 * result + (textNew != null ? textNew.hashCode() : 0);
        result = 31 * result + (link != null ? link.hashCode() : 0);
        return result;
    }
}
