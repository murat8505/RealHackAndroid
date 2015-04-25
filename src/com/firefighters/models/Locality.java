package com.firefighters.models;

public class Locality {
	private int locId;
	private double locLat;
	private double locLong;
	private String locName;
	private String placeId;
	private int parentLocId;
	private double locROI;
	private double locLFSTL;
	
	public double getLocROI() {
		return locROI;
	}
	public void setLocROI(double locROI) {
		this.locROI = locROI;
	}
	public double getLocLFSTL() {
		return locLFSTL;
	}
	public void setLocLFSTL(double locLFSTL) {
		this.locLFSTL = locLFSTL;
	}
	public int getParentLocId() {
		return parentLocId;
	}
	public void setParentLocId(int parentLocId) {
		this.parentLocId = parentLocId;
	}
	public int getLocId() {
		return locId;
	}
	public void setLocId(int locId) {
		this.locId = locId;
	}
	public double getLocLat() {
		return locLat;
	}
	public void setLocLat(double locLat) {
		this.locLat = locLat;
	}
	public double getLocLong() {
		return locLong;
	}
	public void setLocLong(double locLong) {
		this.locLong = locLong;
	}
	public String getLocName() {
		return locName;
	}
	public void setLocName(String locName) {
		this.locName = locName;
	}
	public String getPlaceId() {
		return placeId;
	}
	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}
}
