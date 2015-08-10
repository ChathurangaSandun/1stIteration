package com.example.chathuranga_pamba.myapplication;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

/**
 * Created by Chathuranga - Pamba on 6/8/2015.
 */
public class SendLocation extends AsyncTask<String,Void,String> {

    public SendLocation (){
    }



    @Override
    protected String doInBackground(String... args) {
        String imeiNumber = args[0].toString();
        String latitude = args[1].toString();
        String longitude = args[2].toString();
        String date = args[3].toString();
        String time = args[4].toString();

        try {
            imeiNumber = URLEncoder.encode("imeinumber", "UTF-8") + "=" + URLEncoder.encode(imeiNumber, "UTF-8")+"&";
            latitude  = URLEncoder.encode("latitude", "UTF-8") + "=" + URLEncoder.encode(latitude, "UTF-8")+"&";
            longitude= URLEncoder.encode("longitude", "UTF-8") + "=" + URLEncoder.encode(longitude, "UTF-8")+"&";
            date = URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8")+"&";
            time = URLEncoder.encode("time", "UTF-8") + "=" + URLEncoder.encode(time, "UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }



        String link = "http://chathurangasandunkumara.net16.net/VehicleLocation.php?"+imeiNumber+latitude+longitude+date+time;
        // //String link = "http://192.168.56.1/

        Log.e("TAG",link);


        try {

            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(link));
            HttpResponse response = null;
            try {
                response = client.execute(request);
            } catch (IOException e) {
                return new String("response");
            }


            BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            String resultAffected = in.readLine().charAt(0)+"";
            System.out.println(resultAffected);


            return resultAffected;
        }catch (IOException e){
            return new String("IOEX");
        } catch (URISyntaxException e) {
            return new String("URISyntaxException");
        }



    }
}
