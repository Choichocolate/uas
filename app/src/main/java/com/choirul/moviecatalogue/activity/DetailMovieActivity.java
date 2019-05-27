package com.choirul.moviecatalogue.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.choirul.moviecatalogue.R;
import com.choirul.moviecatalogue.adapter.RelatedAdapter;
import com.choirul.moviecatalogue.data.Movie;
import com.choirul.moviecatalogue.data.MovieResponse;
import com.choirul.moviecatalogue.data.RelatedResponse;
import com.choirul.moviecatalogue.data.Result;
import com.choirul.moviecatalogue.database.FavoriteMovieContract;
import com.choirul.moviecatalogue.database.FavoriteMovieDBHelper;
import com.choirul.moviecatalogue.network.GetDataService;
import com.choirul.moviecatalogue.network.RetrofitClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailMovieActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RelatedAdapter adapter;
    List<Result> resultList = Collections.emptyList();
    private Call<RelatedResponse> getUpComing;

    private GetDataService service = RetrofitClient.getRetrofitClient().create(GetDataService.class);

    private boolean isFavorite = false;
    private final String BASE_POSTER_URL = "http://image.tmdb.org/t/p/w500";

    TextView texrTitle;
    TextView textDesciption;
    ImageView imageBackdrop;
    ImageView imageMovieImage;
    ImageView favorite;

    String id = "";
    String title = "";
    String description = "";
    String movieImage = "";
    String backdrop = "";

    ConstraintLayout constraintLayout;

    FavoriteMovieDBHelper dbHelper = new FavoriteMovieDBHelper(this);


    public void getUpComing(){
        getUpComing.clone().enqueue(new Callback<RelatedResponse>() {
            @Override
            public void onResponse(Call<RelatedResponse> call, Response<RelatedResponse> response) {
                if (response.body() == null){
                    Toast.makeText(DetailMovieActivity.this,id,Toast.LENGTH_LONG).show();
                    resultList = null;
                }else {
                    resultList = response.body().getResults();
                }
                setUpRecycleView(resultList);
            }

            @Override
            public void onFailure(Call<RelatedResponse> call, Throwable t) {
//
//                Toast.makeText(DetailMovieActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_movie);

        Intent intent = getIntent();

        title = intent.getStringExtra("TITLE");
        description = intent.getStringExtra("DESCRIPTION");
        movieImage = intent.getStringExtra("MOVIE_IMAGE");
        backdrop = intent.getStringExtra("BACKDROP");
        id = intent.getStringExtra("ID");



        texrTitle = findViewById(R.id.text_title);
        textDesciption = findViewById(R.id.text_description);
        imageBackdrop = findViewById(R.id.image_movie_detail);
        imageMovieImage = findViewById(R.id.image_movie);
        constraintLayout = findViewById(R.id.layout_detail);
        recyclerView = findViewById(R.id.recyclerView2);

        setUpRecycleView(resultList);


        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);



        recyclerView.setLayoutManager(layoutManager);



        texrTitle.setText(title);
        textDesciption.setText(description);
        Glide.with(this)
                .load(BASE_POSTER_URL + backdrop)
                .into(imageBackdrop);
        Glide.with(this)
                .load(BASE_POSTER_URL + movieImage)
                .into(imageMovieImage);

        favorite = findViewById(R.id.button_favorite);
        favoriteState();
        setFavorite();
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFavorite) {
                    deleteDataFromDatabase();
                } else {
                    inserToDatabase();
                }
                isFavorite = !isFavorite;
                setFavorite();
            }
        });

        getUpComing = service.getRelated(id);
        getUpComing();
    }

    private void setUpRecycleView(List<Result> Related) {



        adapter = new RelatedAdapter(this, Related);

        recyclerView.setAdapter(adapter);
    }


    public void inserToDatabase() {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();



        values.put(FavoriteMovieContract.FavoriteMovie.COLOUMN_MOVIE_ID , id);
        values.put(FavoriteMovieContract.FavoriteMovie.COLOUMN_MOVIE_TITLE, title);
        values.put(FavoriteMovieContract.FavoriteMovie.COLOUMN_MOVIE_DESCRIPTION, description);
        values.put(FavoriteMovieContract.FavoriteMovie.COLOUMN_MOVIE_IMAGE, movieImage);
        values.put(FavoriteMovieContract.FavoriteMovie.COLOUMN_MOVIE_BACKDROP, backdrop);

        long newRowId = db.insert(FavoriteMovieContract.FavoriteMovie.TABLE_NAME, null, values);

        Snackbar.make(constraintLayout, "is favorited:)", Snackbar.LENGTH_SHORT).show();
    }

    public void deleteDataFromDatabase() {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Definisikan proses seleksi 'where' dari query.
        String selection = FavoriteMovieContract.FavoriteMovie.COLOUMN_MOVIE_TITLE + " LIKE ?";
        // Argumen yang ditentukan.
        String[] selectionArgs = {title};
        // Eksekusi SQLstatement
        db.delete(FavoriteMovieContract.FavoriteMovie.TABLE_NAME, selection, selectionArgs);

        Snackbar.make(constraintLayout, "is un - favorited:(", Snackbar.LENGTH_SHORT).show();
    }

    public void favoriteState() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Movie> favorites = new ArrayList<Movie>();
        Cursor cursor = db.query(FavoriteMovieContract.FavoriteMovie.TABLE_NAME,
                new String[]{FavoriteMovieContract.FavoriteMovie.COLOUMN_MOVIE_TITLE},
                FavoriteMovieContract.FavoriteMovie.COLOUMN_MOVIE_TITLE + " =?",
                new String[]{title}, null, null,
                FavoriteMovieContract.FavoriteMovie._ID + " ASC", null);

        cursor.moveToFirst();
        Movie movie;

        if (cursor.getCount() > 0) {
            do {
                movie = new Movie();
                movie.setMovieTitle(cursor.getString(cursor.getColumnIndexOrThrow(FavoriteMovieContract.FavoriteMovie.COLOUMN_MOVIE_TITLE)));

                favorites.add(movie);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        if (!favorites.isEmpty()) {
            isFavorite = true;
        }
    }

    public void setFavorite() {
        if (isFavorite) {
            Glide.with(this).load(R.drawable.ic_favorite_button_after).into(favorite);
        } else {
            Glide.with(this).load(R.drawable.ic_favorite_button_before).into(favorite);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }
}
