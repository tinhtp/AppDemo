package com.example.myname.mediaplayer.data.Source.Local;

import com.example.myname.mediaplayer.data.model.Song;
import java.util.List;

public interface OnGetDataLocal {
    void success(List<Song> songList);
}
