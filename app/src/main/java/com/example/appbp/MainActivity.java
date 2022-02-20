package com.example.appbp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "HASH" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_AppBP);
        setContentView(R.layout.activity_telefono);

        /*
        // Inside Main Activity
        Log.d(TAG, "HashKey: " + appSignatureHashHelper.getAppSignatures().get(0));

         */
    }
}