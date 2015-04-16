package de.udos.androiddemocontentprovider;

import android.net.Uri;

public class Album {
	
	private int id;
	private String title;
	private String artist;
	private Uri art;
	
	public Album (int id, String title, String artist, Uri art) {
		
		this.id = id;
		this.title = title;
		this.artist = artist;
		this.art = art;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public Uri getArt() {
		return art;
	}

	public void setArt(Uri art) {
		this.art = art;
	}

	@Override
	public String toString() {
		return this.artist + " - " + this.title;
	}
}
