package com.example.chathuranga_pamba.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


public class SigninActivity  extends AsyncTask<String,Void,String> {
    private TextView statusField,roleField;
    private Context context;


    //flag 0 means get and 1 means post.(By default it is get.)
    public SigninActivity(Context context,TextView statusField,TextView roleField) {
        this.context = context;
        this.statusField = statusField;
        this.roleField = roleField;

    }



    @Override
    protected String doInBackground(String... arg0) {


            try{
                String username = arg0[0].toString();
                String password = arg0[1].toString();

                String username1 = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
                String password1= "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

                String link = "http://chathurangasandunkumara.net16.net/Tologin.php?"+username1+password1;
                //String link = "http://192.168.56.1/Tologin.php?"+username1+password1;

                System.out.println(link);


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

                String result = in.readLine().charAt(0)+"";
                System.out.println(result);


                in.close();

                return result;
            } catch (MalformedURLException e) {
                return new String("MalformedURLException");
            } catch (ClientProtocolException e) {
                return new String("ClientProtocolException");
            } catch (URISyntaxException e) {
                e.printStackTrace();
                return new String("URISyntaxException");

            } catch (IOException e) {
                return new String("IOEX");
            }




    }

}
