package com.example.newstutby;

import android.content.Context;
import android.os.AsyncTask;

import com.example.rssfeedtutby.fragments.dialogs.WaitingDialog;
import com.example.rssfeedtutby.interfaces.DetailNewListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by Сергей on 21.04.2016.
 */
public class TaskGetTextNew extends AsyncTask<Void, Void, String> {

    private Context context;
    private String urlNew;
    private DetailNewListener detailNewListener;
    private WaitingDialog progressDialog;

    public TaskGetTextNew(Context context, String urlNew, DetailNewListener detailNewListener) {
        this.context = context;
        this.urlNew = urlNew;
        this.detailNewListener = detailNewListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new WaitingDialog();
        progressDialog.setCancelable(false);
        progressDialog.show(((MainActivity)context).getSupportFragmentManager(), "progressDialog");
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            String textNew = parseTextNew();
            return textNew;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if(!result.isEmpty()) {
            detailNewListener.onComleteLoadData(result);
        } else {
            detailNewListener.onFailLoadData();
        }
        progressDialog.dismiss();
    }

    private String parseTextNew() throws IOException {
        String textNew = "";
        Document document = Jsoup.connect(urlNew).get();
        Element div = document.select("div#article_body").first();
        Elements elements = div.getElementsByTag("p");
        for (Element element : elements) {
            if(!element.text().isEmpty()) {
                textNew += element.text();
                textNew += "\n\n";
            }
        }
        return textNew;
    }
}
