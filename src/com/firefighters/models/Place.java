package com.firefighters.models;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.firefighters.connection.ConnectionInterface;
import com.firefighters.connection.ServerConnectionMaker;
import com.firefighters.connection.ServiceProvider;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Place implements ConnectionInterface{
	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	String placeId;
	PlaceInterface callee;
	
	@Override
	public void sendRequest(ServiceProvider sp) {
		// TODO Auto-generated method stub
		sp.getProperties(this.placeId, new Callback<JsonObject>() {
			
			@Override
			public void success(JsonObject result, Response response) {
				// TODO Auto-generated method stub
				
				callee.getPropertieSuccess(result);
			}
			
			@Override
			public void failure(RetrofitError response) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	public void getProperties(PlaceInterface callee) {
		// TODO Auto-generated method stub
		this.callee = callee;
		ServerConnectionMaker.sendRequest(this);
	}
}
