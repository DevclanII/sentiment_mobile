package com.example.musa.twisent;

import android.content.Context;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;


import com.example.musa.twisent.twisent.Example;
import com.example.musa.twisent.twisent.Sentiment;
import com.example.musa.twisent.twisent.Tweettime;

import java.util.ArrayList;
import java.util.List;


public class SentimentViewAdapter extends RecyclerView.Adapter<SentimentViewAdapter.ViewHolder>  {

    public static Example mitems;
    private Context mcontext;


    public SentimentViewAdapter(Context mcontext) {
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tweet_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        new Sentiment().getAll();
        new Tweettime().getAll();
            holder.msentimentView.setText(mitems.getSentiment().Sents.get(position));
            holder.mDateView.setText(mitems.getTweettime().Sents.get(position));

        Log.d("Dick", "onBindViewHolder: "+mitems.getTweettime().Sents.get(1));
    }

    public static Example getMitems() {
        return mitems;
    }

    public void setMitems(Example mitems) {
        SentimentViewAdapter.mitems = mitems;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mitems != null) {
            return new Sentiment().Sents.size();
        } else
            return 0;
    }





    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView msentimentView;
        private TextView mDateView;



        private ViewHolder(View itemView) {
            super(itemView);
            msentimentView = itemView.findViewById(R.id.Sentiment);
            mDateView = itemView.findViewById(R.id.date);

        }
    }
}
