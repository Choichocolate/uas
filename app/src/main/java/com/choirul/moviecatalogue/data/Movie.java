package com.choirul.moviecatalogue.data;

import com.google.gson.annotations.SerializedName;

public class Movie {

    @SerializedName("id")
    private String movieId;

    @SerializedName("title")
    private String movieTitle;

    @SerializedName("overview")
    private String movieOverview;

    @SerializedName("release_date")
    private String movieReleaseDate;

    @SerializedName("poster_path")
    private String movieImage;

    @SerializedName("backdrop_path")
    private String movieBackdrop;

    @SerializedName("vote_average")
    private String movieRating;

    public Movie(){
    }

    public Movie(String movieId, String movieTitle, String movieOverview, String movieReleaseDate, String movieImage, String movieBackdrop, String movieRating) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.movieOverview = movieOverview;
        this.movieReleaseDate = movieReleaseDate;
        this.movieImage = movieImage;
        this.movieBackdrop = movieBackdrop;
        this.movieRating = movieRating;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public void setMovieOverview(String movieOverview) {
        this.movieOverview = movieOverview;
    }

    public String getMovieReleaseDate() {
        return movieReleaseDate;
    }

    public void setMovieReleaseDate(String movieReleaseDate) {
        this.movieReleaseDate = movieReleaseDate;
    }

    public String getMovieImage() {
        return movieImage;
    }

    public void setMovieImage(String movieImage) {
        this.movieImage = movieImage;
    }

    public String getMovieBackdrop() {
        return movieBackdrop;
    }

    public void setMovieBackdrop(String movieBackdrop) {
        this.movieBackdrop = movieBackdrop;
    }

    public String getMovieRating() {
        return movieRating;
    }

    public void setMovieRating(String movieRating) {
        this.movieRating = movieRating;
    }
}
