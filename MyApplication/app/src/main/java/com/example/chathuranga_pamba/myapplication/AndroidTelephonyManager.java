package com.example.chathuranga_pamba.myapplication;

/**
 * Created by Chathuranga - Pamba on 5/12/2015.
 */
import android.telephony.TelephonyManager;

public class AndroidTelephonyManager  {   // for imei number


    String getDeviceID(TelephonyManager phonyManager){

        String id = phonyManager.getDeviceId();
        if (id == null){
            id = "not available";
        }

        int phoneType = phonyManager.getPhoneType();
        switch(phoneType){
            case TelephonyManager.PHONE_TYPE_NONE:
                return id;

            case TelephonyManager.PHONE_TYPE_GSM:
                return  id;

            case TelephonyManager.PHONE_TYPE_CDMA:
                return  id;

 /*
  *  for API Level 11 or above
  *  case TelephonyManager.PHONE_TYPE_SIP:
  *   return "SIP";
  */

            default:
                return  id;
        }

    }
}