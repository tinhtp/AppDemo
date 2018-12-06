package com.example.myname.mediaplayer.data.Source.Local;

import android.content.Context;
import com.example.myname.mediaplayer.data.Source.MusicDataSource;

public class LocalDataSource implements MusicDataSource.Local {
    private static LocalDataSource sInstance;
    private Context mContext;

    private LocalDataSource(Context mContext) {
        this.mContext = mContext;
    }

    public static LocalDataSource getsInstance(Context context) {
        if (sInstance == null) {
            sInstance = new LocalDataSource(context);
        }
        return sInstance;
    }

    @Override
    public void getData(OnGetDataLocal onGetDataLocal) {
        GetDataLocal getDataLocal = new GetDataLocal(onGetDataLocal);
        getDataLocal.getDataLocal(mContext);
    }
}
