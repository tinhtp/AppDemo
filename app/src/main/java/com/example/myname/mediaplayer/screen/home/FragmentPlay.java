package com.example.myname.mediaplayer.screen.home;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.myname.mediaplayer.R;
import com.example.myname.mediaplayer.data.model.Song;
import java.util.ArrayList;
import java.util.List;

public class FragmentPlay extends Fragment implements View.OnClickListener {

    private static final String BUNDEL_POSITION = "POSITION";
    private static final String BUNDEL_LIST = "LIST_SONG";
    int position;
    MainActivity mMainActivity;
    List<Song> mSongList;
    ImageView imageViewPlay;
    MediaPlayer mMediaPlayer ;

    public static FragmentPlay newInstance(int position, List<Song> songList) {
        FragmentPlay fragmentPlay = new FragmentPlay();
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDEL_POSITION, position);
        bundle.putParcelableArrayList(BUNDEL_LIST, (ArrayList<? extends Parcelable>) songList);
        fragmentPlay.setArguments(bundle);
        return fragmentPlay;
    }

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lay_music, container, false);
        Bundle bundle = getArguments();
        position = bundle.getInt(BUNDEL_POSITION);
        mSongList = bundle.getParcelableArrayList(BUNDEL_LIST);
        mMainActivity = (MainActivity) getActivity();
        view.findViewById(R.id.imgNext).setOnClickListener(this);
        view.findViewById(R.id.imgPre).setOnClickListener(this);
        view.findViewById(R.id.imgPlay).setOnClickListener(this);
        imageViewPlay = view.findViewById(R.id.imgPlay);
        return view;
    }


    private void nextSong() {
        position++;
        if (position == mSongList.size()) {
            position = 0;
        }
        mMainActivity.mPlayService.next();
    }

    private void preSong() {
        position--;
        if (position == -1) {
            position = mSongList.size() - 1;
        }
        mMainActivity.mPlayService.pre();
    }

    private void pauseSong() {
       if(mMainActivity.mPlayService.isPlaying()){
           imageViewPlay.setImageResource(R.drawable.ic_play_circle_filled_black_24dp);
           mMainActivity.mPlayService.pause();
        }else {
           imageViewPlay.setImageResource(R.drawable.ic_pause_black_24dp);
           mMainActivity.mPlayService.play();

       }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgNext:
                nextSong();
                break;
            case R.id.imgPre:
                preSong();
                break;
            case R.id.imgPlay:
                pauseSong();
        }
    }

    }

