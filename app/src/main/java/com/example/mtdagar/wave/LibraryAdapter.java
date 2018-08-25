package com.example.mtdagar.wave;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.ViewHolder> {

    //variables


    private static final String TAG = "RecentlyPlayedAdapter";

    private ArrayList<String> mNames = new ArrayList<>();
    private Context mContext;

    public LibraryAdapter(Context mContext, ArrayList<String> mNames) {
        this.mNames = mNames;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_library_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.name.setText(mNames.get(position));
        holder.name.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked" + mNames.get(position));
                Toast.makeText(mContext, ""+position, Toast.LENGTH_SHORT).show();
                MainActivity.player.start(SearchLibrary.mySongs, position, MainActivity.cont);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.library_item_name);
        }
    }
}
