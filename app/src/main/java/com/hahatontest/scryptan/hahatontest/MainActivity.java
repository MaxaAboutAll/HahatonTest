package com.hahatontest.scryptan.hahatontest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

import io.chirp.connect.ChirpConnect;
import io.chirp.connect.interfaces.ConnectEventListener;
import io.chirp.connect.interfaces.ConnectSetConfigListener;
import io.chirp.connect.models.ChirpError;

public class MainActivity extends AppCompatActivity {
    ChirpConnect chirpConnect;
    TextView myTV;
    ConnectEventListener connectEventListener;
    public final int RESULT_REQUEST_RECORD_AUDIO = 6001;
    private final String TAG = "ChirpHackatonTry";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String KEY = "D5144D4b91b17fBf0266Dbd9d";
        String SECRET = "69Cc407c95ccdCb4Ae2cc50AAa226B5f6F510fDcc23bEfCe50";
        String CONFIG = "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";
        myTV = findViewById(R.id.textView);

        chirpConnect = new ChirpConnect(this, KEY, SECRET);
        chirpConnect.setConfig(CONFIG, new ConnectSetConfigListener() {

            @Override
            public void onSuccess() {
                Log.i(TAG, "Config successfully set.");
            }

            @Override
            public void onError(ChirpError setConfigError) {
                Log.e(TAG, setConfigError.getMessage());
            }
        });
        connectEventListener = new ConnectEventListener() {

            @Override
            public void onSending(byte[] payload, byte channel) {
                Log.v(TAG, "This is called when a payload is being sent " + payload.toString() + " on channel: " + channel);
                updateLastPayload("sending");
            }

            @Override
            public void onSent(byte[] payload, byte channel) {
                Log.v(TAG, "This is called when a payload has been sent " + payload.toString()  + " on channel: " + channel);
                updateLastPayload("sended");
            }

            @Override
            public void onReceiving(byte channel) {
                Log.v(TAG, "This is called when the SDK is expecting a payload to be received on channel: " + channel);
                updateLastPayload("receiving");
            }

            @Override
            public void onReceived(byte[] payload, byte channel) {
                Log.v(TAG, "This is called when a payload has been received " + payload  + " on channel: " + channel);
                updateLastPayload(Arrays.toString(payload));
            }

            @Override
            public void onStateChanged(byte oldState, byte newState) {
                Log.v(TAG, "This is called when the SDK state has changed " + oldState + " -> " + newState);
            }

            @Override
            public void onSystemVolumeChanged(int old, int current) {
                Log.d(TAG, "This is called when the Android system volume has changed " + old + " -> " + current);
            }

        };
        chirpConnect.start();
        chirpConnect.setListener(connectEventListener);
        Toast.makeText(getApplicationContext(),"SDK STARTED", Toast.LENGTH_SHORT).show();
    }

    public void updateLastPayload(final String text){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myTV.setText(text);
            }
        });
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.button:
                byte a[] ={1};
                chirpConnect.send(a);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.RECORD_AUDIO}, RESULT_REQUEST_RECORD_AUDIO);
        }
        else {
            chirpConnect.start();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RESULT_REQUEST_RECORD_AUDIO: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    chirpConnect.start();
                }
                return;
            }
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        chirpConnect.stop();
        try {
            chirpConnect.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        chirpConnect.stop();
        try {
            chirpConnect.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
