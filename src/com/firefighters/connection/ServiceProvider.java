package com.firefighters.connection;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

import com.google.gson.JsonObject;

public interface ServiceProvider {

	
	
	@GET("/getproperties/{placeId}")
	void getProperties( @Path("placeId") String placeId,Callback<JsonObject> callback);
	
	@GET("/")
	void getPlacesSuggestion(Callback<JsonObject> callback);
	
	
}
