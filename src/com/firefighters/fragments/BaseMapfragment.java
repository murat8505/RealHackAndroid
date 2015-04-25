/**
 * 
 */
package com.firefighters.fragments;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.SearchManager;
import android.content.Context;
import android.database.MatrixCursor;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.support.v7.widget.SearchView.OnSuggestionListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.firefighters.connection.ServiceProvider;
import com.firefighters.models.Place;
import com.firefighters.models.PlaceInterface;
import com.firefighters.models.PlacePrediction;
import com.firefighters.models.PropertyResponse;
import com.firefighters.realhack.R;
import com.firefighters.ui.PlacesSearchAdapter;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.CancelableCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * @author Harshit Pathak
 *
 */
public class BaseMapfragment extends Fragment implements PlaceInterface{
	View rootView;
	private static SearchView shopSearchView;
	private MatrixCursor suggestionCursor;
	private ArrayList<String> placesNameList;
	private SupportMapFragment mMapFragment;
	private Menu MenuReference;
	private static GoogleMap mMap;
	private static PlacesSearchAdapter searchAdapter;
	private static Context context;
	public static ArrayList<PlacePrediction> placesList;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        // Inflate the layout for this fragment
		rootView = inflater.inflate(R.layout.map_fragment_layout, container, false);
    	setHasOptionsMenu(true);
    	context = getActivity();
    	mMapFragment = ((SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.mapFragment));
    	mMap = mMapFragment.getMap();
    	move_map_camera(new LatLng(12.9715987,77.5945627), null,11);
		return rootView;
    }
	
	@Override
	public void onActivityCreated (Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);

	    
	}
	@Override
	public void onResume()
	{
		
		super.onResume();
		
	}
	
	
	@Override
	public void onPause(){
		super.onPause();
	
	}
	
	 public static void move_map_camera(LatLng coordinate, final CancelableCallback callBack,int zoom) {
			CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(coordinate, zoom);
		    mMap.animateCamera(yourLocation, callBack);
			//mMap.moveCamera(yourLocation);
		    
		}
	 private String getSuggestion(int position) {
		    String suggest1 = (String) shopSearchView.getSuggestionsAdapter().getItem(position);    
		    return suggest1;
	 }
	 

	    @Override
	    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	    	MenuReference = menu;
	    	inflater.inflate(R.menu.main, menu);
	        SearchManager searchManager = (SearchManager)context.getSystemService(Context.SEARCH_SERVICE);
	        MenuItem searchMenuItem =  menu.findItem(R.id.search);
	        shopSearchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
	        
	        shopSearchView.setQueryHint("Search Location");
	        shopSearchView.setSubmitButtonEnabled(false);
	        shopSearchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
	        
	        shopSearchView.setIconifiedByDefault(true); //this line
	        String[] columnNames = {"_id","description"};
	         suggestionCursor  = new MatrixCursor(columnNames);
	         placesNameList = new ArrayList<String>();
	         searchAdapter = new PlacesSearchAdapter(context, suggestionCursor, placesNameList);
				shopSearchView.setSuggestionsAdapter(searchAdapter);
	        	final PlacesAutoComplete pl = new PlacesAutoComplete();

	        shopSearchView.setOnQueryTextListener(new OnQueryTextListener(){
				@Override
				public boolean onQueryTextChange(String newText) {
					if(newText.length()<= 3){
					    pl.autocomplete(newText);
						
					}
					else{
						searchAdapter.getFilter().filter(newText);
					}
					
					return true;
				}

				@Override
				public boolean onQueryTextSubmit(String query) {
					
					return true;
				}
	        	
	        });
	        
	        shopSearchView.setOnSuggestionListener(new OnSuggestionListener(){

				@Override
				public boolean onSuggestionClick(int index) {
					
					if(Geocoder.isPresent()) {

					    Geocoder geocoder = new Geocoder(context);
					    List<android.location.Address> addresses = null;
					    try {
					    	
					        addresses = geocoder.getFromLocationName(getSuggestion(index), 1);
					        if (!addresses.isEmpty()) {
					            android.location.Address address = addresses.get(0);
					            LatLng coordinate = new LatLng(address.getLatitude(),address.getLongitude());
					            
					            //using converted address to latlng to query for shops
					            move_map_camera(coordinate,null,14);
//					           
					            
					            
					            if(placesList.get(index)!= null){
					            	if(placesList.get(index).getPlace_id() != null)
					            	getProperties(placesList.get(index).getPlace_id());
					            }
					            //hide keyboard
					            InputMethodManager inputManager = (InputMethodManager)
					            context.getSystemService(Context.INPUT_METHOD_SERVICE); 
					            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
					            InputMethodManager.HIDE_NOT_ALWAYS);
					            shopSearchView.setIconifiedByDefault(true); //this line
					            
					           

					        } else {
					            // No results for your HomelyBuysLocation
					        }
					    } catch (Exception e) {
					        e.printStackTrace();
					    }
					}
					return true;
				}

				

				@Override
				public boolean onSuggestionSelect(int arg0) {
					// TODO Auto-generated method stub
					return false;
				}
	        });
	        
	    }
	    private void getProperties(String place_id) {
			// TODO Auto-generated method stub
			Place place = new Place();
			place.setPlaceId(place_id);
			place.getProperties(this);
		}

	    public static class PlacesAutoComplete {

	

	private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
	private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
	private static final String OUT_JSON = "/json";
	private static final String API_KEY = "AIzaSyDYYRgTGk777pOLVGQgqyYA3QtFKF9BMbw";
	public ServiceProvider serviceProvider = null;
	public JsonArray placesPrediction = new JsonArray();
	public ArrayList<String> placesName ;
	private String[] columnNames = {"_id","description"};
	public MatrixCursor suggestionCursor  ;
	public ArrayList<String> placesNameList;
	public static ArrayList<PlacePrediction> getPlacesList()
	{
		return placesList;
	}
	
	public  void autocomplete(String input) {
		
		placesList = new ArrayList<PlacePrediction>();
		placesNameList = new ArrayList<String>();
		suggestionCursor  = new MatrixCursor(columnNames);
		
	    StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
	    sb.append("?key=" + API_KEY);
	    sb.append("&types=(regions)");
	    sb.append("&location=12.9715987,77.5945627");
        sb.append("&components=country:in");
      
        try {
			sb.append("&input=" + URLEncoder.encode(input, "utf8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        sb.append("&offset=2");
        String url = sb.toString();
	    RestAdapter restAdapter = new RestAdapter.Builder()
		.setEndpoint(url)		
		.setLogLevel(RestAdapter.LogLevel.FULL)
		.build();
		
	    serviceProvider = restAdapter.create(ServiceProvider.class);
		
		serviceProvider.getPlacesSuggestion(new Callback<JsonObject>(){

			@Override
			public void failure(RetrofitError response) {
				
				Log.e("Places API Error", response.toString());
	           

			}


			@Override
			public void success(JsonObject result, Response response) {
				// TODO Auto-generated method stub
				Log.e("Places API Success", response.toString());
				placesPrediction = result.getAsJsonArray("predictions");
				for (int i = 0; i < placesPrediction.size(); i++) {
					Gson gson = new Gson();
					PlacePrediction pl = gson.fromJson(placesPrediction.get(i), PlacePrediction.class);
					placesList.add(pl);
					suggestionCursor.addRow(new Object[]{i,pl.getDescription()});
		            placesNameList.add(pl.getDescription());
		            
		        }
				if(placesNameList.size() != 0){
					searchAdapter = new PlacesSearchAdapter( context,suggestionCursor, placesNameList);
					shopSearchView.setSuggestionsAdapter(searchAdapter);
				}
			}
			
		});
		
	   
	   
	    

	   
	}

	
}

		@Override
		public void getPropertieSuccess(JsonObject response) {
			// TODO Auto-generated method stub
			Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_LONG).show();
		}

}