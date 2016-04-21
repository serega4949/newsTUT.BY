package com.example.newstutby.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.rssfeedtutby.MainActivity;
import com.example.rssfeedtutby.R;
import com.example.rssfeedtutby.TaskGetTextNew;
import com.example.rssfeedtutby.interfaces.DetailNewListener;
import com.squareup.picasso.Picasso;

public class DetailNew extends Fragment implements DetailNewListener {

    private String title;
    private String imageUrl;
    private String pubDate;
    private String urlNew;

    private ScrollView svComleteLoad;
    private LinearLayout llFailLoad;
    private TextView tvTitle;
    private TextView tvPubDate;
    private ImageView ivImage;
    private TextView tvText;
    private Button btnRestartLoad;

    public void setUrlNew(String urlNew) {
        this.urlNew = urlNew;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public static DetailNew newInstance(String title, String imageUrl, String pubDate, String urlNew) {
        DetailNew fragment = new DetailNew();
        fragment.setTitle(title);
        fragment.setImageUrl(imageUrl);
        fragment.setPubDate(pubDate);
        fragment.setUrlNew(urlNew);
        return fragment;
    }

    public DetailNew() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.title_detail_new));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_new, container, false);
        svComleteLoad = (ScrollView) view.findViewById(R.id.svCompleteLoad);
        llFailLoad = (LinearLayout) view.findViewById(R.id.llFailLoad);
        btnRestartLoad = (Button) view.findViewById(R.id.btnRestartLoad);
        tvTitle = (TextView) view.findViewById(R.id.titleDetailNew);
        tvPubDate = (TextView) view.findViewById(R.id.pubDateDetailNew);
        ivImage = (ImageView) view.findViewById(R.id.imageDetailNew);
        tvText = (TextView) view.findViewById(R.id.textDetailNew);
        new TaskGetTextNew(getActivity(), urlNew, this).execute();

        btnRestartLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TaskGetTextNew(getActivity(), urlNew, DetailNew.this).execute();
            }
        });

        return view;
    }

    @Override
    public void onComleteLoadData(String result) {
        llFailLoad.setVisibility(View.GONE);
        svComleteLoad.setVisibility(View.VISIBLE);
        tvTitle.setText(title);
        tvPubDate.setText(pubDate);
        Picasso.with(getActivity()).load(imageUrl).into(ivImage);
        tvText.setText(result);
    }

    @Override
    public void onFailLoadData() {
        llFailLoad.setVisibility(View.VISIBLE);
        svComleteLoad.setVisibility(View.GONE);
    }
}
