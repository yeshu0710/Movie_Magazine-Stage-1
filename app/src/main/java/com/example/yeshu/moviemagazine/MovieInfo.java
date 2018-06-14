package com.example.yeshu.moviemagazine;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieInfo extends AppCompatActivity {

    String[] myMovieDetails;

    @BindView(R.id.collapsing_toolbar) CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.id_toolbar) Toolbar toolbar;
    @BindView(R.id.background_poster) ImageView background_photo;
    @BindView(R.id.poster_of_the_movie) ImageView poster;
    @BindView(R.id.movie_title_tv) TextView title;
    @BindView(R.id.overview_tv) TextView overview;
    @BindView(R.id.rating_tv) TextView rating;
    @BindView(R.id.release_date_tv) TextView release_date;
    @BindView(R.id.is_adult) TextView adult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        myMovieDetails=getIntent().getStringArrayExtra("details");

        Picasso.with(this).load("https://image.tmdb.org/t/p/w500/"+myMovieDetails[6]).into(background_photo);
        Picasso.with(this).load("https://image.tmdb.org/t/p/w500/"+myMovieDetails[3]).into(poster);

        title.setText(myMovieDetails[5]);

        rating.setText(myMovieDetails[9]);

        release_date.setText(myMovieDetails[8]);

        overview.setText(myMovieDetails[7]);

        Boolean s= Boolean.valueOf(myMovieDetails[12]);
        if(s){
            adult.setText(R.string.adult_18);
        }
        else {
            adult.setText(R.string.under_age);
        }

        collapsingToolbarLayout.setTitle(myMovieDetails[2]);
    }
}
