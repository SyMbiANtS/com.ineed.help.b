package com.ineed.help.b;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ActivityManager.RunningTaskInfo;


import android.content.CursorLoader;
import android.content.Intent;
import android.app.PendingIntent;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import android.os.Build;
import android.annotation.TargetApi;

import android.provider.Contacts;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.SyncStateContract.Constants;
import android.widget.Toast;


/* Created by ra on 31/08/13.
*/

public class ContactList extends Activity
{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_main);
        Intent intent = getIntent();
        getContactNum(intent);
    }

    final static int PICK_CONTACT_REQUEST = 0;


    public String getContactNum(Intent da)
    {
        String ContNumber = "911";

        da = new Intent(Intent.ACTION_PICK);
        da.setType(ContactsContract.Contacts.CONTENT_TYPE);

        try
        {
            startActivityForResult(da, PICK_CONTACT_REQUEST  );
        }
        catch (Exception e)
        {

        }

        return ContNumber;
    }
    @Override
    @TargetApi(11)
    public void onActivityResult(int reqCode, int resCode, Intent data) {
        super.onActivityResult(reqCode, resCode, data);
        String ContactNumber = "11";
        switch (reqCode) {
            case (PICK_CONTACT_REQUEST) :

                if (resCode == Activity.RESULT_OK) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                    {
                        getContactNumber11(data);

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
                            ContactNumber = phNu.getString(phNu.getColumnIndex(Phone.NUMBER));

                            EditText et = (EditText)findViewById(R.id.editText1);
                            et.setText(ContactNumber);
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
}
