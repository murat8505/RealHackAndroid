package com.firefighters.models;

public class Property {
	private int propID;
	private String propName;
	
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
