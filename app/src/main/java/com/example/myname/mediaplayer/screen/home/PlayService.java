package com.example.myname.mediaplayer.screen.home;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import com.example.myname.mediaplayer.data.model.Song;
import java.io.IOException;
import java.util.List;

public class PlayService extends Service {
    public static final String POSITION = "POSITION";
    public static final String LIST_MUSIC = "LIST_MUSIC";

    private IBinder mIBinder;
    private MediaPlayer mMediaPlayer;
    private List<Song> mSongList;
    private int position;

    @Override
    public void onCreate() {
        mIBinder = new ServiceSong();
        mMediaPlayer = new MediaPlayer();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        position = intent.getIntExtra(POSITION, 0);
        mSongList = intent.getParcelableArrayListExtra(LIST_MUSIC);
        play();
        //next();
        // pre();
        return START_NOT_STICKY;
    }

    public void play() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
        }
        mMediaPlayer.reset();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mMediaPlayer.setDataSource(mSongList.get(position).getUrlSong());
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mMediaPlayer.start();
    }

    public void next() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
        }
        mMediaPlayer.reset();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        position++;
        if (position == mSongList.size()) {
            position = 0;
        }
        try {
            mMediaPlayer.setDataSource(mSongList.get(position).getUrlSong());
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.start();
    }

    public void pre() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
        }
        mMediaPlayer.reset();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        position--;
        if (position == -1) {
            position = mSongList.size() - 1;
        }
        try {
            mMediaPlayer.setDataSource(mSongList.get(position).getUrlSong());
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.start();
    }

    public void pause() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
        }
    }
    public boolean isPlaying(){
        return  mMediaPlayer.isPlaying();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder;
    }

    public class ServiceSong extends Binder {
        public PlayService getService() {
            return PlayService.this;
        }
    }
}
