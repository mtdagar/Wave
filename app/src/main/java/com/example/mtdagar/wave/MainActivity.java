package com.example.mtdagar.wave;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //variables
    public static Player player;
    public static Context cont;

    public static SeekBar seekBar;
    public static ImageButton playButton;
    public static ImageView albumArtImage;
    public static TextView nowPlayingTitleTextView;
    public static TextView nowPlayingArtistTextView;
    public static TextView nowPlayingAlbumTextView;

    public static Thread updateSeekbar;

    public static ArrayList<File> recentSongs = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.cont = getApplicationContext();


        //Set NavBar
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //Set Home Fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();

        player = new Player();

        playButton = findViewById(R.id.btn_play);
        seekBar = findViewById(R.id.seekBar);
        albumArtImage = findViewById(R.id.albumArtImage);
        nowPlayingTitleTextView = findViewById(R.id.nowPlayingTitleTextView);
        nowPlayingArtistTextView = findViewById(R.id.nowPlayingArtistTextView);
        nowPlayingAlbumTextView = findViewById(R.id.nowPlayingAlbumTextView);

        //Search library for songs
        Thread searchSongs = new Thread(new SearchLibrary());
        searchSongs.start();

        playButton.setBackgroundResource(R.drawable.baseline_play_circle_outline_white_36dp);
        playButton.setOnClickListener(this);

        updateSeekbar = new Thread(){
            @Override
            public void run(){
                seekBar.setMax(Player.mediaPlayer.getDuration());

                int totalDuration = Player.mediaPlayer.getDuration();
                int currentPosition = 0;
                //magic variable
                int adv = 0;

                while((adv = ((adv = totalDuration - currentPosition) < 500)?adv:500) > 2){
                    try{
                        currentPosition = Player.mediaPlayer.getCurrentPosition();
                        if(seekBar!=null){
                            seekBar.setProgress(currentPosition);
                        }
                        sleep(adv);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }catch (IllegalStateException e){
                        seekBar.setProgress(0);
                        break;
                    }
                }
            }
        };

    }

    //Bottom NavBar Listener
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()){
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_library:
                            selectedFragment = new LibraryFragment();
                            break;
                        case R.id.nav_playlists:
                            selectedFragment = new PlaylistsFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
    };


    //if play button is clicked
    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.btn_play:
                try {
                    Player.playButtonClicked();
                } catch (Exception e) {
                    Toast.makeText(this, "No song selected.", Toast.LENGTH_SHORT);
                }
                break;
        }
    }

    public static Bitmap getAlbumArt(ArrayList<File> fileList, int position){
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(fileList.get(position).getPath());
        Bitmap bitmap;

        byte [] data = mmr.getEmbeddedPicture();

        if(data != null)
        {
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            return bitmap;

        }
        else
        {
            bitmap = BitmapFactory.decodeResource(cont.getResources(), R.drawable.default_album_art);
            return bitmap;
        }
    }

}
