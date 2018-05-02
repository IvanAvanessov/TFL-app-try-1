package com.example.vano.example2;

import java.net.MalformedURLException;
import java.net.URL;
import java.io.*;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;


public class HttpRequest extends AsyncTask<String, String, String[]> {

    int thisURL = 0;
    int errorCode = 3;
    int result = 1;
    int counter = 0;
    int requestID = 2;

    private ProgressDialog thisPrDialog;
    //private MainActivity thisActivity;
    private MainViewActivity context;

    public HttpRequest(MainViewActivity c) {
            super();
            context = (MainViewActivity) c;
    }

    @Override
    protected String[] doInBackground(String... params){ //String thisURL, String result, int responseCode) {
        HttpsURLConnection con = null;
        counter = 0;
        try {
            int responseCode = 0;
            while (responseCode != 200 && counter <10) {
                URL url = new URL(params[thisURL]);
                con = (HttpsURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                responseCode = con.getResponseCode();
                counter ++;
            }
            //con.connect();
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                params[result] = response.toString();
            }
            else {
                params[result] = "";
                params[errorCode] = Integer.toString(responseCode);
            }

        } catch (MalformedURLException e){
            params[result] = e.getMessage();
            params[errorCode] = "0";
            e.printStackTrace();
        } catch (IOException e){
            params[result] = e.getMessage();
            params[errorCode] = "0";
            e.printStackTrace();
        } finally {
            if(con != null) {
                con.disconnect();
            }
        }
        return params;

        //return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        thisPrDialog = new ProgressDialog(context);
        thisPrDialog.setMessage("Downloading data");
        thisPrDialog.show();
    }

    @Override
    protected void onPostExecute(String... results) {
        super.onPostExecute(results);

        context.parseJSON(results[result], results[requestID]);

        thisPrDialog.dismiss();
    }
    /*
    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(Object o) {
        super.onCancelled(o);
    }
    */
    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
