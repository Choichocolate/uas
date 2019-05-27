package com.choirul.moviecatalogue.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.choirul.moviecatalogue.fragment.NowPlayingFragments;
import com.choirul.moviecatalogue.R;
import com.choirul.moviecatalogue.fragment.UpComingFragments;
import com.choirul.moviecatalogue.fragment.FavoriteFragments;

public class  MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private FragmentManager manager = getSupportFragmentManager();
    private Fragment nowPlaying = new NowPlayingFragments();
    private Fragment favoriteFragment = new FavoriteFragments();
    private Fragment upComingFragment = new UpComingFragments();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_now_playing:
                    nowPlaying = new NowPlayingFragments();
                    manager.beginTransaction().replace(R.id.fragment_container, nowPlaying).commit();
                    return true;
                case R.id.navigation_favorite:
                    favoriteFragment = new FavoriteFragments();
                    manager.beginTransaction().replace(R.id.fragment_container, favoriteFragment).commit();
                    return true;
                case R.id.navigation_up_coming:
                    upComingFragment = new UpComingFragments();
                    manager.beginTransaction().replace(R.id.fragment_container, upComingFragment).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if(savedInstanceState == null){
            manager
                    .beginTransaction()
                    .replace(R.id.fragment_container, nowPlaying)
                    .commit();
        }
    }

}
