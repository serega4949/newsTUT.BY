package com.example.newstutby.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rssfeedtutby.MainActivity;
import com.example.rssfeedtutby.R;
import com.example.rssfeedtutby.fragments.DetailNew;
import com.example.rssfeedtutby.models.DBNew;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Сергей on 19.04.2016.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private Context context;
    private List<DBNew> dbNews;

    public NewsAdapter(Context context, List<DBNew> dbNews) {
        this.context = context;
        this.dbNews = dbNews;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public CardView cvItem;
        public ImageView ivIcon;
        public TextView tvTitle;
        public TextView tvPubDate;
        public ViewHolder(View itemViem) {
            super(itemViem);
            cvItem = (CardView) itemViem.findViewById(R.id.cvItem);
            ivIcon = (ImageView) itemViem.findViewById(R.id.iconNew);
            tvTitle = (TextView) itemViem.findViewById(R.id.titleNew);
            tvPubDate = (TextView) itemViem.findViewById(R.id.pubDateNew);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_new, null, false);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final DBNew currentItem = dbNews.get(position);
        holder.tvTitle.setText(currentItem.getTitle());
        if(!dbNews.get(position).equals("")) {
            try {
                Picasso.with(context).load(currentItem.getIconUrl()).into(holder.ivIcon);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            holder.ivIcon.setVisibility(View.GONE);
        }
        holder.tvPubDate.setText(currentItem.getDatePublication());
        holder.cvItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) context).replaceFragment(DetailNew.newInstance(
                        currentItem.getTitle(),
                        currentItem.getImageUrl(),
                        currentItem.getDatePublication(),
                        currentItem.getLink()
                ));
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return dbNews.size();
    }
}
