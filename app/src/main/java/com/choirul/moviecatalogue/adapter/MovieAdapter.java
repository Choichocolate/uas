package com.choirul.moviecatalogue.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.choirul.moviecatalogue.R;
import com.choirul.moviecatalogue.activity.DetailMovieActivity;
import com.choirul.moviecatalogue.data.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private Context context;
    private List<Movie> movies;
    private final String BASE_POSTER_URL = "http://image.tmdb.org/t/p/w500";

    public MovieAdapter(Context context, List<Movie> movies){
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.item_movie_list, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.ViewHolder viewHolder, int i) {
        final Movie movie = movies.get(i);

        viewHolder.movieTitle.setText(movie.getMovieTitle());
        Log.d("movie image" , movie.getMovieImage());

        Glide.with(context)
                .load(BASE_POSTER_URL + movie.getMovieImage())
                .into(viewHolder.movieImage);

        viewHolder.movieRating.setText(movie.getMovieRating());

        viewHolder.movieCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailMovieActivity.class);
                intent.putExtra("ID",movie.getMovieId());
                intent.putExtra("TITLE" , movie.getMovieTitle());
                intent.putExtra("DESCRIPTION" , movie.getMovieOverview());
                intent.putExtra("BACKDROP" , movie.getMovieBackdrop());
                intent.putExtra("MOVIE_IMAGE" , movie.getMovieImage());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView movieCardView;
        private ImageView movieImage;
        private TextView movieTitle;
        private TextView movieRating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            movieCardView = itemView.findViewById(R.id.movie_card_view);
            movieImage = itemView.findViewById(R.id.movie_image);
            movieTitle = itemView.findViewById(R.id.movie_title);
            movieRating = itemView.findViewById(R.id.movie_rating);
        }
    }
}
