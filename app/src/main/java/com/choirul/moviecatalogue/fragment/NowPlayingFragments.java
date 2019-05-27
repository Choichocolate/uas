package com.choirul.moviecatalogue.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.choirul.moviecatalogue.R;
import com.choirul.moviecatalogue.adapter.MovieAdapter;
import com.choirul.moviecatalogue.data.Movie;
import com.choirul.moviecatalogue.data.MovieResponse;
import com.choirul.moviecatalogue.network.GetDataService;
import com.choirul.moviecatalogue.network.RetrofitClient;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class NowPlayingFragments extends Fragment {

    private Context context;
    private RecyclerView recyclerView;
    private MovieAdapter adapter;

    private GetDataService service = RetrofitClient.getRetrofitClient().create(GetDataService.class);
    private Call<MovieResponse> getNowPlaying;
    private List<Movie> nowPlaying = Collections.emptyList();

    public NowPlayingFragments() {
        // Required empty public constructor
    }

    public void getNowPlaying(){
        getNowPlaying.clone().enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.body() == null){
                    nowPlaying = null;
                }else{
                    nowPlaying = response.body().getMovieResponse();
                }

                setUpRecycleView(nowPlaying);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.d("On Error", "error");
            }
        });
    }

    private void setUpRecycleView(List<Movie> nowPlaying) {
        adapter = new MovieAdapter(context, nowPlaying);

        recyclerView.setAdapter(adapter);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_now_playing_fragments, container, false);

        context = getActivity();
        recyclerView = view.findViewById(R.id.rv_now_playing);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 3));

        getNowPlaying = service.getNowPlaying();

        getNowPlaying();

        Log.d("Fragment", "now playing");

        return view;
    }

}
