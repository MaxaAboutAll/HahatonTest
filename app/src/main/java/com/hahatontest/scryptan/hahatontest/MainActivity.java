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
        String KEY = "YOUR_KEY";
        String SECRET = "YOUR_SECRET_KEY";
        String CONFIG = "YOOUR_CONFIG";
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
