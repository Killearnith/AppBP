package com.example.appbp.Actividades.Servicios;

import static com.google.android.gms.auth.api.phone.SmsRetriever.SMS_RETRIEVED_ACTION;

import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.util.Log;
import android.content.Context;
import android.content.Intent;
//---Imports de google para la API SMSRetriever
import com.example.appbp.Actividades.OtpActivity;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;
//----- Import del modelo OTP

//REF: https://developers.google.com/identity/sms-retriever/request
/**
 * SMSBroadcastReceiver recibe el mensaje SMS llega a la aplicación y parsea los campos para obtener los diferentes,
 * datos que se mandan por el mensajes SMS como el código OTP.
 * El SMS se obtiene de SmsRetriever.SMS_RETRIEVED_ACTION.
 */
public class SMSBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "BroadcastSMS";
    private String nTel;
    @Override
    public void onReceive(Context context, Intent intent) {
        String nTel = intent.getStringExtra("tel");
        if (SMS_RETRIEVED_ACTION.equals(intent.getAction())) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                Status status = (Status) extras.get(SmsRetriever.EXTRA_STATUS);
                if (status != null)
                    if (CommonStatusCodes.SUCCESS==0) {
                        // Obtiene el texto del SMS
                        String message = (String) extras.get(SmsRetriever.EXTRA_SMS_MESSAGE);
                        if(message.contains("g3Mji1k3j7Q")) {
                            String[] partes2 = new String[0];
                                String[] partes = message.split(" ");
                                if (partes.length > 3) {
                                    partes2 = partes[4].split("\n");
                                } else {
                                    Log.d(TAG, "Error al traer el Mensaje SMS.");
                                }
                                Intent i = new Intent(context, OtpActivity.class); //Se envía a la actividad
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                i.putExtra("message", partes2[0]);
                                context.startActivity(i);
                        }
                    }else if (CommonStatusCodes.TIMEOUT==5){
                        Log.d(TAG,"El tiempo del broadcast ha excedido su limite de 5 minutos.");
                    }else Log.d(TAG,"Error no contemplado.");
            }
        }
    }
}