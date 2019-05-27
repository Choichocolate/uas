package com.choirul.moviecatalogue.network;

import com.choirul.moviecatalogue.BuildConfig;
import com.choirul.moviecatalogue.data.MovieResponse;
import com.choirul.moviecatalogue.data.RelatedResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetDataService {

    @GET("/3/movie/now_playing?api_key=" + BuildConfig.TMDB_API_KEY + "&language=en-US&page=1")
    Call<MovieResponse> getNowPlaying();

    @GET("/3/movie/upcoming?api_key=" + BuildConfig.TMDB_API_KEY + "&language=en-US&page=1")
    Call<MovieResponse> getUpcoming();

    @GET("/3/movie/{id}/similar?api_key=d05a67fa25786531d1adc0c469147aa1&language=en-US&page=1")
    Call<RelatedResponse> getRelated(@Path("id") String id);


}
