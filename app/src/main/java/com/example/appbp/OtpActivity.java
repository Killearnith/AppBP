package com.example.appbp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class OtpActivity extends AppCompatActivity {
    private Button bCont;
    private ProgressBar pBar;
    private EditText cOTP;
    private String clave, nTel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        //Esconder la barra superior de la APP
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        //Obtenemos los datos del bundle de la actividad anterior
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            nTel = extras.getString("tel");
        }

        //Enlazar views
        cOTP = (EditText) findViewById(R.id.ClaveOTP);
        bCont = (Button) findViewById(R.id.buttonContinuar);
        pBar = (ProgressBar) findViewById(R.id.progressBar);
        pBar.setVisibility(View.INVISIBLE);

        //Comenzamos el cliente SMSRetriever
        inicioClienteSMSRetriever();
    }



    public void onContinuar(View v) {
        pBar.setVisibility(View.VISIBLE);
        clave = String.valueOf(cOTP.getText());
        Toast.makeText(this, "nTel es: "+nTel, Toast.LENGTH_LONG).show();
        //Codigo correspondiente al envio por API Rest al Servidor para comprobar la clave OTP.

        //Código necesario para obtener el codigo hash de la app
        //AppSignatureHelper appSignatureHelper = new AppSignatureHelper(this);
        //Log.d(TAG,"El código hash de la app es: "+appSignatureHelper.getAppSignatures().get(0));
        //Codigo HASH de la app es: g3Mji1k3j7Q

    }

    private void inicioClienteSMSRetriever(){
        // Get an instance of SmsRetrieverClient, used to start listening for a matching
        // SMS message.
        SmsRetrieverClient client = SmsRetriever.getClient(this /* context */);

        // Starts SmsRetriever, which waits for ONE matching SMS message until timeout
        // (5 minutes). The matching SMS message will be sent via a Broadcast Intent with
        // action SmsRetriever#SMS_RETRIEVED_ACTION.
        Task<Void> task = client.startSmsRetriever();

        // Listen for success/failure of the start Task. If in a background thread, this
        // can be made blocking using Tasks.await(task, [timeout]);
        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // Successfully started retriever, expect broadcast intent
                // ...
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Failed to start retriever, inspect Exception for more details
                // ...
            }
        });
    }
}