package com.example.myname.mediaplayer.data.Source.Local;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import com.example.myname.mediaplayer.data.model.Song;
import java.util.ArrayList;
import java.util.List;

public class GetDataLocal {

    private OnGetDataLocal mOnGetDataLocal;

    public GetDataLocal(OnGetDataLocal onGetDataLocal) {
        mOnGetDataLocal = onGetDataLocal;
    }

    public void getDataLocal(Context context) {
        List<Song> songList = new ArrayList<>();
        ContentResolver contentResolver = context.getContentResolver();
        Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = contentResolver.query(musicUri, null, null, null, null);
        if (musicCursor != null && musicCursor.moveToFirst()) {
            int uri = musicCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            int id = musicCursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int nameSong = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int duration = musicCursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
            int singer = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            do {
                int currentId = musicCursor.getInt(id);
                String currentNameSong = musicCursor.getString(nameSong);
                String currentDuration = musicCursor.getString(duration);
                String currentSinger = musicCursor.getString(singer);
                String currentUrl = musicCursor.getString(uri);
                songList.add(new Song(currentId, currentNameSong, currentUrl, currentDuration,
                        currentSinger));
            } while (musicCursor.moveToNext());
            musicCursor.close();
        }
        mOnGetDataLocal.success(songList);
    }
}
