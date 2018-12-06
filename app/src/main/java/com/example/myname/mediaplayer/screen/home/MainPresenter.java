package com.example.myname.mediaplayer.screen.home;

import com.example.myname.mediaplayer.data.Source.Local.OnGetDataLocal;
import com.example.myname.mediaplayer.data.Source.MusicRepository;
import com.example.myname.mediaplayer.data.model.Song;
import java.util.List;

public class MainPresenter implements MainContract.Presenter {
    private MusicRepository mMusicRepository;
    private MainContract.View mView;

    public MainPresenter(MusicRepository musicRepository, MainContract.View view) {
        mMusicRepository = musicRepository;
        mView = view;
    }

    @Override
    public void getDataLocal() {
        mMusicRepository.getDataLocal(new OnGetDataLocal() {
            @Override
            public void success(List<Song> songList) {
                mView.ok(songList);
            }
        });
    }
}
