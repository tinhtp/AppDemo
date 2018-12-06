package com.example.myname.mediaplayer.screen.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.myname.mediaplayer.R;
import com.example.myname.mediaplayer.data.model.Song;
import com.example.myname.mediaplayer.utils.OnItemRecyclerViewClick;
import java.util.ArrayList;
import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.MyViewHolder> {
    private List<Song> mSongList;
    private Context mContext;
    private OnItemRecyclerViewClick mOnItemRecyclerViewClick;

    public SongAdapter(Context context, OnItemRecyclerViewClick onItemRecyclerViewClick) {
        mSongList = new ArrayList<>();
        mContext = context;
        mOnItemRecyclerViewClick = onItemRecyclerViewClick;
    }

    public void updateAdapter(List<Song> songList) {
        mSongList.addAll(songList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_song, viewGroup, false);
        return new MyViewHolder(view, mOnItemRecyclerViewClick);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.BinData(mSongList.get(i));
    }

    @Override
    public int getItemCount() {
        return mSongList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTextViewName, mTextViewSinger;
        private Song mSong;
        OnItemRecyclerViewClick mlistener;

        public MyViewHolder(@NonNull View itemView,
                OnItemRecyclerViewClick onItemRecyclerViewClick) {
            super(itemView);
            mTextViewName = itemView.findViewById(R.id.textViewNameItem);
            mTextViewSinger = itemView.findViewById(R.id.textViewNameSingger);
            mlistener = onItemRecyclerViewClick;
            itemView.setOnClickListener(this);
        }

        public void BinData(Song song) {
            mSong = song;
            mTextViewName.setText(song.getNameSong());
            mTextViewSinger.setText(song.getSingger());
        }

        @Override
        public void onClick(View v) {
            mlistener.onclick(getAdapterPosition());
        }
    }
}
