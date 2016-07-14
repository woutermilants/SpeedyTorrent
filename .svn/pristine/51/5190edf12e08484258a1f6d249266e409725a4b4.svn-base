package com.example.SpeedyTorrent;

import android.os.AsyncTask;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Wouter
 */
public class MagnetExecuterActivity extends AsyncTask<String, String, String> {

    String parameterType = null;

    public MagnetExecuterActivity(String parameterType) {
        super();
        this.parameterType = parameterType;
    }

    @Override
    protected String doInBackground(String... params) {

        final HttpClient httpclient = new DefaultHttpClient();
        final HttpPost httppost = new HttpPost("http://wouter.no-ip.biz:5680/torrentgen/jsonmagnet");
        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("param", params[0]));
            nameValuePairs.add(new BasicNameValuePair("type", parameterType));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            HttpResponse response = httpclient.execute(httppost);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "success";
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}
