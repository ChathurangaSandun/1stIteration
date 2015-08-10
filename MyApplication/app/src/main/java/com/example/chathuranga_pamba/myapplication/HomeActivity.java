package com.example.chathuranga_pamba.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutionException;

import static java.util.Calendar.getInstance;


public class HomeActivity extends ActionBarActivity {

    private TextView textView;
    private Button logoutButton;

    private EditText imeiNumberEditText,
                     latitudeEditText,
                    longitudeEditText,
                    timeEditText,
                    dateEditText;


    GpsTracker gps;

    String imeiNumber;
    double latitude;
    double longitude;
    String date;
    String time;

    boolean gpsEnable = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ActionStartsHere();


        textView = (TextView) findViewById(R.id.label);
        logoutButton = (Button) findViewById(R.id.logoutbutton);


        String username = getIntent().getStringExtra("username");

        textView.setText(username+ " is Successfully login");

        logoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                gpsEnable = true;
                finish();
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void ActionStartsHere()
    {
        againStartGPSAndSendFile();
    }


    public void againStartGPSAndSendFile(){ //recursion for send data
        new CountDownTimer(20000,10000){

            @Override
            public void onTick(long millisUntilFinished) {


                    imeiNumberEditText = (EditText) findViewById(R.id.imei);
                    latitudeEditText = (EditText) findViewById(R.id.latitude);
                    longitudeEditText = (EditText) findViewById(R.id.longitude);
                    dateEditText = (EditText) findViewById(R.id.date);
                    timeEditText = (EditText) findViewById(R.id.time);

                    //get data

                    //imei number
                    TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    AndroidTelephonyManager androidTelephonyManager = new AndroidTelephonyManager();
                    imeiNumber = androidTelephonyManager.getDeviceID(telephonyManager);

                    // imri numnber to text
                    imeiNumberEditText.setText(imeiNumber);


                    gps = new GpsTracker(HomeActivity.this);

                    if (gps.CanGetLocation()) {
                        //codinates
                        latitude = gps.getLatitide();
                        longitude = gps.getLongtitude();
                        System.out.println(latitude);




                        //date and time
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        date = dateFormat.format(getInstance().getTime());
                        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                        time = timeFormat.format(getInstance().getTime());

                        //codinates to text
                        latitudeEditText.setText(String.valueOf(latitude));
                        longitudeEditText.setText(String.valueOf(longitude));

                        //date and time to text
                        dateEditText.setText(date);
                        timeEditText.setText(time);


                        System.out.println(latitude + " " + longitude + " " + time);


                        AsyncTask<String, Void, String> execute =
                                new SendLocation().execute(imeiNumber, String.valueOf(latitude), String.valueOf(longitude), date, time);

                        try {
                            System.out.println("____________"+execute.get()+"____________");

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    } else {
                        gps.showSettingsAlerts();
                    }
                }



            @Override
            public void onFinish(){
                if(!gpsEnable){
                    ActionStartsHere(); //when logout recursion is stopped
                }


            }

        }.start();
    }


}
