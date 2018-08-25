package com.example.mtdagar.wave;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";



    static View view;

    static RecyclerView libraryList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView recyclerView = HomeFragment.view.findViewById(R.id.recentRecyclerview);
        RecentlyPlayedAdapter listAdapter = new RecentlyPlayedAdapter(HomeFragment.view.getContext(), SearchLibrary.mNames, SearchLibrary.mImageUrls, SearchLibrary.mImages);
        recyclerView.setAdapter(listAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(HomeFragment.view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);


        return view;
    }

    public void toast(String text){
        Toast.makeText(view.getContext(), text, Toast.LENGTH_SHORT).show();
    }


}
