package com.firefighters.connection;


import retrofit.RestAdapter;


public class ServerConnectionMaker  {
	
	
	
	
	public static RestAdapter restAdapter = new RestAdapter.Builder()
	.setEndpoint("http://192.168.2.8:8080/RealHackServer/api/")
	.setLogLevel(RestAdapter.LogLevel.FULL)
	.build();

	public  static ServiceProvider serviceProvider =  restAdapter.create(ServiceProvider.class);
		 
	
	public static  void sendRequest(ConnectionInterface ci){
				
			
		ci.sendRequest(serviceProvider);
		
	}
	
	
	    
	
}
