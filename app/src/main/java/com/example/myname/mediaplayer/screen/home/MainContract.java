package com.example.myname.mediaplayer.screen.home;

import com.example.myname.mediaplayer.data.model.Song;
import java.util.List;

public interface MainContract {

    interface View {
        void ok(List<Song> songList);
    }

    interface Presenter {
        void getDataLocal();
    }
}
