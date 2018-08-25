package com.example.mtdagar.wave;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Player {

    Context cont;
    public static MediaPlayer mediaPlayer;
    public static MediaMetadataRetriever mmr;
    public static String title;
    public static String artist;
    public static String album;
    Uri u;
    static ArrayList<File> librarySongsList;


    public void start(ArrayList librarySongsList, int position, Context cont){
        int pos = position;
        this.librarySongsList = librarySongsList;
        this.cont = cont;
        mmr = new MediaMetadataRetriever();

        if(mediaPlayer != null) {
            clearMediaPlayer();
        }

        //MainActivity.recentSongs.add((File)librarySongsList.get(position));


        mmr.setDataSource(this.getLibrarySongsList().get(position).getPath());
        Bitmap albumArtMain;

        byte [] data = mmr.getEmbeddedPicture();

        if(data != null)
        {
            albumArtMain = BitmapFactory.decodeByteArray(data, 0, data.length);
        } else{
            albumArtMain = BitmapFactory.decodeResource(cont.getResources(), R.drawable.default_album_art);
        }

        artist =  mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
        title = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        album = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);

        MainActivity.albumArtImage.setImageBitmap(albumArtMain);
        MainActivity.nowPlayingTitleTextView.setText(title);
        MainActivity.nowPlayingArtistTextView.setText(artist);
        MainActivity.nowPlayingAlbumTextView.setText(album);


        u = Uri.parse(librarySongsList.get(position).toString());
        mediaPlayer = MediaPlayer.create(cont, u);
        mediaPlayer.start();
        MainActivity.playButton.setBackgroundResource(R.drawable.baseline_pause_circle_outline_white_36dp);
        MainActivity.seekBar.setProgress(0);

        if (MainActivity.updateSeekbar.getState() == Thread.State.NEW) {
            MainActivity.updateSeekbar.start();
        }


        MainActivity.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
    }

    public static void playButtonClicked() throws Exception{
        if(mediaPlayer.isPlaying()){
            MainActivity.playButton.setBackgroundResource(R.drawable.baseline_play_circle_outline_white_36dp);
            mediaPlayer.pause();
        }else {
            MainActivity.playButton.setBackgroundResource(R.drawable.baseline_pause_circle_outline_white_36dp);
            mediaPlayer.start();
        }
    }

    /**public void saveRecentSongs(){
        SharedPreferences sharedPreferences = MainActivity.cont.getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(librarySongsList);
        editor.putString("song list", json);
        editor.apply();
    }

    public void loadRecentSongs(){
        SharedPreferences sharedPreferences = MainActivity.cont.getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("song list", null);
        Type type = new TypeToken<ArrayList<File>>() {}.getType();
        mFile = gson.fromJson(json, type);
    }**/

    public static void clearMediaPlayer(){
        mediaPlayer.stop();
        mediaPlayer.release();
    }

    public ArrayList<File> getLibrarySongsList() {
        return librarySongsList;
    }

    public void setLibrarySongsList(ArrayList<File> mySongs) {
        this.librarySongsList = librarySongsList;
    }
}
