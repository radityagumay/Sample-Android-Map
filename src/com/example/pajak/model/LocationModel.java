package com.example.pajak.model;

public class LocationModel {

	private String mId;
	private String mAddress;
	private String mName;
	private String mImage;
	private String mPhone;
	private double mLatitude;
	private double mLongitude; 
	private double mDistance;

	public String getId() {
		return mId;
	}
	public void setId(String mId) {
		this.mId = mId;
	}
	public String getPhone() {
		return mPhone;
	}
	public void setPhone(String mPhone) {
		this.mPhone = mPhone;
	}
	public String getAddress() {
		return mAddress;
	}
	public void setAddress(String mAddress) {
		this.mAddress = mAddress;
	}
	public double getDistance() {
		return mDistance;
	}
	public void setDistance(double mDistance) {
		this.mDistance = mDistance;
	}
	public String getName() {
		return mName;
	}
	public void setName(String mName) {
		this.mName = mName;
	}
	public String getImage() {
		return mImage;
	}
	public void setImage(String mImage) {
		this.mImage = mImage;
	}
	public double getLatitude() {
		return mLatitude;
	}
	public void setLatitude(double mLatitude) {
		this.mLatitude = mLatitude;
	}
	public double getLongitude() {
		return mLongitude;
	}
	public void setLongitude(double mLongitude) {
		this.mLongitude = mLongitude;
	}
}
