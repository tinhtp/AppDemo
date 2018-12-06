package com.example.myname.mediaplayer.screen.home;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import com.example.myname.mediaplayer.R;
import com.example.myname.mediaplayer.data.Source.Local.LocalDataSource;
import com.example.myname.mediaplayer.data.Source.MusicRepository;
import com.example.myname.mediaplayer.data.model.Song;
import com.example.myname.mediaplayer.utils.OnItemRecyclerViewClick;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements OnItemRecyclerViewClick, MainContract.View {

    private RecyclerView mRecyclerView;
    List<Song> mSongArrayList;
    private SongAdapter mSongAdapter;
    public PlayService mPlayService;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectSV();
        init();
        checkPermission();
        initPresenter();

    }

    private void connectSV() {
        ServiceConnection serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                PlayService.ServiceSong serviceSong = (PlayService.ServiceSong) service;
                mPlayService = serviceSong.getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        Intent intent = new Intent(this, PlayService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    private void init() {
        mRecyclerView = findViewById(R.id.recyclerview);
        mSongArrayList = new ArrayList<>();
        mSongAdapter = new SongAdapter(this, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mSongAdapter);
    }

    public void initPresenter() {
        LocalDataSource dataSource = LocalDataSource.getsInstance(this);
        MusicRepository repository = MusicRepository.getsInstance(dataSource);
        MainPresenter presenter = new MainPresenter(repository, this);
        presenter.getDataLocal();
    }
    //    private void getSong() {
    //        ContentResolver resolver = getContentResolver();
    //        Uri uriMusic = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    //        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
    //
    //        String[] projection = {
    //                MediaStore.Audio.Media._ID, MediaStore.Audio.Media.ARTIST,
    //                MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DATA,
    //                MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.Media.DURATION
    //        };
    //
    //        final String sortOrder = MediaStore.Audio.AudioColumns.TITLE + " COLLATE LOCALIZED
    // ASC";
    //        Cursor cursor = resolver.query(uriMusic, projection, null, null, sortOrder);
    //        if (cursor != null && cursor.moveToFirst()) {
    //            int uri = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
    //            int nameSong = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
    //            int id = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
    //            int duration = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
    //            do {
    //                String currentURI = cursor.getString(uri);
    //                String currentNameSong = cursor.getString(nameSong);
    //                int currentId = cursor.getInt(id);
    //                String currentDuration = cursor.getString(duration);
    //                mSongArrayList.add(new Song(currentId, currentNameSong, currentURI));
    //            } while (cursor.moveToNext());
    //            mSongAdapter.updateAdapter(mSongArrayList);
    //            mSongAdapter.notifyDataSetChanged();
    //        }
    //
    //        cursor.close();
    //    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            //getSong();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
            int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    //getSong();

                    //mSongAdapter.notifyDataSetChanged();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Chua Cap quyen", Toast.LENGTH_SHORT).show();
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onclick(int position) {
        Intent intent = new Intent(this, PlayService.class);
        intent.putExtra(PlayService.POSITION, position);
        intent.putParcelableArrayListExtra(PlayService.LIST_MUSIC,
                (ArrayList<? extends Parcelable>) mSongArrayList);
        startService(intent);
        FragmentPlay fragmentPlay = FragmentPlay.newInstance(position, mSongArrayList);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame, fragmentPlay);
        fragmentTransaction.commit();
    }

    @Override
    public void ok(List<Song> songList) {
        mSongArrayList=songList;
        mSongAdapter.updateAdapter(songList);
    }
}
