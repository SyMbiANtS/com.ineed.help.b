package com.ineed.help.b;

import android.app.Activity;
import android.app.Application;
import android.widget.Toast;

public class warn_user_with_toast extends send_sms
{

	public void gpsEnabled(boolean isGps)
	{
		String anyG = " no1 nose";
		if ( isGps == true)
		{
			anyG ="Gps Enabled";
		 
		}
		else
		{
			anyG ="Gps Disabled";
		}
		
		Toast.makeText( getApplicationContext(), anyG, Toast.LENGTH_SHORT ).show();
	}
	
	
}
