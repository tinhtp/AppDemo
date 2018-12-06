package com.example.myname.mediaplayer.data.Source;

import com.example.myname.mediaplayer.data.Source.Local.OnGetDataLocal;

public class MusicRepository {
    private static MusicRepository sInstance;
    private MusicDataSource.Local mLocal;

    private MusicRepository(MusicDataSource.Local local) {
        mLocal = local;
    }

    public static MusicRepository getsInstance(MusicDataSource.Local local) {
        if (sInstance == null) {
            sInstance = new MusicRepository(local);
        }
        return sInstance;
    }
    public void getDataLocal(OnGetDataLocal onGetDataLocal){
        mLocal.getData(onGetDataLocal);
    }
}
