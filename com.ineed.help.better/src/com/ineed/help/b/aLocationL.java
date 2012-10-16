package com.ineed.help.b;

import java.util.Timer;
import java.util.TimerTask;
import com.ineed.help.b.send_sms;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;
import android.location.Criteria;
import android.app.Application;

public class aLocationL implements LocationListener

{
	String gpsLoc;
	Location gotLoc;
//	 mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, mlocListener);
//	 LocationListener locationListener = new MyLocationListener();


	
	
	 @Override

	 public void onLocationChanged(Location loc)

	 {
	

	gotLoc = loc;

	 String Text = "GPS location is: " +

	 "Lati = " + loc.getLatitude() +

	 " Longi = " + loc.getLongitude();

	 
	 
	 gpsLoc = Text;
	 

	 send_sms tost = new send_sms();
	 tost.ticTac(gpsLoc);
	 }

	 @Override

	 public void onProviderDisabled(String provider)

	 {

	
		 send_sms tost = new send_sms();
		 tost.gpsEnbled(false);

	 }

	 @Override

	 public void onProviderEnabled(String provider)

	 {
		 send_sms tost = new send_sms();
		 tost.gpsEnbled(true);
	

	 }

	 @Override

	 public void onStatusChanged(String provider, int status, Bundle extras)

	 {
		 onLocationChanged(gotLoc);
		 
		 
	 }
	 
	 

	 /* End of Class MyLocationListener */
	 
	

}
