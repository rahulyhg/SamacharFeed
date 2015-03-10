package com.ashrif786.helloworld;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView mRssFeed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRssFeed = (TextView) this.findViewById(R.id.editText1);
        (new GetAndroidPitRssFeedTask()).execute();
    }

    private class GetAndroidPitRssFeedTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            String result = "";
            InputStream in = null;
            try {
                URL url = new URL("http://www.androidpit.com/feed/main.xml");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                in = conn.getInputStream();
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                for (int count; (count = in.read(buffer)) != -1; ) {
                    out.write(buffer, 0, count);
                }
                byte[] response = out.toByteArray();
                String rssFeed = new String(response, "UTF-8");
                return rssFeed;
                //mRssFeed.setText(rssFeed);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(String rssFeed) {
            mRssFeed.setText(rssFeed);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
