package com.example.chathuranga_pamba.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;


public class MainActivity extends ActionBarActivity {


    private EditText usernameField,passwordField;
    private TextView status,role,method;
    private Button loginButton,callButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e("TAG","Strart___________________________________________________");

        usernameField = (EditText)findViewById(R.id.editText1);
        passwordField = (EditText)findViewById(R.id.editText2);

        status = (TextView)findViewById(R.id.textView6);
        role = (TextView)findViewById(R.id.textView7);


        loginButton = (Button) findViewById(R.id.button1);

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {

                    String username = usernameField.getText().toString();
                    String password = passwordField.getText().toString();

                    if(!("".equals(username)) && !("".equals(password))){

                        String result = login();
                        if("0".equals(result)){
                            status.setText("Un - successfull");
                            role.setText(result);
                            Toast.makeText(getApplicationContext(),
                                    "Your Username or Password not correct", Toast.LENGTH_LONG)
                                    .show();
                            usernameField.setText("");
                            passwordField.setText("");


                        }else{
                            status.setText("successfull");
                            role.setText(result);

                            Intent intent = new Intent(getApplicationContext(),HomeActivity.class);

                            intent.putExtra("username",usernameField.getText().toString());

                            startActivity(intent);

                            finish();

                        }

                    }else {
                        Toast.makeText(getApplicationContext(),
                                "Please enter the credentials!", Toast.LENGTH_LONG)
                                .show();

                    }







                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });


        callButton = (Button) findViewById(R.id.callButton);

        callButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(Intent.ACTION_CALL);
                String phNum = "tel:" + "0718256773";
                myIntent.setData(Uri.parse(phNum));
                startActivity( myIntent ) ;


            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public String login() throws ExecutionException, InterruptedException {
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        AsyncTask<String, Void, String> result = new SigninActivity(this, status, role).execute(username, password);
       return result.get();
    }





}
