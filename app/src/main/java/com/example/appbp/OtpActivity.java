package com.example.appbp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

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

        //API Rest request
        final TextView textView = (TextView) findViewById(R.id.text);
    /*
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://smsretrieverservera-default-rtdb.europe-west1.firebasedatabase.app/numeros.json";
        Log.d("Test","Aqui llego");
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        textView.setText("Response is: "+ response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorMessage = error.getMessage();
                textView.setText("That didn't work! "+errorMessage);
            }
        });
        queue.add(stringRequest);
        Log.d("Test","Aqui tmb");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        textView.setText("Response: " + response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        */

        // Instantiate the RequestQueue.
        String url ="https://smsretrieverservera-default-rtdb.europe-west1.firebasedatabase.app/numeros.json";
        Log.d("Test","Aqui llego");
        // Request a string response from the provided URL.
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject postData = new JSONObject();
        try {
            postData.put("tel", nTel);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getApplicationContext(), "Response: "+response, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(jsonObjectRequest);
        Log.d("Test","Aqui tmb");
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