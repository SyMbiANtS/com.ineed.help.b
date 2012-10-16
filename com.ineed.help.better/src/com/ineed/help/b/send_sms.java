
package com.ineed.help.b;

import java.security.Timestamp;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import com.ineed.help.b.R;
import com.ineed.help.b.aLocationL;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.app.PendingIntent;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.CallLog.Calls;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.telephony.*;
import android.text.format.Time;
import android.view.ViewGroup.LayoutParams;
import android.util.Log;





public class send_sms extends Activity  
{
	private String sendTXT;
	private String sendPhone;
	private String callPhone;
	private String gpsLoc;
	//private PowerManager.WakeLock wl;
	
	private int powerClick;
	WindowManager.LayoutParams wmlp;
	
	aLocationL locationListener;


@Override
 public void onCreate(Bundle savedInstanceState)
  {
	
	
	
		   super.onCreate(savedInstanceState);
		  
		   	
		      
			 sendTXT= this.getIntent().getExtras().getString("smsTXT");

			sendPhone = this.getIntent().getExtras().getString("smsPhone");
			
			callPhone = this.getIntent().getExtras().getString("callPhone");
			
			wmlp = getWindow().getAttributes();
			
		   	requestWindowFeature(Window.FEATURE_NO_TITLE);
		   
		   	
		    getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); 		    
		    getWindow().addFlags(WindowManager.LayoutParams.FLAG_TOUCHABLE_WHEN_WAKING);
		    getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
		    
		// установить флаг screen_ON
		   //    getWindow().setFlags (WindowManager.LayoutParams.SCREEN_BRIGHTNESS_CHANGED  , 
		//    		WindowManager.LayoutParams.SCREEN_BRIGHTNESS_CHANGED);
	     
		    setContentView(R.layout.working_layout);
	       
	      WindowManager.LayoutParams lp = getWindow().getAttributes();
	        lp.screenBrightness = (float) 0.01;
	        getWindow().setAttributes(lp);

	        final ToggleButton toggleOffButton = (ToggleButton) findViewById(R.id.toggleButton_1);
			toggleOffButton.setChecked(true) ;
			toggleOffButton.setOnLongClickListener(new OnLongClickListener() 
			
			{
				
				public boolean onLongClick (View v) {
					// TODO Auto-generated method stub
				       
				         	finish();	           
				         	return true;
					
				}
			});
				
				toggleOffButton.setOnClickListener(new OnClickListener() 
				
				{
					
					public void onClick (View v) {
						// TODO Auto-generated method stub
					       
						toggleOffButton.setChecked(true) ;      
					           return;
						
					}
				
				});
			
			
	      Button btnClose = (Button) findViewById(R.id.button_1);
	     
	      btnClose.setOnLongClickListener(new OnLongClickListener() 
	      
	      {
				
			public boolean onLongClick(View v) {
					// TODO Auto-generated method stub
				
					doThings();
					doOtherThings();
					System.exit(0);
				//	setOnbg();
				//	moveTaskToBack(true);
				
					return true;
				};
			});
//	 	 LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//	      lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1111, 1, this.locationListener);
	      
	      displayCurrentPhones();
	      displayCurrentPhones();
	     locationListener = new aLocationL();
	     enableGps();
	           
		}




	public void displayCurrentPhones()
	{	
		 if (callPhone != null)
	     {
		
	    	 if (callPhone.length()>1)
	    	 {
	    		 Toast.makeText( getApplicationContext(), getString(R.string.phone_name) +": " +callPhone, Toast.LENGTH_SHORT ).show();
	    	 }
	    	 else
	    	 {
	    		 Toast.makeText( getApplicationContext(), getString(R.string.weird_phone_name) +": " +callPhone, Toast.LENGTH_SHORT ).show();	    		 
	    	 }
	    	 
	    	 
	     }  
		 else
		 {
			 Toast.makeText( getApplicationContext(), getString(R.string.wrong_phone_name) +": " +callPhone, Toast.LENGTH_SHORT ).show();
			 
		 }
		 
/*	      Timer ti = new Timer();
	      	
	       	ti.schedule(new TimerTask() {		
					@Override
					public void run() {	
		*/
	        if (sendPhone!=null)
	        {
	        	 if (sendPhone.length()>1)
		    	 {
	   		     	Toast.makeText( getApplicationContext(), 
	  										getString(R.string.send_name) +": " +sendPhone, Toast.LENGTH_SHORT ).show();
	   		     	
	  					
		    	 }
		    	 else
		    	 {
		    		 Toast.makeText( getApplicationContext(), getString(R.string.weird_send_name) +": " +sendPhone, Toast.LENGTH_SHORT ).show();	    		 
		    	 }
		    	 
		    	 
		     }  
			 else
			 {
				 Toast.makeText( getApplicationContext(), getString(R.string.wrong_send_name) +": " +sendPhone, Toast.LENGTH_SHORT ).show();
				 
			 }
	        
/*					}
	    
					}, 1111);
		      
*/
			
	}


	
	public void ticTac(String isCoord)
	{
		
		gpsLoc = isCoord;
		 Toast.makeText( getApplicationContext(),

				 "TACK "+isCoord,

				 Toast.LENGTH_SHORT ).show();
		 
		 Timer gpsTi = new Timer();
		 

		 gpsTi.schedule(new TimerTask() {
			
			@Override
			public void run() {
				
				
				// TODO Auto-generated method stub
				Toast.makeText( getApplicationContext(),

						 "TICK "+gpsLoc,

						 Toast.LENGTH_SHORT ).show();
				
			}
		}, 22222);
		 
		
		
	}

	public void gpsEnbled(boolean isGps)
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
	
	
	
	
	//public void onBackPressed()   
   // {  
        //do whatever you want the 'Back' button to do  
        //as an example the 'Back' button is set to start a new Activity named 'NewActivity'  
        //this.startActivity(new Intent(YourActivity.this,NewActivity.class));  
       
    //	Intent listenButton = new Intent(Intent.EXTRA_KEY_EVENT);
    //	startActivity(listenButton);
   //     return;  
   // }  
  
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
	    if (event.getKeyCode() == KeyEvent.KEYCODE_POWER) {

	    	powerClick = powerClick+1;
	    	Toast.makeText( getApplicationContext(),  powerClick + " gotcha", Toast.LENGTH_SHORT ).show();
	    	if 		    	
	    	
	    	(powerClick == 3)
	    	{
	    		doOtherThings();
	    		return true;
	    	}
	    	else if (powerClick == 5) {
				powerClick=0;
				return false;
			}
	    	else
	    	{
	    		return true;
	    	}
	        
	    }

	    return super.dispatchKeyEvent(event);
	}

	
	
    //Override the onKeyDown method  
	 @Override
	    public boolean onKeyDown (int keyCode, KeyEvent event)  
	    {  
		
		 super.onKeyDown(keyCode, event);
		 switch (keyCode) 
		 {
		    case KeyEvent.KEYCODE_VOLUME_UP:
		    	

	            //do whatever you want the 'Back' button to do  
	            //as an example the 'Back' button is set to start a new Activity named 'NewActivity'  
	           // this.startActivity(new Intent(YourActivity.this,NewActivity.class));  
	        	// String smsText = getSmsText();
	        	Button bb1 = (Button)findViewById(R.id.button_1);
	        	bb1.setText(R.string.extra_call);
	        //	 Intent sendIntent = new Intent(Intent.ACTION_VIEW);
	         //    sendIntent.putExtra("sms_body", "Content of the SMS goes here..."); 
	         //    sendIntent.setType("vnd.android-dir/mms-sms");
	         //    startActivity(sendIntent);
	        	
/*	            String nuText;
		    	EditText phoneNum = (EditText) findViewById(R.id.editText3);
		    	nuText = phoneNum.getText().toString();
		    	
	        	
		    	  
		    	  EditText smsTextEdit = (EditText) findViewById(R.id.editText2);
		    	  String smsText = smsTextEdit.getText().toString();
		    	*/
		    	
	        	
	        	doThings();
	        	//setOnbg();
	        	
	       	return true;
	       	
		   

	       default:
	        
	        return false;
	         
		 }
		 
	        
	    }
	 
	 
	 private void setOnbg()
	 {
		 
     	Timer ti = new Timer();
    		//startActivity(sendIntent);
         	
         	ti.schedule(new TimerTask() {
 				
 				@Override
 				public void run() {
 					
 					finish();

 				}
 			}, 3333);
 	        
		 
	 }
	 
	/* private void setOnFront()
	 {
	     	Timer ti = new Timer();
	    		//startActivity(sendIntent);
	         	
	         	ti.schedule(new TimerTask() {
	 				
	 				@Override
	 				public void run() {
	 					moveTaskToBack(false);

	 				}
	 			}, 7777);
	 
	 }*/
	 
	 
	 
	 private void doThings()
	 {
		 
	     if (sendPhone != null)
	     {
		
	    	 if (sendPhone.length()>1)
	    	 {
	    		 sendSMS(sendPhone, sendTXT);
	    	 }
	     }  
	        
	        if (callPhone!=null)
	        {
	        	if (callPhone.length()>1)
	        	{
        	Timer ti = new Timer();
   		//startActivity(sendIntent);
        	
        	ti.schedule(new TimerTask() {
				
				@Override
				public void run() {
					performDial();
					
						}
					}, 777);
	        	}
	        }
	 }
	 
	 private void doOtherThings()
	 {
		 
	     if (sendPhone != null)
	     {
		
	    	 if (sendPhone.length()>1)
	    	 {
	    		 sendSMS(sendPhone, "Alf-A-All-A: "+sendTXT);
	    	 }
	     }  
	        
	        if (callPhone!=null)
	        {
	        	if (callPhone.length()>1)
	        	{
	        		sendSMS(callPhone, "Alf-A-All-A: "+sendTXT);
	        	}
	        }
	        
	        Toast.makeText( getApplicationContext(), "iNeedHelp! HelpMe! Alf-A-All-A", Toast.LENGTH_SHORT ).show();
	 }
	 
	 

	//---sends an SMS message to another device---
	    @SuppressWarnings("deprecation")
	    private void sendSMS(String phoneNumber, String message)
	    {        
	    
	     //	Uri uri = Uri.parse("smsto:"+message);
	    	
	    	
	       
	      PendingIntent pi = PendingIntent.getActivity(this, 0,   new Intent(this, me.class), 0);         
	            
	        SmsManager sms = SmsManager.getDefault();
	        
	        Calendar t1 = Calendar.getInstance();
	        String strTime = ""+t1.get(Calendar.DAY_OF_MONTH)+" "+t1.get(Calendar.HOUR)+":"+t1.get(Calendar.MINUTE);
/*	    	 Toast.makeText( getApplicationContext(),

					 "TiCK "+gpsLoc,

					 Toast.LENGTH_SHORT ).show();*/
	        sms.sendTextMessage(phoneNumber, null, message +" "+strTime, pi, null);    
	        
	//        Toast.makeText( getApplicationContext(), "sms sent, starting a call", Toast.LENGTH_SHORT ).show();
	        
/*
	        if (gpsLoc != null){
	        	 Toast.makeText( getApplicationContext(),

						 "TiCK "+gpsLoc,

						 Toast.LENGTH_SHORT ).show();
	        }
	        else
	        {
	       	 LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	        	
	        if(lm.isProviderEnabled(LocationManager.GPS_PROVIDER))
	        { 
	        		        	
	        Location loc = new Location(LocationManager.GPS_PROVIDER);
			 gpsLoc = "GPS location is: " + "Lati = " + 
			 " Longi = " + loc.toString();
			 Toast.makeText( getApplicationContext(),

					 "TACK "+gpsLoc,

					 Toast.LENGTH_SHORT ).show();
			
	            }  
	        	
	        	
	        	
	        	
	        }*/

	        
	      ////   sms.sendTextMessage(phoneNumber, null, gpsLoc +" "+strTime, pi, null);
	        
	       
		    	
		  //  	SmsManager smMan = SmsManager.getDefault() ;
		    	
		    	 
		  //  	  smMan.sendTextMessage(nuText, null, smsText, null, null);
		    	
		  //  	 Intent sendIntent = new Intent(Intent.ACTION_SENDTO, uri);
		    	 
		  //       sendIntent.putExtra("sms_body", "iNeedHelp :"+smsText); 
		  
		        
		       // execSMS.setAction("android.intent.action.VOICE_COMMAND");

		      
		    		
		  //  	startActivity(sendIntent);
	        
	        
	    /*    SmsManager sms = SmsManager.getDefault();
	        ArrayList<String> parts = sms.divideMessage(base64EncodedMessage);
	        sms.sendMultipartTextMessage(phoneNumber, null, parts, null, null);*/

	        
	        
	    }    

	 
	 
	 
	 public void performDial()
	 {
		 
	        String dialNum = callPhone;
		 
		      try {
		    	  
		        startActivityForResult(new Intent("android.intent.action.CALL", Uri.parse("tel:"+dialNum)), 1) ;
		      } catch (Exception e) {
		        e.printStackTrace();
		      }
		   //if
			
	  }
	 
/*	 

	 
	*/

			 


	 public void enableGps()
	 {
		 
		 LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		   	Location loc = new Location(LocationManager.GPS_PROVIDER);
       	loc.setLatitude(0.0);
       	loc.setAltitude(0.0);
       	loc.setTime(System.currentTimeMillis());
       	
     //  	lm.setTestProviderEnabled(LocationManager.GPS_PROVIDER, true);
      // 	lm.setTestProviderLocation(LocationManager.GPS_PROVIDER, loc);
       	lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 33333, 11, this.locationListener);
       	
       	loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
       	
       	gpsLoc = loc.toString();
       	
       	Toast.makeText( getApplicationContext(), 
       			gpsLoc, 
       			Toast.LENGTH_SHORT ).show();
		 
	 }
	 
	 
	 

//    private void sendSMS(String phoneNumber, String message)
//    {        
//        PendingIntent pi = PendingIntent.getActivity(this, 0,
//            new Intent(this, SMS.class), 0);                
//        SmsManager sms = SmsManager.getDefault();
//        sms.sendTextMessage(phoneNumber, null, message, pi, null);        
//    } 
			 
			 
			 
}
