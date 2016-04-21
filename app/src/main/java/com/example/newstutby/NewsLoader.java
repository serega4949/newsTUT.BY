package com.example.newstutby;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.example.rssfeedtutby.DB.DBHelper;
import com.example.rssfeedtutby.models.DBNew;
import com.example.rssfeedtutby.models.Enclosure;
import com.example.rssfeedtutby.models.Item;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Сергей on 19.04.2016.
 */

public class NewsLoader extends AsyncTaskLoader<List<DBNew>> {
    private Context context;
    private ArrayList<Item> items;

    public NewsLoader(Context context) {
        super(context);
        this.context = context;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    @Override
    public List<DBNew> loadInBackground() {
        Log.d(Constants.MYLOG, "loadInBackground");
        DBHelper dbHelper = new DBHelper(context);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm");
        try {
            Dao<DBNew, Integer> dao = dbHelper.getDao();
            List<DBNew> dbNews = dao.queryForAll();
            dao.delete(dbNews);
            for (Item item : items) {
                dao.create(new DBNew(
                        item.getTitle(),
                        dateFormat.format(new Date(item.getPubDate())),
                        getUrlIcon(item.getDescription()),
                        getUtlImage(item.getEnclosure()),
                        "text",
                        item.getLink()
                ));
            }

            return dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            dbHelper.close();
            Log.d(Constants.MYLOG, "DB is closed");
        }
    }

    private String getUrlIcon(String data) {
        Document doc = Jsoup.parse(data);
        Element link = doc.select("img").first();
        if (link != null) {
            String iconUrl = link.attr("src");
            return iconUrl;
        } else {
            return "";
        }
    }

    private String getUtlImage(Enclosure enclosure) {
        if (enclosure.getType().equals("image/jpeg")) {
            return enclosure.getUrlImage();
        }
        return "";
    }
}
