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
import com.choirul.moviecatalogue.data.Result;

import java.util.List;

public class RelatedAdapter extends RecyclerView.Adapter<RelatedAdapter.ViewHolder> {
    private Context context;
    private List<Result> resultList;
    private final String BASE_POSTER_URL = "http://image.tmdb.org/t/p/w500";

    public RelatedAdapter(Context context, List<Result> resultList) {
        this.context = context;
        this.resultList = resultList;
    }

    public void setResultList(List<Result> resultList) {
        this.resultList = resultList;
    }

    @NonNull
    @Override
    public RelatedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_movie_list, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RelatedAdapter.ViewHolder viewHolder, int i) {
        final Result movie = resultList.get(i);

        viewHolder.movieTitle.setText(movie.getTitle());
        Log.d("movie image" , movie.getBackdropPath());

        Glide.with(context)
                .load(BASE_POSTER_URL + movie.getPosterPath())
                .into(viewHolder.movieImage);

        viewHolder.movieRating.setText(Double.toString(movie.getVoteAverage()));

        viewHolder.movieCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailMovieActivity.class);
                intent.putExtra("ID",Integer.toString(movie.getId()));
                intent.putExtra("TITLE" , movie.getTitle());
                intent.putExtra("DESCRIPTION" , movie.getOverview());
                intent.putExtra("BACKDROP" , movie.getBackdropPath());
                intent.putExtra("MOVIE_IMAGE" , movie.getPosterPath());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return (resultList==null) ? 0 : resultList.size();
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
