package com.ineed.help.b;

import java.util.Timer;
import java.util.TimerTask;
import com.ineed.help.b.send_sms;
import android.content.Context;
import android.content.Intent;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;
import android.location.Criteria;
import android.app.Application;

public class aLocationL extends Application implements LocationListener

{
	String gpsLoc;
	Location gotLoc;
//	 mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, mlocListener);
//	 LocationListener locationListener = new MyLocationListener();


	
	
	 @Override

	 public void onLocationChanged(Location loc)

	 {
		 	//gotLoc = loc;

         gpsLoc = "GPS location is: Lati = " + loc.getLatitude() + " Longi = " + loc.getLongitude();

	  	// send_sms tost = new send_sms();
	// tost.ticTac(gpsLoc);
	 }

	 @Override

	 public void onProviderDisabled(String provider)

	 {

		 
	//	 warn_user_with_toast tost = warn_user_with_toast.;
	//	 tost.gpsEnabled(false);
//		 send_sms tost = new send_sms();
	//	 tost.gpsEnabled(false);

	 }

	 @Override

	 public void onProviderEnabled(String provider)

	 {
		 
	//	 warn_user_with_toast tost = new warn_user_with_toast();
	//	 tost.gpsEnabled(true);
	//	 send_sms tost = new send_sms();
	//	 tost.gpsEnabled(true);
	

	 }

	 @Override

	 public void onStatusChanged(String provider, int status, Bundle extras)

	 {
		 onLocationChanged(gotLoc);
		 
	 }
	 
	 public String getGpsData()
	 {
		 String thisData = "oops, no gps data";
		 
		 LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	    	
	    	
	    	
		// Criteria criteria = new Criteria();
		 String gR = "gps";
		 Location loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	     // 	loc.setLatitude(0.0);
	     // 	loc.setAltitude(0.0);
	     // 	loc.setTime(System.currentTimeMillis());
		 
	     //	LocationProvider gps = lm.getProvider("gps");
	     	
	     	
	     	
	       	if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER))
	       	{
	       		Toast.makeText( getApplicationContext(), 
	           			"no gps provider aL",
	           			Toast.LENGTH_SHORT ).show();
	       		try
	       		{
	       		
	       			
	       			//    Intent myIntent = new Intent(android.provider.Settings.ACTION_SECURITY_SETTINGS );
	       			//    startActivity(myIntent);
	       			

	       		 lm.removeUpdates(this);
	    		 lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 4444, 3, this);

	  	      	this.onLocationChanged(loc);
	       			
	       		}
	       		catch (Exception e) 
	       		{
	       			Toast.makeText( getApplicationContext(), 
		           			"GOT ERROR iNiT GPS aL"+ e.toString(),
		           			Toast.LENGTH_SHORT ).show();
	       		}
	       	}	
	       	       	else
	       	{
	         lm.removeUpdates(this);
		// lm.setTestProviderEnabled(gpsProvider, true);
		     lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 44444, 3, this);

	      	this.onLocationChanged(loc);
	 //     	Location ewLoc = aloc.; 
		// Location loc = new Location();
		
      	
    //lm.setTestProviderEnabled(LocationManager.GPS_PROVIDER, true);
     // 	lm.setTestProviderLocation(LocationManager.GPS_PROVIDER, loc);
      	
      	
     // 	loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
      	
     // 	gpsLoc = loc.toString();
     // lm. ;

     // 	lm.requestLocationUpdates("gps", 33333, 111, this.locationListener);

	       	}
	      
	       	thisData = "GPS|: Latitude : " + toString().valueOf(lm.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude()  )
	       			+" Longitude : " + toString().valueOf(lm.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude()  )
	       			+" Altitude : " + toString().valueOf(lm.getLastKnownLocation(LocationManager.GPS_PROVIDER).getAltitude()  ) ;
	      	
	      	
		 
		 return thisData;
	 }

	 /* End of Class MyLocationListener http://b3d.mezon.ru/index.php/Blender_Basics_4-rd_edition/Chapter_3-_Creating_and_Editing_Objects
	 
	*/

}
