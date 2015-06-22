package com.app.android.kent.sportlottery;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class Main extends ActionBarActivity {
    private static final String TAG = "Main";
    private String urlSite = "http://tw.yahoo.com";
    private HttpURLConnection urlConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        start.setOnClickListener(new SportData());
    }

    private Button start;
    private TextView data;

    private void initView() {
        start = (Button) findViewById(R.id.btn_read);
        data = (TextView) findViewById(R.id.tv_data);
    }

    public class SportData implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            //Log.e(TAG, "onClick");

            new AsyncTask<String, Void, Void>() {
                @Override
                protected Void doInBackground(String... params) {
                    try {
                        URL url = new URL(params[0]);
                        urlConnection = (HttpURLConnection) url.openConnection();
                        urlConnection.setRequestMethod("GET");
                        urlConnection.setDoOutput(true);
//                        urlConnection.setChunkedStreamingMode(0);
//                        urlConnection.setReadTimeout(10000);
//                        urlConnection.setConnectTimeout(15000);
                        urlConnection.setUseCaches(false);
//                        urlConnection.connect();

                        int code = urlConnection.getResponseCode();
                        Log.d(TAG, "code = " + code);

                        InputStream is = urlConnection.getInputStream();
                        InputStreamReader isr = new InputStreamReader(is);
                        BufferedReader br = new BufferedReader(isr);
                        String line;
                        while ((line = br.readLine()) != null) {
                            Log.d(TAG, line);
                        }
                        br.close();
                        isr.close();
                        is.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {

                        urlConnection.disconnect();
                    }
                    return null;
                }
            }.execute(urlSite);
        }
    }
}



