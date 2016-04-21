package com.example.newstutby.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rssfeedtutby.MainActivity;
import com.example.rssfeedtutby.R;
import com.example.rssfeedtutby.adapters.NewsAdapter;

public class ListNews extends android.support.v4.app.Fragment {

//    private OnFragmentInteractionListener mListener;
    private NewsAdapter newsAdapter;

    public void setNewsAdapter(NewsAdapter newsAdapter) {
        this.newsAdapter = newsAdapter;
    }

    public ListNews() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.title_list_news));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_news, null, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rvListNews);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(newsAdapter);
        return view;
    }


    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        public void onFragmentInteraction(Uri uri);
//    }

}
