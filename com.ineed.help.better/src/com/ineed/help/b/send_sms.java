
package com.ineed.help.b;

import java.security.Timestamp;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.security.auth.Destroyable;

import com.ineed.help.b.R;
import com.ineed.help.b.aLocationL;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.app.PendingIntent;
import android.location.Criteria;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
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
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.format.Time;
import android.view.ViewGroup.LayoutParams;
import android.util.Log;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.provider.Contacts.People;




public class send_sms extends Activity  
{
	private String sendTXT;
	private String sendPhone;
	private String callPhone;
	private String gpsLoc;
	private String cellLoc;
	private String passLoc;
	public String sendThis;
	private String sendNext;
    private boolean gpsReady;
	//private PowerManager.WakeLock wl;
	
	private int powerClick;
	WindowManager.LayoutParams wmlp;
	
	aLocationL locationListener;
    Location loc;
    aLocationL aloc;
    LocationManager lm;

@Override
    protected void onDestroy() {
        super.onDestroy();
        // The activity is about to be destroyed.
    }
	
	
@Override
 public void onCreate(Bundle savedInstanceState)
  {

		   super.onCreate(savedInstanceState);
		  
		   	
		      
			sendTXT= this.getIntent().getExtras().getString("smsTXT");

			sendPhone = this.getIntent().getExtras().getString("smsPhone");
			
			callPhone = this.getIntent().getExtras().getString("callPhone");
			
			wmlp = getWindow().getAttributes();

			cellLoc = "";
			
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

	        final ToggleButton toggleOffButton = (ToggleButton) findViewById(R.id.toggleExtraButton_1);
			
	        if (!toggleOffButton.isChecked())
	        {
	        	
	        toggleOffButton.setOnLongClickListener(finishThis);
	        
	        toggleOffButton.setChecked(true) ;
	        
	        }
	        else
	        {
	        	//toggleOffButton.setOnLongClickListener(null);
	        	finish();
	        }
	        
		/*		toggleOffButton.setOnClickListener(new OnClickListener() 
				
				{
					
					public void onClick (View v) {
						// TODO Auto-generated method stub
					       
						toggleOffButton.setChecked(true) ;      
					           return;
						
					}
				
				});
			
			*/
	      Button btnClose = (Button) findViewById(R.id.button_1);
	     
	      btnClose.setOnLongClickListener(new OnLongClickListener() 
	      
	      {
				
			public boolean onLongClick(View v) {
					// TODO Auto-generated method stub
				
					doThings();
					//doOtherThings();
				//	System.exit(0);
				//	setOnbg();
				//	moveTaskToBack(true);
				
					return true;
				}
			});


            /* WorkAround of contact open button */


//	 	 LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//	      lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1111, 1, this.locationListener);
	      
	      displayCurrentPhones();
	//      displayCurrentPhones();
      synchronized(SharedData.globalInstance)
      {
          SharedData.globalInstance.gpsCoordString = "No gps data";
      }
	     enableGps();


            if (gpsReady)
            {
                runTicTac();

            }
	           
		}

private OnLongClickListener finishThis = new OnLongClickListener()
    {
	
	@Override
	public boolean onLongClick(View v) {
		
		final ToggleButton toggleOffButton = (ToggleButton) findViewById(R.id.toggleExtraButton_1);
	   	
		//toggleOffButton.setChecked(false) ;
		finishActivity(0);
		finish();	
		//onDestroy();
	    //Intent thisIntent = getIntent();
	    //finishFromChild(getParent());
		// TODO Auto-generated method stub
		return true;
	}


    };




	public void displayCurrentPhones()
	{
        String showUp;

		 if (callPhone != null)
	     {
		
	    	 if (callPhone.length()>1)
	    	 {
	    		 showUp = getString(R.string.phone_name) +": " +callPhone +"\n";
	    	 }
	    	 else
	    	 {
                 showUp = getString(R.string.weird_phone_name) +": " +callPhone+"\n";
	    	 }

	     }  
		 else
		 {
             showUp = getString(R.string.wrong_phone_name) +": " +callPhone+"\n";
			 
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
                     showUp += " "+  getString(R.string.send_name) +": " +sendPhone;

		    	 }
		    	 else
		    	 {
                     showUp += " "+ getString(R.string.weird_send_name) +": " +sendPhone;
		    	 }
		    	 
		    	 
		     }  
			 else
			 {
                 showUp += " "+ getString(R.string.wrong_send_name) +": " +sendPhone;
				 
			 }
        Toast.makeText( getApplicationContext(), showUp, Toast.LENGTH_LONG ).show();
/*					}
	    
					}, 1111);
		      
*/
			
	}


    public void runTicTac()
    {
        final ToggleButton toggleOffButton = (ToggleButton) findViewById(R.id.toggleExtraButton_1);
        Timer tack = new Timer();
        tack.schedule(new TimerTask()
        {

            @Override
            public void run() {

                if (toggleOffButton.isChecked())
                {
                     ticTac();
                }
            }
        }, 3333);

    }


	
	public void ticTac()
	{
        if (gpsReady)
        {


        new Thread(new Runnable()
        {

            public void run() {
                do
                {
                    Looper.prepare();



                            final String gpsLoc  = getYoLocation();



                    runOnUiThread(new Runnable()
                    {
                        public void run()
                        {

                            sendThis = gpsLoc;
                            Toast.makeText( getApplicationContext(), "TiCK |"+sendThis+"| TaCk", Toast.LENGTH_SHORT ).show();
                        }
                    });


                    Looper.loop();
                }
                while
                        (gpsReady);
                }

        }).start();



        Timer tick = new Timer();
        tick.schedule(new TimerTask()
        {

            @Override
            public void run()
            {
                // TODO Auto-generated method stub
              //  Toast.makeText( getApplicationContext(), "TICK "+gpsLoc, Toast.LENGTH_SHORT ).show();
                new Thread(new Runnable()
                {

                    public void run() {
                        Looper.prepare();

                             runTicTac();

                        Looper.loop();

                    }
                }).start();


            }
        }, 22222);

        }


		
	}




	public void gpsEnabled(boolean isGps)
	{
		String anyG = " no1 nose wru";
		if ( isGps)
		{
			anyG ="GPS Enabled";
            gpsReady= true;
		 
		}
		else
		{
			anyG ="NO GPS!! GPS Disabled";
            gpsReady = false;
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
	        //	Button bb1 = (Button)findViewById(R.id.button_1);
	        //	bb1.setText(R.string.extra_call);
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

           case KeyEvent.KEYCODE_VOLUME_DOWN:

               if (gpsReady)
               {

                   getYoLocation();
                   toastGpsMessage();
               }
	       	
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

 //                   Looper.prepare();
 //                   disableGps();
 //                   Looper.loop();
 					finish();

 				}
 			}, 2333);
 	        
		 
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

         final String lloc;

         if (gpsReady)
         {
             disableGps();
         }
         String sharedCoord;
         synchronized(SharedData.globalInstance) {
             sharedCoord  = SharedData.globalInstance.gpsCoordString;
         }
         if (sharedCoord != null && sharedCoord.length()>11)
         {

                 //lloc = getYoLocation();
                 //        enableGps();
                 //  sendGpsMessage(sendPhone);
                 //    toastGpsMessage();

         /*        if (sendPhone != null)
                 {
                     if (sendPhone.length()>1)
                     {
                         sendGpsMessage(sendPhone);

                     }


                 }
        */

             String tr = sharedCoord ;

  /*
             if (tr.length()>139)
             {

                  tr = sharedCoord.substring(0, 139);
             }
             else
             {
                 if (sendTXT != null)
                 {
                      tr = sharedCoord +" "+ sendTXT;
                         if (tr.length()>139)
                             {
                                 tr = tr.substring(0, 139);
                             }
                 }
             }
*/
             final String sct = tr;
             if (sendPhone != null)
             {
                 if (sendPhone.length()>1)
                 {

                             sendSMS(sendPhone, sct);

                     if (sendTXT.length() > 13)
                     {
                             sendSMS(sendPhone, sendTXT);
                     }
                 }
             }


             }

             else
             {
                 if (sendTXT.length()<1)
                 {
                     sendTXT="Call me, i need help!";
                 }
                 lloc = getCellLocation()+" "+sendTXT;
                 if (sendPhone != null)
                 {
                     if (sendPhone.length()>1)
                     {
                         sendSMS(sendPhone, lloc);
                     }
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
                 }, 999);


             }
             setOnbg();

         }

	 }
	 
	 private void doOtherThings()
	 {

         final String lloc;
         if (gpsReady)
         {
             disableGps();
         }
         String sharedCoord;
         synchronized(SharedData.globalInstance) {
             sharedCoord  = SharedData.globalInstance.gpsCoordString;
         }
         if (sharedCoord != null && sharedCoord.length()>11)
         {
             final String sct = sharedCoord;
             //lloc = getYoLocation();
             //        enableGps();
             //  getYoLocation();
             //  sendGpsMessage(sendPhone);
             //   toastGpsMessage();

             if (sendPhone != null)
             {
                 if (sendPhone.length()>1)
                 {
/*

                     Timer ti = new Timer();
                     //startActivity(sendIntent);

                     ti.schedule(new TimerTask() {

                         @Override
                         public void run() {
*/
                             sendSMS(sendPhone, sct);
                     if (sendTXT.length() > 13)
                     {
                          sendSMS(sendPhone, sendTXT);
                     }
/*
                         }
                     }, 999);

                     */
/*
                     Uri uri = Uri.parse("smsto:"+sendPhone);
                     Intent smssend = new Intent(Intent.ACTION_SENDTO, uri);
                     smssend.putExtra("sms_body", sct);
                     smssend.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                     startActivity(smssend);
*/

                 }
             }
             if (callPhone != null)
             {
                 if (callPhone.length()>1)
                 {

                     Timer ti = new Timer();
                     //startActivity(sendIntent);

                     ti.schedule(new TimerTask() {

                         @Override
                         public void run() {
                             sendSMS(callPhone, sct);
                             if (sendTXT.length() > 13)
                             {
                                  sendSMS(callPhone, sendTXT);
                             }
                         }
                     }, 999);


                     setOnbg();
                    // sendGpsMessage(callPhone);
                 }
             }




         }
         else
         {

             if (sendTXT.length()<1)
             {
                 sendTXT="Call me, i need help!";
             }


             lloc = getCellLocation()+" "+sendTXT;
             if (sendPhone != null)
             {
                 if (sendPhone.length()>1)
                 {
                     sendSMS(sendPhone, lloc);
                 }
             }
             if (callPhone != null)
             {
                 if (callPhone.length()>1)
                 {

                     sendSMS(callPhone, lloc);
                 }
             }

         }

/*
             if (sendPhone != null)
             {

                 if (sendPhone.length()>1)
                 {

                    // sendSMS(sendPhone, "HelpMe: "+sendTXT);
                     try {
                     new Thread(new Runnable()
                     {

                         public void run() {
                             Looper.prepare();
                             final String sTxt = getYoLocation();
                             Looper.loop();
                             sendSMS(sendPhone, sTxt+" "+sendTXT);
                         }
                     }).start();

                     /*

                         Timer ti = new Timer();
                         //startActivity(sendIntent);

                         ti.schedule(new TimerTask() {

                             @Override
                             public void run() {
                                 sendSMS(sendPhone, lloc+" "+sendTXT);

                             }
                         }, 999);



                     } catch (Exception e) {
                         e.printStackTrace();
                     }
                 }
	    	 }


	        if (callPhone != null)
	        {
	        	if (callPhone.length()>1)
	        	    {
                    try {



                        Timer ti = new Timer();
                        //startActivity(sendIntent);

                        ti.schedule(new TimerTask() {

                            @Override
                            public void run() {
                                sendSMS(sendPhone, lloc+" "+sendTXT);

                            }
                        }, 999);


                     new Thread(new Runnable()
                     {

                           public void run()
                            {
                                           Looper.prepare();
                                                        final String sTxt = getYoLocation();
                                            Looper.loop();
                                            sendSMS(sendPhone, sTxt+" "+sendTXT);
                                            }
                            }).start();
                    }
                 catch (Exception e) {
                        e.printStackTrace();
                    }
                    }

                //    setOnbg();
	        	//  sendGpsMessage(callPhone);
	        	//	sendSMS(callPhone, "Alf-A-All-A: "+sendTXT);
	        	}
*/
	        
	 //       Toast.makeText( getApplicationContext(), sendTXT, Toast.LENGTH_LONG ).show();
	 }
	 
	 
	 public void sendGpsMessage(String phoneNumber)
	 {

         final String phNum = phoneNumber;
         disableGps();

                 String sharedCoord;
                 synchronized(SharedData.globalInstance) {
                     sharedCoord  = SharedData.globalInstance.gpsCoordString;
                 }



                 sendThis = sharedCoord;
                 String seT = sharedCoord;


 /*        if (sendThis != null)
         {
*/

                 if ( 140 - sendTXT.length() > seT.length())

                 {

                     seT += " "+ sendTXT;

                 }

                 else
                 {


                     final String sentxt = sendTXT;
/*                     Timer ti = new Timer();
                     //startActivity(sendIntent);

                     ti.schedule(new TimerTask() {

                         @Override
                         public void run() {
                             Looper.prepare();
*/
                                         sendSMS(phNum, sentxt);


/*

                         }
                     }, 733);

*/
                 }


                 final String seN = seT;
/*
                 Timer tis = new Timer();
                 //startActivity(sendIntent);

                 tis.schedule(new TimerTask() {

                     @Override
                     public void run() {

                            Looper.prepare();
 */
                                      sendSMS(phNum, seN);


                         /*
                         runOnUiThread(new Runnable()
                         {
                             public void run()
                             {
                                    Toast.makeText( getApplicationContext(), "TiCK |"+seN+"| TaCk", Toast.LENGTH_SHORT ).show();
                             }
                         });
                         */
/*                     }
                 }, 433);
*/

                 ToggleButton tb = (ToggleButton) findViewById(R.id.toggleExtraButton_1);
                 tb.setChecked(false);









     /*    }
        else
        {
            sendSMS(phoneNumber, sendTXT);
        }
*/
	 }
	 

    public String getCellLocation()
    {
        String cellLoc ="";

        String cdmaLoc = toString().valueOf(new CdmaCellLocation());
        String gsmLoc = toString().valueOf(new GsmCellLocation());


        if (cdmaLoc.length() > 11)	{	cellLoc += " CDMA:"+cdmaLoc; 	}
        if (gsmLoc.length() > 11)	{	cellLoc += " |GSM:" +gsmLoc;   	}

        return cellLoc;

    }
	 
	 public void toastGpsMessage()
	 {

		 if (140 - sendTXT.length() > sendThis.length())
    		 
		 {
			 sendThis += sendTXT;
		 
			 
			 Toast.makeText( getApplicationContext(), sendThis+" "+ toString().valueOf(sendThis.length()), Toast.LENGTH_LONG ).show();
			 if (sendNext.length() > 11)
			 {
				 Toast.makeText( getApplicationContext(), sendNext, Toast.LENGTH_LONG ).show();
			 }
		 }
		 else
		 {
			 if (140 - sendTXT.length() > sendNext.length())
			 {
				 sendNext += sendTXT;
				 Toast.makeText( getApplicationContext(), sendThis, Toast.LENGTH_LONG ).show();
			 }
			 else
			 {
				 Toast.makeText( getApplicationContext(), sendThis, Toast.LENGTH_LONG ).show();
				 Toast.makeText( getApplicationContext(), sendTXT, Toast.LENGTH_LONG ).show();
			 }
			 
		 }
	 }
	 
	 
	 

	//---sends an SMS message to another device---
	    @SuppressWarnings("deprecation")
	    private void sendSMS(String phoneNumber, String message)
	    {        
	    
	     //	Uri uri = Uri.parse("smsto:"+message);

            Intent smSend = new Intent(this, me.class);
         //   smSend.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

	      PendingIntent pi = PendingIntent.getActivity(this, 0, smSend, 0);
	            
	        SmsManager sms = SmsManager.getDefault();
	        
//	        Calendar t1 = Calendar.getInstance();
//	        String strTime = ""+t1.get(Calendar.DAY_OF_MONTH)+" "+t1.get(Calendar.HOUR)+":"+t1.get(Calendar.MINUTE);
/*	    	 Toast.makeText( getApplicationContext(),

					 "TiCK "+gpsLoc,

					 Toast.LENGTH_SHORT ).show();*/


	        sms.sendTextMessage( phoneNumber, null, message, pi, null);    // +" "+strTime
            final String mssg = message;
            runOnUiThread(new Runnable()
            {
                public void run()
                {
	                  Toast.makeText( getApplicationContext(), "SMS "+mssg+ " sent, starting a call", Toast.LENGTH_SHORT ).show();
                }
            });
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
		 

		      try
              {

                          startActivityForResult(new Intent("android.intent.action.CALL", Uri.parse("tel:"+callPhone)), 1) ;


		      } catch (Exception e) {
		        e.printStackTrace();
		      }
		   //if
			
	  }
	 
/*	 

	 
	*/

			 


	 public void enableGps()
	 {
		        gpsReady = false;

		    	aloc = new aLocationL();
		    	lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		    	
		    	
		    	
				 Criteria criteria = new Criteria();
				 String gR = "gps";
				 try
				 {

                     loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                     if (loc != null)
                     {
                         loc.setTime(System.currentTimeMillis());
                   //      loc.getLatitude();
                  //       loc.getLongitude();
                     }
                     else
                     {
                      //   loc.setLatitude(0.0);
                      //   loc.setAltitude(0.0);
                     }
                     //	LocationProvider gps = lm.getProvider("gps");


                     if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER) != true)
			       	{
                        synchronized(SharedData.globalInstance)
                        {
                            SharedData.globalInstance.gpsCoordString = "No gps data";
                        }
			       		Toast.makeText( getApplicationContext(), 
			           			"no gps provider", 
			           			Toast.LENGTH_SHORT ).show();
			       		try
			       		{
			       		
			       			
			       			//    Intent myIntent = new Intent(android.provider.Settings.ACTION_SECURITY_SETTINGS );
			       			//    startActivity(myIntent);
			       			


			       		 lm.removeUpdates(aloc);
			    		 lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 24444, 3, aloc);
			    		 
			    
			  	      	aloc.onLocationChanged(loc);
                            synchronized(SharedData.globalInstance)
                            {
                                SharedData.globalInstance.gpsCoordString = "No gps data";
                            }
			       			gpsReady=true;
			       		}
			       		catch (Exception e) 
			       		{
			       			Toast.makeText( getApplicationContext(), 
				           			"GOT ERROR iNiT GPS g "+ e.toString(),
				           			Toast.LENGTH_SHORT ).show();
			       		}
			       	}	
			       	       	else
			       	{
			     lm.removeUpdates(aloc);
				// lm.setTestProviderEnabled(gpsProvider, true);
				 lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 24444, 3, aloc);

			      aloc.onLocationChanged(loc);
                        synchronized(SharedData.globalInstance)
                        {
                            SharedData.globalInstance.gpsCoordString = "GPS | Lat:"+ toString().valueOf(loc.getLatitude()) +" Long "
                                    + toString().valueOf(loc.getLongitude()) +" Alt "+ toString().valueOf(loc.getAltitude()) ;
                        }
                        gpsReady=true;
			 //     	Location ewLoc = aloc.; 
				// Location loc = new Location();
				
		      	
		    //lm.setTestProviderEnabled(LocationManager.GPS_PROVIDER, true);
		     // 	lm.setTestProviderLocation(LocationManager.GPS_PROVIDER, loc);
		      	
		      	
		     // 	loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		      	
		     // 	gpsLoc = loc.toString();
		     // lm. ;

		     // 	lm.requestLocationUpdates("gps", 33333, 111, this.locationListener);
		       
		      	
		      	
			       	}
			      
				 }
				 catch (Exception e) 
		       		{
		       			Toast.makeText( getApplicationContext(), 
			           			"GOT ERROR iNiT GPS 1 "+ e.toString(),
			           			Toast.LENGTH_LONG ).show();
		       		}
	       	
	        gpsEnabled(gpsReady);
	 }

    public void disableGps()
    {
        gpsReady = true;

        try
        {
        aloc = new aLocationL();
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        aloc.onProviderDisabled(LocationManager.GPS_PROVIDER);
        lm.removeUpdates(aloc);
        gpsReady=false;


        }
        catch (Exception e)
        {
            Toast.makeText( getApplicationContext(),
                    "GOT ERROR iNiT GPS 1 "+ e.toString(),
                    Toast.LENGTH_LONG ).show();
        }

        gpsEnabled(gpsReady);
    }
	 
	 public String getYoLocation()
	 {
         sendThis = "HelpME!";
         sendNext = "iNeedHelp!";
		 try
		 {
		 
		 lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


             aloc = new aLocationL();
             lm.removeUpdates(aloc);
             lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 777, 11, aloc);
             loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
             aloc.onLocationChanged(loc);


		 
		 	String gR = "gps";
		 	String pR = "passive";
	       //	String gotProviders = toString().valueOf(lm.getProviders(true));
			 //gps data      	
	       	String gLat = toString().valueOf(loc.getLatitude() );
	       	String gLong = toString().valueOf(loc.getLongitude()  );
	       	String gAlt = toString().valueOf(loc.getAltitude()  );
			   String gpsLoc1 = " GPS|: Lat: " + gLat +" Long: " +  gLong +" Alt: " + gAlt;
             //
             synchronized(SharedData.globalInstance)
             {
                 SharedData.globalInstance.gpsCoordString = gpsLoc1 ;
             }
			      	
			   gpsLoc = " GPS|Lat:" + gLat +" Long:" + gLong +" Alt:" + gAlt ;
			    //passive data
             Location  ploc;
             LocationManager lmp = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
             lmp.removeUpdates(aloc);
             lmp.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 777, 11, aloc);
			      	ploc = lmp.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
			    	String pLat = toString().valueOf(ploc.getLatitude() );
			       	String pLong = toString().valueOf(ploc.getLongitude()  );
			       	String pAlt = toString().valueOf(ploc.getAltitude()  );
             lmp.removeUpdates(aloc);
             String gpsLoc2 = " ";
             passLoc = " ";
			 if (!pLat.matches(gLat) || !pLong.matches(gLong))
             {

			   gpsLoc2 = " Passive|: Lat: " + pLat +" Long: " + pLong +" Alt: " + pAlt;
             //
                 synchronized(SharedData.globalInstance)
                 {
                     SharedData.globalInstance.gpsCoordString += gpsLoc2 ;
                 }
			   passLoc = " Pas|Lat" + pLat + " Long" + pLong +" Alt" + pAlt; //	 ;
             }
             else
             {
                 passLoc = "";
             }

			      	
			    //    Toast.makeText( getApplicationContext(), gotProviders, Toast.LENGTH_SHORT ).show();
			      	
			   //cell data   	
			 String cellLoc = getCellLocation();
			       	
		//	      	Toast.makeText( getApplicationContext(), gpsLoc1, Toast.LENGTH_LONG ).show();
		//	      	Toast.makeText( getApplicationContext(), gpsLoc2, Toast.LENGTH_SHORT ).show();
		//	      	Toast.makeText( getApplicationContext(), cellLoc, Toast.LENGTH_SHORT ).show();
				    
			//      	Toast.makeText( getApplicationContext(), gotProviders +" | \n"+gpsLoc +" | \n"
			//      	+gpsLoc2+ " | \n"+cellLoc, Toast.LENGTH_SHORT ).show();
			      	
			      	
			      	
			String zeroStr = "helpme!";
			      
			      
			      if (!pLat.matches(gLat) || !pLong.matches(gLong) || !pAlt.matches(gAlt)) //
			      {
			      	if ((zeroStr+= gpsLoc + cellLoc + passLoc).length() < 140)
			      	{
			      		if (gpsLoc.length()> 16)    {	sendThis += gpsLoc;		}
			      		if (cellLoc.length() > 7)	{	sendThis += cellLoc;	}				      	
			      		if (passLoc.length()>16)	{  	sendThis += passLoc;	}
			      	}
			      	else
			      	{
			      		if (gpsLoc.length()> 16)    {	sendThis += gpsLoc;		}
                        if (passLoc.length()>16)	{  	sendThis += passLoc;	}

			      	}
			      		
			      }
			      else
			      {
			    		if (gpsLoc.length()> 16)    {	sendThis += gpsLoc;		}
                        if (cellLoc.length() > 7)	{	sendThis += cellLoc;	}
                  }

             synchronized(SharedData.globalInstance)
             {
                 SharedData.globalInstance.gpsCoordString = sendThis ;
             }

		 }
		 catch
		 (Exception e) 
    		{
    			Toast.makeText( getApplicationContext(), 
	           			"GOT ERROR iNiT GPS 2"+ e.toString(),
	           			Toast.LENGTH_LONG ).show();
    		}
			      
		 return sendThis;
	 }
	 
	 
	 

//    private void sendSMS(String phoneNumber, String message)
//    {        
//        PendingIntent pi = PendingIntent.getActivity(this, 0,
//            new Intent(this, SMS.class), 0);                
//        SmsManager sms = SmsManager.getDefault();
//        sms.sendTextMessage(phoneNumber, null, message, pi, null);        
//    } 
			 
			 
			 
}
