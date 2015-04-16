package de.udos.androiddemocontentprovider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AlbumArrayAdapter extends ArrayAdapter<Album> {
	
	static class ViewHolder {
		
		public ImageView art;
		public TextView title;
		public TextView artist;
	}

	private int mLayout;

	public AlbumArrayAdapter(Context context, int resource, List<Album> objects) {
		
		super(context, resource, objects);
		this.mLayout = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder vh;
		
		if (convertView == null) {
			
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(mLayout, parent, false);
			
			vh = new ViewHolder();
			vh.art = (ImageView) convertView.findViewById(R.id.iv_album_art);
			vh.title = ((TextView) convertView.findViewById(R.id.tv_album_title));
			vh.artist = ((TextView) convertView.findViewById(R.id.tv_album_artist));
			
			convertView.setTag(vh);
		}
		
		Album album = getItem(position);

		vh = (ViewHolder) convertView.getTag();
		
		vh.art.setImageURI(album.getArt());
		vh.title.setText(album.getTitle());
		vh.artist.setText(album.getArtist());
		
		return convertView;
	}
}
