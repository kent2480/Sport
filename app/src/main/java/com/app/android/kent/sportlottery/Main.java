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
import java.net.URL;
import java.net.URLConnection;


public class Main extends ActionBarActivity {
    private static final String TAG = "Main";
    private String url = "https://www.sportslottery.com.tw/web/services/rs/betting/games/15102/0.json?status=active&limit=21&action=excludeTournamentWithExceptionPriority&marketLimit=1&sportId=s-442&locale=tw&brandId=defaultBrand&channelId=1";

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

    public class SportData implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Log.e(TAG, "onClick");

            new AsyncTask<String, Void, Void>() {
                @Override
                protected Void doInBackground(String... params) {
                    try {
                        URL url = new URL(params[0]);
                        URLConnection connection = url.openConnection();
                        InputStream is = connection.getInputStream();
                        InputStreamReader isr = new InputStreamReader(is);
                        BufferedReader br = new BufferedReader(isr);
                        String line;
                        while((line = br.readLine()) != null ) {
                            Log.d(TAG,"line = " + line);
                        }
                        br.close();
                        isr.close();
                        is.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }.execute(url);
        }
    }
}
