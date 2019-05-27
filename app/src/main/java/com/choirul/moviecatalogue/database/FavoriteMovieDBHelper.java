package com.choirul.moviecatalogue.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FavoriteMovieDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FavoriteMovie.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_TABLE_FAVORITE =
            "CREATE TABLE " + FavoriteMovieContract.FavoriteMovie.TABLE_NAME + " (" +
                    FavoriteMovieContract.FavoriteMovie._ID + " INTEGER PRIMARY KEY," +
                    FavoriteMovieContract.FavoriteMovie.COLOUMN_MOVIE_TITLE + TEXT_TYPE + COMMA_SEP +
                    FavoriteMovieContract.FavoriteMovie.COLOUMN_MOVIE_ID + TEXT_TYPE + COMMA_SEP +
                    FavoriteMovieContract.FavoriteMovie.COLOUMN_MOVIE_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    FavoriteMovieContract.FavoriteMovie.COLOUMN_MOVIE_IMAGE + TEXT_TYPE + COMMA_SEP +
                    FavoriteMovieContract.FavoriteMovie.COLOUMN_MOVIE_BACKDROP + TEXT_TYPE + " )";

    private static final String SQL_DELETE_TABLE_FAVORITE =
            "DROP TABLE IF EXISTS " + FavoriteMovieContract.FavoriteMovie.TABLE_NAME;

    public FavoriteMovieDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_TABLE_FAVORITE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE_FAVORITE);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
