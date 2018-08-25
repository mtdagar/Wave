package com.example.mtdagar.wave;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LibraryFragment extends Fragment{

    View view;

    RecyclerView libraryList;
    String[] items;
    static ArrayList<String> libraryItems;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_library, container, false);

        RecyclerView libraryRecyclerView = view.findViewById(R.id.libraryList);
        LibraryAdapter libraryAdapter = new LibraryAdapter(view.getContext(), libraryItems);
        libraryRecyclerView.setAdapter(libraryAdapter);
        RecyclerView.LayoutManager libraryLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        libraryRecyclerView.setLayoutManager(libraryLayoutManager);



        return view;

    }

    public ArrayList<File> findSongs(File root){
        ArrayList<File> arrayList = new ArrayList<File>();

        File[] files = root.listFiles();
        for(File singleFile:files){
            if(singleFile.isDirectory() && !singleFile.isHidden()){
                arrayList.addAll(findSongs(singleFile));
            }else {
                if(singleFile.getName().endsWith(".mp3")){
                    arrayList.add(singleFile);
                }
            }
        }
        return arrayList;
    }

    public void toast(String text){
        Toast.makeText(view.getContext(), text, Toast.LENGTH_SHORT).show();
    }


    public static void setLibraryItems(ArrayList<String> li){
        libraryItems = li;
    }
}
