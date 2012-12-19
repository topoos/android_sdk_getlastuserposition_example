package com.topoos.showpos;

import java.util.ArrayList;

import topoos.Exception.TopoosException;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Handler.Callback;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;

/**
 * This app is a very simple project that show an example about using Get Position SDK Operation
 * 
 * www.topoos.com
 * Read documentation and examples in http://docs.topoos.com
 * 
 * 
 * 
 * Note:
 * You must get a Google Maps Api key before use this code: 
 * 		https://developers.google.com/maps/documentation/android/mapkey
 * About publishing app with Google Map
 *		http://developer.android.com/intl/es/tools/publishing/app-signing.html
 */
public class MainActivity extends MapActivity {

	private static final String APPTOKEN_USER = "XXXXXXXXXXXXXXXXXXXXXXXXX";
	
	public final int WORKER_ERROR = -1;
	public final int WORKER_OK = 1;

	private Handler handler = new Handler(new WorkerResultMessageCallback());
	
    private MapView mMapView;
	private MapController mMapController;
	
	Thread mWorkerThread;
	boolean mFetchPositions = true;	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //build and save topoos access token in preferences
        topoos.AccessTokenOAuth token = new topoos.AccessTokenOAuth(APPTOKEN_USER);
        token.save_Token(this);

        mMapView = (MapView)findViewById(R.id.mapview);
        mMapController = mMapView.getController();
        mMapController.setZoom(20);
        
        mWorkerThread = new Thread(new PositionsFetchBackground());
		mWorkerThread.start();
    }
    

    @Override
    protected void onDestroy() {

    	super.onDestroy();
    	
    	mFetchPositions = false;
    }
    

	private class WorkerResultMessageCallback implements Callback {

		public boolean handleMessage(Message arg0) {
			switch(arg0.what)
			{
			case WORKER_ERROR:
				Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();
				break;
			case WORKER_OK:
				centerMapAndRefresh((topoos.Objects.Position)arg0.obj);
				break;
			}
			return true;
		}
	}
	
	/**
	 * Get the user current location associated with the token
	 */
	private class PositionsFetchBackground implements Runnable {
		
		public void run(){
			try
			{
				String currentUserIdentifier = topoos.Users.Operations.Get(MainActivity.this, "me").getId();
				
				while(mFetchPositions)
				{
					Message msg = new Message();
					try {
						msg.obj = topoos.Positions.Operations.GetLastUser(
								MainActivity.this, currentUserIdentifier);
						
						msg.what = WORKER_OK;
					} catch (TopoosException te)
					{
						Log.e("topoos","The AccessToken is valid and there is any position registered?");
						msg.what = WORKER_ERROR;
						te.printStackTrace();
					} catch (Exception e) {
						msg.what = WORKER_ERROR;
						e.printStackTrace();
					}
					
					try {
						handler.sendMessage(msg); //Response to the main thread
						Thread.sleep(5 * 1000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}

	private void centerMapAndRefresh(topoos.Objects.Position pos)
	{
		if (pos != null)
		{
			GeoPoint geoPoint = new GeoPoint(
		     	(int)(pos.getLatitude()*1000000),
		     	(int)(pos.getLongitude()*1000000));
			 
			 Drawable drawable = this.getResources().getDrawable(R.drawable.ic_marker);
			 CustomMapMarker itemizedoverlay = new CustomMapMarker(drawable, this);
			 OverlayItem overlayitem = new OverlayItem(geoPoint, "", "");
			 itemizedoverlay.addOverlay(overlayitem);
			 
			 mMapView.getOverlays().clear();
			 mMapView.getOverlays().add(itemizedoverlay);
			 
			 mMapController.animateTo(geoPoint);
		}
	}
	
	/**
	 * Custom Map Marker for Google Maps
	 */
	@SuppressWarnings("rawtypes")
	public class CustomMapMarker extends ItemizedOverlay {

	    private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	    public CustomMapMarker(Drawable defaultMarker) {
	        super(boundCenterBottom(defaultMarker));
	    }

	    public CustomMapMarker(Drawable defaultMarker, Context context) {
	        this(defaultMarker);
	    }

	    public void addOverlay(OverlayItem item) {
	        mOverlays.add(item);
	        populate();
	    }

	    @Override
	    protected OverlayItem createItem(int i) {
	        return mOverlays.get(i);
	    }
	    @Override
	    public int size() {
	        return mOverlays.size();
	    }
	    @Override
	    protected boolean onTap(int index) { 
	        return true;
	    }
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
    
}
