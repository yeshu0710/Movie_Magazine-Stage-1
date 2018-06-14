package com.example.yeshu.moviemagazine;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;


/*
 * Created by Yeshu on 23-05-2018.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private Context c;
    private ArrayList<JsonDataClass> jsonData;


    MovieAdapter(Context c, ArrayList<JsonDataClass> jsonData) {
        this.c = c;
        this.jsonData = jsonData;
    }

    private String[] jsonDetails=new String[50];

    @NonNull
    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.movie_view,parent,false);
        return new MovieViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieViewHolder holder, int position) {
        URL url=Home.HttpResponse.bulidImgUrl(jsonData.get(position).getPoster_path());
        Picasso.with(c).load(url.toString()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return jsonData.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        MovieViewHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.movie_poster);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getLayoutPosition();
                    jsonDetails[0]= String.valueOf(jsonData.get(position).getVote_count());
                    jsonDetails[1]=jsonData.get(position).getId();
                    jsonDetails[2]=jsonData.get(position).getTitle();
                    jsonDetails[3]=jsonData.get(position).getPoster_path();
                    jsonDetails[4]=jsonData.get(position).getOriginal_language();
                    jsonDetails[5]=jsonData.get(position).getOriginal_title();
                    jsonDetails[6]=jsonData.get(position).getBackdrop_path();
                    jsonDetails[7]=jsonData.get(position).getOverview();
                    jsonDetails[8]=jsonData.get(position).getRelease_date();
                    jsonDetails[9]= String.valueOf(jsonData.get(position).getVote_average());
                    jsonDetails[10]= String.valueOf(jsonData.get(position).getPopularity());
                    jsonDetails[11]= String.valueOf(jsonData.get(position).isVideo());
                    jsonDetails[12]= String.valueOf(jsonData.get(position).isAdult());

                    Intent i=new Intent(c,MovieInfo.class);
                    i.putExtra("details",jsonDetails);
                    view.getContext().startActivity(i);
                }
            });
        }
    }
}
