package com.example.newstutby;

import com.example.rssfeedtutby.models.RSSfeed;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Сергей on 19.04.2016.
 */
public interface TutByService {
    @GET("rss/all.rss")
    Call<RSSfeed> getAllLastNews();
}
