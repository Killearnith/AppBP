package com.example.appbp.Android;

import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.util.Log;
import android.content.Context;
import android.content.Intent;
//---Imports de google para la API SMSRetriever
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;
//----- Import del modelo OTP
import com.example.appbp.Modelo.OTPClave;

/**
 * SMSBroadcastReceiver recibe el mensaje SMS llega a la aplicacióny parsea los campos para obtener los diferentes,
 * datos que se mandan por el mensajes SMS como el código OTP.
 * El SMS se obtiene de SmsRetriever.SMS_RETRIEVED_ACTION.
 */
public class SMSBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "BroadcastSMS";
    private OTPClave otpClave;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (SmsRetriever.SMS_RETRIEVED_ACTION.equals(intent.getAction())) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                Status status = (Status) extras.get(SmsRetriever.EXTRA_STATUS);
                if (status != null)
                    if (CommonStatusCodes.SUCCESS==0) {
                        // Get SMS message contents
                        String message = (String) extras.get(SmsRetriever.EXTRA_SMS_MESSAGE);
                        if (message != null) {
                            String[] partes = message.split(" ");
                            otpClave.setClave(partes[4]);
                        }
                    }else if (CommonStatusCodes.TIMEOUT==15){
                        Log.d(TAG,"El tiempo del broadcast ha excedido su limite de 5 minutos.");
                    }else Log.d(TAG,"Error no contemplado.");
            }
        }
    }
}