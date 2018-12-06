package com.example.myname.mediaplayer.data.Source;

import com.example.myname.mediaplayer.data.Source.Local.OnGetDataLocal;

public interface MusicDataSource {
    interface Local {
        void getData(OnGetDataLocal onGetDataLocal);
    }
}
