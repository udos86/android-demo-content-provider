package de.udos.androiddemocontentprovider;

import android.Manifest;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOADER_ID_ALBUMS_ID = 0;
    private static final int REQUEST_CODE_PERMISSION_READ_EXTERNAL_STORAGE = 999;

    private static final Uri ALBUMS_EXTERNAL = android.provider.MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
    private static final String COLUMN_ALBUM_ID = android.provider.MediaStore.Audio.Albums._ID;
    private static final String COLUMN_ALBUM_TITLE = android.provider.MediaStore.Audio.Albums.ALBUM;
    private static final String COLUMN_ALBUM_ARTIST = android.provider.MediaStore.Audio.Albums.ARTIST;
    private static final String COLUMN_ALBUM_ART = android.provider.MediaStore.Audio.Albums.ALBUM_ART;
    //private static final String[] projection = {COLUMN_ALBUM_ID, COLUMN_ALBUM_TITLE, COLUMN_ALBUM_ARTIST};

    private AlbumArrayAdapter mAdapter;
    private ArrayList<Album> mAlbums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAlbums = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_PERMISSION_READ_EXTERNAL_STORAGE);

        } else {
            loadAlbums(true);
        }

		/*
		mCAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, c,
				new String[] {COLUMN_ALBUM_NAME, COLUMN_ALBUM_ARTIST},
				new int[] {android.R.id.text1, android.R.id.text2}, -1);
		*/

        mAdapter = new AlbumArrayAdapter(this, R.layout.album_list_item, mAlbums);
        setListAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);
    }

    protected void loadAlbums(boolean async) {

        if (async) {
            getLoaderManager().initLoader(LOADER_ID_ALBUMS_ID, null, this);

        } else {

            Cursor c = getContentResolver().query(ALBUMS_EXTERNAL, null, null, null, null);
            onLoadFinished(null, c);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, ALBUMS_EXTERNAL, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor c) {

        mAdapter.clear();

        int colIdxId = c.getColumnIndex(COLUMN_ALBUM_ID);
        int colIdxArtist = c.getColumnIndex(COLUMN_ALBUM_ARTIST);
        int colIdxTitle = c.getColumnIndex(COLUMN_ALBUM_TITLE);
        int colIdxArt = c.getColumnIndex(COLUMN_ALBUM_ART);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            String title = c.getString(colIdxTitle);
            String artist = c.getString(colIdxArtist);
            String art = c.getString(colIdxArt);

            mAlbums.add(new Album(colIdxId, title, artist, (art != null) ? Uri.parse(art) : null));
        }

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //Nothing to do
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {

            case REQUEST_CODE_PERMISSION_READ_EXTERNAL_STORAGE: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    loadAlbums(true);
                }
            }
        }

    }
}
