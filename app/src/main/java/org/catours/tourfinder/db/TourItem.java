package org.catours.tourfinder.db;

import java.text.NumberFormat;

import com.google.android.gms.maps.model.LatLng;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class TourItem implements Parcelable {
	private long id;
	private String title;
	private String description;
	private double price;
	private String image;
	private double latitude = 36.778261;
	private double longitude = -119.417932;
	private String markerText = "";

	private static String LOGTAG = "EXPLORECA";

	public TourItem() {
	}
	public TourItem(Parcel in) {
		Log.i(LOGTAG, "Parcel constructor");

		id = in.readLong();
		title = in.readString();
		description = in.readString();
		price = in.readDouble();
		image = in.readString();
		latitude = in.readDouble();
		longitude = in.readDouble();
		markerText = in.readString();
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getMarkerText() {
		return markerText;
	}
	public void setMarkerText(String markerText) {
		this.markerText = markerText;
	}
	@Override
	public String toString() {
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		return title + "\n(" + nf.format(price) + ")";
	}


	public LatLng getLatLng() {
		LatLng latLng = new LatLng(latitude, longitude);
		return latLng;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(id);
		dest.writeString(title);
		dest.writeString(description);
		dest.writeDouble(price);
		dest.writeString(image);
		dest.writeDouble(latitude);
		dest.writeDouble(longitude);
		dest.writeString(markerText);
	}

	public static final Parcelable.Creator<TourItem> CREATOR =
			new Parcelable.Creator<TourItem>() {

		@Override
		public TourItem createFromParcel(Parcel source) {
			return new TourItem(source);
		}

		@Override
		public TourItem[] newArray(int size) {
			return new TourItem[size];
		}

	};

}
