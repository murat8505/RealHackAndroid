package com.firefighters.models;

public class Property {
	private int propID;
	private String propName;
	private double propLat;
	private double propLng;
	public double getPropLat() {
		return propLat;
	}
	public void setPropLat(double propLat) {
		this.propLat = propLat;
	}
	public double getPropLng() {
		return propLng;
	}
	public void setPropLng(double propLng) {
		this.propLng = propLng;
	}
	private int locId;
	
	public int getPropID() {
		return propID;
	}
	public void setPropID(int propID) {
		this.propID = propID;
	}
	public String getPropName() {
		return propName;
	}
	public void setPropName(String propName) {
		this.propName = propName;
	}
	
	public int getLocId() {
		return locId;
	}
	public void setLocId(int locId) {
		this.locId = locId;
	}
	
}
