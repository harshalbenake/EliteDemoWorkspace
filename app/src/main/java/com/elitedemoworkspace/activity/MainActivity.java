package com.elitedemoworkspace.activity;

import android.Manifest;
import android.app.AppOpsManager;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.elitedemoworkspace.R;
import com.elitedemoworkspace.services.BackgroundService;
import com.elitetechnologies.customlibs.CustomActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private long mEventID=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(getApplicationContext(), "onCreate Called", Toast.LENGTH_SHORT).show();

        Button btn_pressme = (Button) findViewById(R.id.btn_pressme);
        btn_pressme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Button Is Pressed");
                Toast.makeText(getApplicationContext(), "Button Pressed", Toast.LENGTH_SHORT).show();
            }
        });

        Button btn_display = (Button) findViewById(R.id.btn_display);
        btn_display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                startActivity(intent);
            }
        });

        Button btn_addevent = (Button) findViewById(R.id.btn_addevent);
        btn_addevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               addCalenderEvent();
            }
        });

        Button btn_viewevent = (Button) findViewById(R.id.btn_viewevent);
        btn_viewevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewCalenderEvent();
            }
        });

        Button btn_sensors = (Button) findViewById(R.id.btn_sensors);
        btn_sensors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SensorsActivity.class);
                startActivity(intent);
            }
        });

        Button btn_cordova = (Button) findViewById(R.id.btn_cordova);
        btn_cordova.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CordovaActivity.class);
                startActivity(intent);
            }
        });

        Button btn_module = (Button) findViewById(R.id.btn_module);
        btn_module.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CustomActivity.class);
                startActivity(intent);
            }
        });

        ImageView iv_logo = (ImageView) findViewById(R.id.iv_logo);
        Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_logo);
        iv_logo.startAnimation(animation);

        askPermissions();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(getApplicationContext(), "onResume Called", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(getApplicationContext(), "onStart Called", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(getApplicationContext(), "onStop Called", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(getApplicationContext(), "onPause Called", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "onDestroy Called", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(getApplicationContext(), "onRestart Called", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(getApplicationContext(), "onBackPressed Called", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(getApplicationContext(), "onConfigurationChanged PORTRAIT", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(getApplicationContext(), "onConfigurationChanged LANDSCAPE", Toast.LENGTH_SHORT).show();
        }
    }

    private void askPermissions() {
        String[] permissions = new String[]{
                Manifest.permission.SEND_SMS,
                Manifest.permission.READ_SMS,
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.WRITE_CONTACTS,
                Manifest.permission.READ_CALENDAR,
                Manifest.permission.WRITE_CALENDAR,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE};
        ActivityCompat.requestPermissions(this, permissions, 10);


        boolean usage_granted = false;
        AppOpsManager appOps = (AppOpsManager)getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,android.os.Process.myUid(),getPackageName());
        if (mode == AppOpsManager.MODE_DEFAULT) {
            usage_granted = (checkCallingOrSelfPermission(android.Manifest.permission.PACKAGE_USAGE_STATS) == PackageManager.PERMISSION_GRANTED);
        } else {
            usage_granted = (mode == AppOpsManager.MODE_ALLOWED);
        }

        if(usage_granted==false) {
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 10: {
                for (int i = 0; i < grantResults.length; i++)
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        break;
                    }else{
                        startService(new Intent(MainActivity.this, BackgroundService.class));
                        contactProvider();
                    }
                return;
            }
        }
    }

    private void contactProvider() {
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        while (cursor.moveToNext())
        {
            String name=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            System.out.println("contactProvider name: "+name+" "+"phoneNumber: "+phoneNumber);
        }
        cursor.close();
    }

    private void addCalenderEvent() {
        Calendar beginTimeIntents = Calendar.getInstance();
      //  beginTimeIntents.set(2018, 6, 16, 10, 10);
        Calendar endTimeIntents = Calendar.getInstance();
       // endTimeIntents.set(2018, 6, 16, 20, 10);
        ContentResolver cr = getContentResolver();
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.DTSTART, beginTimeIntents.getTimeInMillis());
        values.put(CalendarContract.Events.DTEND,  endTimeIntents.getTimeInMillis());
        values.put(CalendarContract.Events.TITLE, "Elite Technologies");
        values.put(CalendarContract.Events.DESCRIPTION, "Evolution of Technology");
        values.put(CalendarContract.Events.EVENT_LOCATION, "India");
        values.put(CalendarContract.Events.CALENDAR_ID, 1);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, "India");
        Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
       // get the event ID that is the last element in the Uri
        mEventID = Long.parseLong(uri.getLastPathSegment());
    }

    private void viewCalenderEvent() {
        Uri uriView = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, mEventID);
        Intent intentViewEvent = new Intent(Intent.ACTION_VIEW)
                .setData(uriView);
        startActivity(intentViewEvent);
    }

}
