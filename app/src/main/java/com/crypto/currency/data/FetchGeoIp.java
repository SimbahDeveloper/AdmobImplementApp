package com.crypto.currency.data;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import com.crypto.currency.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

/**
 * Created by MBP on 12/29/17.
 */

public class FetchGeoIp extends AsyncTask<Void,Void,Void> {
    private String data="";
    private String dataAll="";
    private String lines = "";

    @Override
    protected Void doInBackground(Void... voids) {
        BufferedReader bufferedReader = null;
        try {
            URL url = new URL("http://freegeoip.net/json/");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = bufferedReader.readLine();

            while (!Objects.equals(line, lines)) {
                data = line + "\n";
                lines = line;
            }

            JSONObject JO = new JSONObject(data);
            dataAll = "IP : "+JO.getString("ip")+"\n"+
                    "Country Code : "+JO.getString("country_code")+"\n"+
                    "Country Name : "+JO.getString("country_name")+"\n"+
                    "Region Code : "+JO.getString("region_code")+"\n"+
                    "Region Name : "+JO.getString("region_name")+"\n"+
                    "City : "+JO.getString("city")+"\n"+
                    "Zip Code : "+JO.getString("zip_code")+"\n"+
                    "Time Zone : "+JO.getString("time_zone")+"\n"+
                    "Latitude : "+JO.getString("latitude")+"\n"+
                    "Longitude : "+JO.getString("longitude")+"\n"+
                    "Metro Code : "+JO.getString("metro_code")+"\n\n\n";


        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @SuppressLint("SetTextI18n")
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        MainActivity.data.setText("Loading...");
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.d("IP1", "onPostExecute: "+data);
        Log.d("IP2", "onPostExecute: "+dataAll);
        MainActivity.data.setText(this.dataAll);
    }
}
