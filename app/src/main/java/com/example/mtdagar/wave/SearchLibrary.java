package com.example.mtdagar.wave;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchLibrary implements Runnable {

    String[] items;
    View view;
    public static ArrayList<File> mySongs;
    ArrayList<String> libraryItems;

    static ArrayList<String> mNames = new ArrayList<>();
    static ArrayList<String> mImageUrls = new ArrayList<>();
    static ArrayList<Bitmap> mImages = new ArrayList<>();



    @Override
    public void run() {
        mySongs = findSongs(Environment.getExternalStorageDirectory());
        items = new String[mySongs.size()];

        for (int i = 0; i < mySongs.size(); i++) {
            items[i] = mySongs.get(i).getName().toString();
            //Toast.makeText(view.getContext(), mySongs.get(i).getName().toString(), Toast.LENGTH_SHORT).show();
        }

        List<String> songList = Arrays.asList(items);
        libraryItems = new ArrayList<String>();
        libraryItems.addAll(songList);

        LibraryFragment.libraryItems = libraryItems;

        MainActivity.player.setLibrarySongsList(mySongs);




        mImages.add(getAlbumArt(0));
        mNames.add(libraryItems.get(0));

        mImages.add(getAlbumArt(1));
        mNames.add(libraryItems.get(1));

        mImages.add(getAlbumArt(2));
        mNames.add(libraryItems.get(2));

        mImages.add(getAlbumArt(3));
        mNames.add(libraryItems.get(3));

        mImages.add(getAlbumArt(4));
        mNames.add(libraryItems.get(4));


        MainActivity.recentSongs.add(mySongs.get(0));
        MainActivity.recentSongs.add(mySongs.get(1));



    }


    public ArrayList<File> findSongs (File root){
        ArrayList<File> arrayList = new ArrayList<File>();

        File[] files = root.listFiles();
        for (File singleFile : files) {
            if (singleFile.isDirectory() && !singleFile.isHidden()) {
                    arrayList.addAll(findSongs(singleFile));
            } else {
                if (singleFile.getName().endsWith(".mp3")) {
                    arrayList.add(singleFile);
                }
            }
        }
            return arrayList;
    }

    public static Bitmap getAlbumArt(int position){
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(mySongs.get(position).getPath());
        Bitmap bitmap;

        byte [] data = mmr.getEmbeddedPicture();

        if(data != null)
        {
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            return bitmap;

        }
        else
        {
            bitmap = BitmapFactory.decodeResource(MainActivity.cont.getResources(), R.drawable.default_album_art);
            return bitmap;
        }
    }

    public void toast(String text){
        Toast.makeText(view.getContext(), text, Toast.LENGTH_SHORT).show();
    }



}


