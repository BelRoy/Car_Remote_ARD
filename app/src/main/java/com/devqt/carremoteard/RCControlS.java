package com.devqt.carremoteard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RCControlS extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rccontrol_s);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu , menu);
        return true;
    }

    public void settingsPress(MenuItem menuitem) {
        startActivity(new Intent(this, PreferencesActivity.class));
    }

    private class DirRequest extends AsyncTask<String, Void, Void> {
        private String urlBase;

        DirRequest(Context context) {
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
            Log.i("CarRemote", settings.getAll().toString());
            String hostName = settings.getString("hostName", "192.168.1.123");
            this.urlBase = "http://" + hostName + "/";
        }

        @Override
        protected Void doInBackground(String...dirs) {
            for (String dir : dirs) {
                URL url;
                try {
                    url = new URL(this.urlBase + dir);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    Log.w("CarRemoteARD", Log.getStackTraceString(e));
                    return null;
                }
                Log.i("CarRemoteARD", "Contacting " + url.toString());
                HttpURLConnection urlConnection;
                try {
                    urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    Log.i("CarRemoteARD", in.toString());
                } catch (IOException e) {
                    Log.w("CarRemoteARD", Log.getStackTraceString(e));
                    return null;
                }
                urlConnection.disconnect();
            }
            return null;
        }
    }

    private void dirRequest(String dir)
    {
        new DirRequest(this).execute(dir);
    }

    public void pressForward(View view) {
        dirRequest("F");
    }

    public void pressBackward(View view) {
        dirRequest("B");
    }

    public void pressStop(View view) {
        dirRequest("SG");
    }

    public void pressLeft(View view) {
        dirRequest("LF");
    }

    public void pressRight(View view) {
        dirRequest("RF");
    }
}

