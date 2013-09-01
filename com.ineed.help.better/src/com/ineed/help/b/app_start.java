package com.ineed.help.b;




import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.ineed.help.b.R;
import com.ineed.help.b.aLocationL;
import com.ineed.help.b.ContactList;

import android.os.Build;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Application;
import android.content.CursorLoader;
import android.net.Uri;
import android.os.Bundle;

import android.provider.ContactsContract;
import android.provider.SyncStateContract.Constants;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.bluetooth.*;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.app.PendingIntent;
import android.location.*;
import android.location.GpsStatus.Listener;
import android.telephony.*;
import android.telephony.gsm.GsmCellLocation;
import android.telephony.cdma.CdmaCellLocation;
import android.text.Editable;
import android.text.method.KeyListener;
import android.hardware.*;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.util.Log;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;

import java.util.Locale;



public class app_start extends Activity implements OnCheckedChangeListener 
{
	  
    public String ContactPhone = "0";
	public String ContactSms = "0";
    public int ContactSwap = 0;
	
/*	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	

		        
		        
		        //Configuration sysConfig = getResources().getConfiguration();
		        // newConfig.locale = Locale.TRADITIONAL_CHINESE;
	
		
		
	}*/
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
    	  super.onCreate(savedInstanceState);

    	  /*
    	   *   	  
    	Locale locale = new Locale("ru");
  		Locale.setDefault(locale);
  		Configuration config = new Configuration();
  		config.locale = locale;
  		getResources().updateConfiguration(config,null);

   	  String languageToLoad  = "ru";
    	    Locale locale = new Locale(languageToLoad); 
    	    Locale.setDefault(locale);
    	    Configuration config = new Configuration();
    	    config.locale = locale;
    	    getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());

*/

    	  Resources res =  getResources();
  		Configuration newConfig = new Configuration(res.getConfiguration());
          Locale le_locale =  res.getConfiguration().locale;
          String ll = le_locale.getDisplayName().toLowerCase();
  	     if (ll.contains("ru") ||ll.contains("ru_ru") || ll.contains("russian") || ll.contains("русский"))
  	     {
  	        	newConfig.locale = new Locale("ru");
  	            res.updateConfiguration(newConfig, null);
  	            getBaseContext().getResources().updateConfiguration(newConfig, 
  		        	    getBaseContext().getResources().getDisplayMetrics());
  	        
  	     }
  	    else
  	     {
  	        		 newConfig.locale =  new Locale("en");
  	        	        res.updateConfiguration(newConfig, null);
  	       	        getBaseContext().getResources().updateConfiguration(newConfig, 
  	        	        	    getBaseContext().getResources().getDisplayMetrics());
  	     }

  	     
        setContentView(R.layout.tab_main);





        
        TabHost tabs = (TabHost) findViewById(android.R.id.tabhost);

		tabs.setup();

		TabHost.TabSpec spec = tabs.newTabSpec("Сюда вписать телефон лучше 112");
/** тут могут быть различные звуки голуби мыши птицы кошки, то что не будет как будто вызывать вопросов, 
 * но всё таки сообщит о том, что звонок дошёл или началась запись разговора*/
		
		spec.setContent(R.id.tab1);
		spec.setIndicator(getResources().getString(R.string.tab1_text));
		tabs.addTab(spec);

		spec = tabs.newTabSpec("Здесь смс на всякий случай кому-то из друзей");
		spec.setContent(R.id.tab2);
		spec.setIndicator(getResources().getString(R.string.tab2_text));
		tabs.addTab(spec);

		spec = tabs.newTabSpec("Здесь можно настроить почтовый ящик на который отправится сообщение, например в вк оно стрельнет на стенку");
		spec.setContent(R.id.tab3);
		spec.setIndicator(getResources().getString (R.string.tab3_text));
		tabs.addTab(spec);

		//tabs.setCurrentTab(1);
		
		String incomingNumber = new Intent().getStringExtra(Intent.EXTRA_PHONE_NUMBER);
		
		if (incomingNumber != null)
		{
		
		EditText et = (EditText) findViewById(R.id.editText1);
		et.setText(incomingNumber);
		}
		
		//closeBtnBehavior(0);




/*        Button btnContact = (Button) findViewById(R.id.btn_contact);
		
		btnContact.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 List numberList = new ArrayList();
			        Uri baseUri = ContentUris.withAppendedId(Contacts.CONTENT_URI,contactId);
			        Uri targetUri = Uri.withAppendedPath(baseUri,Contacts.Data.CONTENT_DIRECTORY);
			        Cursor cursor = getContentResolver().query(targetUri,
			                    new String[] {Phone.NUMBER},Data.MIMETYPE+"='"+Phone.CONTENT_ITEM_TYPE+"'",null, null);
			        startManagingCursor(cursor);
			        while(cursor.moveToNext()){
			            numberList.add(cursor.getString(0));
			        }
			        cursor.close();

			}
		});*/
		
		
		
      //  btnClose.setText(le_locale.getDisplayName());
        
      
	//	Toast.makeText( getApplicationContext(), "this on create ends", Toast.LENGTH_SHORT ).show();
        
        
        
    }
    
    @Override
	protected void onResume() {
    	super.onResume();
    //	Toast.makeText( getApplicationContext(), "this on resume", Toast.LENGTH_SHORT ).show();
    }
    
    @Override
	protected void onPause() {
    	super.onPause();
   // 	Toast.makeText( getApplicationContext(), "this on pause", Toast.LENGTH_SHORT ).show();
    }
    
    
    @Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		//Toast.makeText( getApplicationContext(), "this on start start", Toast.LENGTH_SHORT ).show();
		ToggleButton toggleHelpButton;
		
		toggleHelpButton = (ToggleButton) findViewById(R.id.toggleButton1);
		TabHost tabs = (TabHost) findViewById(android.R.id.tabhost);
		if (toggleHelpButton.isEnabled() == true)
		{
			
			if (toggleHelpButton.isChecked()==false)
			{
				toggleHelpButton.setOnCheckedChangeListener(this);
		//		Toast.makeText( getApplicationContext(), "this 22222", Toast.LENGTH_SHORT ).show();
				closeBtnBehaviour(1);
				tabs.setCurrentTab(1);
				
			}
			else
			{
		//		Toast.makeText( getApplicationContext(), "this 33333", Toast.LENGTH_SHORT ).show();
			toggleHelpButton.setOnCheckedChangeListener(null);
				
			closeBtnBehaviour(0);
			tabs.setCurrentTab(1);
			}
			
			
			//toggleHelpButton.setChecked(true);
		//toggleHelpButton.setEnabled(true);
		
		//closeBtnBehavior(1);
		
		//
		//toggleHelpButton.setEnabled(false);
		
		//toggleHelpButton.setOnCheckedChangeListener(this);
		}
		else
		{
		//	Toast.makeText( getApplicationContext(), "this 11111", Toast.LENGTH_SHORT ).show();
			
			toggleHelpButton.setEnabled(true);
			toggleHelpButton.setChecked(true);
			closeBtnBehaviour(1);
			toggleHelpButton.setOnCheckedChangeListener(this);
			
			//toggleHelpButton.setOnCheckedChangeListener(null);
		}
		
		Button checkGps = (Button) findViewById(R.id.buttonCheckGps);
		
		checkGps.setOnClickListener(getGps);
		
		/*
		Locale locale = new Locale("ru");
		Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;
		getResources().updateConfiguration(config,null);
		
		*/
        Button btnContact = (Button) findViewById(R.id.btn_contact);

        btnContact.setOnClickListener(openContact);

        Button btnSms = (Button) findViewById(R.id.buttonSms);

        btnSms.setOnClickListener(contactSms);


	}
    
    private OnClickListener getGps = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			getNewGps();
			

			
		}
	};

    private OnClickListener openContact = new OnClickListener(){

        @Override
        public void onClick(View v)
        {
            ContactSwap = 3;
            getContactNum();

        }


    };

    private OnClickListener contactSms = new OnClickListener(){

        @Override
        public void onClick(View v)
        {
            ContactSwap = 7;
            getContactNum();

        }


    };


    final static int PICK_CONTACT_REQUEST = 0;


    public String getContactNum()
    {
        String Contact1 = "007";
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);

        try
        {
            startActivityForResult(intent, PICK_CONTACT_REQUEST  );


        }
        catch (Exception e)
        {

        }
        finally {

        }

        return Contact1;
    }
    @Override

    public void onActivityResult(int reqCode, int resCode, Intent data) {
        super.onActivityResult(reqCode, resCode, data);
        String ContNum= "112";
        switch (reqCode) {
            case (PICK_CONTACT_REQUEST) :

                if (resCode == Activity.RESULT_OK) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                    {
                        ContNum = getContactNumber11(data);

                        setPhoneNumber(ContNum);
                    }
                }
                break;
        }
    }


    @TargetApi(11)
    public String getContactNumber11(Intent da)
    {
        String ContactNumber = "11";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            CursorLoader cL = new CursorLoader(this, da.getData(), null, ContactsContract.Data._ID , null , null);


            try
            {
                Cursor c = cL.loadInBackground();
                c.moveToFirst();
                String CiD = c.getString(c.getColumnIndex(ContactsContract.Data._ID)) ;

                ContactNumber = c.getString(c.getColumnIndex(ContactsContract.Data.LOOKUP_KEY)) ;

                Uri urci = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                CursorLoader ph = new CursorLoader(this, urci, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+CiD , null , null);

                Cursor phNu = ph.loadInBackground();
                phNu.moveToFirst();
                ContactNumber = phNu.getString(phNu.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));


            }
            catch (Exception e)
            {
                Toast.makeText(getApplicationContext(),
                        "GOT ERROR " + e.toString(),
                        Toast.LENGTH_LONG).show();
            }

        }

        return ContactNumber;

    }

    private void setPhoneNumber(String phNum)
    {
        phNum = PhoneNumberUtils.stripSeparators(phNum);
        EditText et = (EditText)findViewById(R.id.editText1);
        EditText ste = (EditText)findViewById(R.id.editText3);

        if (ContactSwap == 3)
        {
        et.setText(phNum);
        ContactPhone = phNum;
        }
        if (ContactSwap == 7)
        {
            ste.setText(phNum);
            ContactSms = phNum;

        }
    }




    private void getNewGps()
    {
    	aLocationL aloc = new aLocationL(); 
    	LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    	
    	
    	
		// Criteria criteria = new Criteria();
		 String gR = "gps";
         Location loc;
    try
        {
		 
		 loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	      //	loc.setLatitude(0.0);
	      //	loc.setAltitude(0.0);
	      //	loc.setTime(System.currentTimeMillis());
	     //	LocationProvider gps = lm.getProvider("gps");
	     	
	     	
	     	
	       	if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER) != true)
	       	{
	       		Toast.makeText( getApplicationContext(), 
	           			"no gps provider", 
	           			Toast.LENGTH_LONG ).show();
	       		try
	       		{
	       		
	       			
	       			//    Intent myIntent = new Intent(android.provider.Settings.ACTION_SECURITY_SETTINGS );
	       			//    startActivity(myIntent);
	       			


	       		 lm.removeUpdates(aloc);
	    		 lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 77, 11, aloc);
	    		 
	    
	  	      	aloc.onLocationChanged(loc);
	       			
	       		}
	       		catch (Exception e) 
	       		{
	       			Toast.makeText( getApplicationContext(), 
		           			"GOT ERROR iNiT GPS 3 "+ e.toString(),
		           			Toast.LENGTH_LONG ).show();
	       		}
	       	}	
	       	       	else
	       	{
	            lm.removeUpdates(aloc);
		        // lm.setTestProviderEnabled(gpsProvider, true);
		         lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 777, 11, aloc);

                if (loc != null)
                 {
	      	        aloc.onLocationChanged(loc);
	              //     	Location ewLoc = aloc.;
	                    //loc = new Location(0,0);
                 }
                    else
                        {

                              lm.setTestProviderEnabled(LocationManager.GPS_PROVIDER, true);
         	                  lm.setTestProviderLocation(LocationManager.GPS_PROVIDER, loc);
      	
         	                    loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        }
     // 	gpsLoc = loc.toString();
     // lm. ;

     // 	lm.requestLocationUpdates("gps", 33333, 111, this.locationListener);

      	
	       	}
	      
	       	String gotProviders = toString().valueOf(lm.getProviders(true));
	       	
	       	String gpsLoc = "GPS|: Lat: " + toString().valueOf(lm.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude()  )
	       			+" Long: " + toString().valueOf(lm.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude()  )
	       			+" Alt: " + toString().valueOf(lm.getLastKnownLocation(LocationManager.GPS_PROVIDER).getAltitude()  ) ;

	      	String gpsLoc2 = "Passive|: Lat: " + toString().valueOf(lm.getLastKnownLocation("passive").getLatitude()  )
	       			+" Long: " + toString().valueOf(lm.getLastKnownLocation("passive").getLongitude()  )
	       			+" Alt: " + toString().valueOf(lm.getLastKnownLocation("passive").getAltitude()  ) ;
	      	
	      	
	      //  Toast.makeText( getApplicationContext(), gotProviders, Toast.LENGTH_SHORT ).show();
	      //	Toast.makeText( getApplicationContext(), gpsLoc2, Toast.LENGTH_LONG ).show();
	      	
	       	
	       	String cellData = "|CDMA:"+toString().valueOf(new CdmaCellLocation())
	      			+" |GSM:" +toString().valueOf(new GsmCellLocation());
	       	
	      	Toast.makeText( getApplicationContext(), gotProviders +" | \n"+gpsLoc +" | \n"+gpsLoc2+" | \n"+cellData, Toast.LENGTH_LONG ).show();
	      	
	    //  	Toast.makeText( getApplicationContext(), cellData, Toast.LENGTH_LONG ).show();
		       
        }
            catch (Exception e)
	        {
	            	Toast.makeText( getApplicationContext(),
   	            		"GOT ERROR iNiT GPS 4 "+ e.toString(),
             			Toast.LENGTH_LONG ).show();
	        }
    	
    }
    
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		//Toast.makeText( getApplicationContext(), "this on stop start", Toast.LENGTH_SHORT ).show();

		ToggleButton toggleHelpButton;
		
		toggleHelpButton = (ToggleButton) findViewById(R.id.toggleButton1);

		closeBtnBehaviour(1);
		toggleHelpButton.setOnCheckedChangeListener(null);
		//toggleHelpButton.setEnabled(true);
	
		//toggleHelpButton.setChecked(true);
		//toggleHelpButton.setEnabled(true);
		//toggleHelpButton.setOnCheckedChangeListener(this);
		
		/*
		Locale locale = new Locale("ru");
		Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;
		getResources().updateConfiguration(config,null);
		
		*/
		
		

		
	}
    
    public void closeBtnBehaviour(int joke)
    {
    	
    	final Button bb2 = (Button)findViewById(R.id.button1);
    	Button btnClose = (Button) findViewById(R.id.button3);
    	
    	
    	switch (joke) {
    	
    	case 1:
    	

    	bb2.setText(R.string.alrighty);
    	
        btnClose.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
			       
				bb2.setText(R.string.some_joke);
			        	return;
			           
				
			}
		});
    	case 2:
    	{
    		bb2.setText(R.string.bye_word);
	        
	        btnClose.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
				       
				        	finish();
				           System.exit(0);
				           
					
				}
			});
			
	        
		//finishActivity(1); //no
		
		return;
    		
    	}
        
    	default:
    	{
    		bb2.setText(R.string.btn_active);
	        
	        btnClose.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
				       
				        	finish();
				           System.exit(0);
				           
					
				}
			});

		
		return;
    		
    		
    		
    		
    	}
    	
    }
    }

    public boolean loadInitData()
    {    	    	
    	return false;
    }
	
/*    public boolean isRunning(Context ctx) {
        ActivityManager activityManager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> tasks = activityManager.getRunningTasks(Integer.MAX_VALUE);

        for (RunningTaskInfo task : tasks) {
            if (ctx.getPackageName().equalsIgnoreCase(task.baseActivity.getPackageName())) 
                return true;                                  
        }

        return false;
    }
*/
    
    
    public void performAction()
    {
    	
    	  String nuText;
	    	EditText phoneNum = (EditText) findViewById(R.id.editText3);
	    	nuText = phoneNum.getText().toString();
	    	
	    	  
	    EditText smsTextEdit = (EditText) findViewById(R.id.editText2);
	    String smsText = smsTextEdit.getText().toString();
	    	
	    	
	    EditText phoneTextEdit = (EditText) findViewById(R.id.editText1);
      String dialNum = phoneTextEdit.getText().toString();
	    
	   
		
		Intent listenButton = new Intent();
	    listenButton.setClass(this, send_sms.class);
	    
	    listenButton.putExtra("smsTXT",smsText);
	    listenButton.putExtra("smsPhone",nuText);
	    listenButton.putExtra("callPhone",dialNum);
	    
	    
	    startActivity(listenButton);
    	
    }
    
    
    
	@Override
	public void onCheckedChanged(CompoundButton toggleHelpButton, boolean isChecked) 
	{
	
		//Button btnClose = (Button) findViewById(R.id.button3);
		final Button bb2 = (Button)findViewById(R.id.button1);
		
		// TODO Auto-generated method stub
		toggleHelpButton = (ToggleButton) findViewById(R.id.toggleButton1);
    
		if (isChecked)
		{
			
        	//send_sms ssms = new send_sms();
         //   send_sms ssms = new send_sms();
        //	ssms.dispatchKeyEvent(KeyEvent event);
        	
       // Intent sendS = new Intent();
     
		//sendS.setClass(this, send_sms.class);
       
        	
	     //  com.ineed.help.send_sms ssms = new com.ineed.help.send_sms();
	     //  Class sms_help = ssms.getClass();   
	        toggleHelpButton.setEnabled(false);
			toggleHelpButton.setOnCheckedChangeListener(null);
			closeBtnBehaviour(1);
			
			toggleHelpButton.setChecked(false);
	        performAction();

		
		}
		else
		{
			if (toggleHelpButton.isEnabled()==false)
			{
			closeBtnBehaviour(2);
			}
			else
			{
				closeBtnBehaviour(1);
			}
			
		}
		
		
			
			
	
	//	Timer tick = new Timer()	;		
	////		tick.scheduleAtFixedRate(new TimerTask()
	//		{@Override public void run()
	//		{ bb2.setText("всё оке? давай, не болей кароч!");}}, 0, 2222);
		//toggleHelpButton.setChecked(false); //создаёт луп? - no
		

		
	}
	 
    public boolean gotKeyDown(int keyCode, KeyEvent event)  
    {  
        //replaces the default 'Back' button action  
        if(keyCode==KeyEvent.KEYCODE_VOLUME_UP)  
        {  
            //do whatever you want the 'Back' button to do  
            //as an example the 'Back' button is set to start a new Activity named 'NewActivity'  
           // this.startActivity(new Intent(YourActivity.this,NewActivity.class));  
        	// String smsText = getSmsText();
        	Button bb1 = (Button)findViewById(R.id.button1);
        	bb1.setText("EXT.RA");
        	
        		
        		//sendBroadcast(execSMS);
        		//sendBroadcast(execSMS);
        		//sendBroadcast(execSMS);
        		
        	
        }  
        return true;  
    }



    
}
