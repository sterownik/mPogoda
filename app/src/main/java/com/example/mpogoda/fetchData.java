package com.example.mpogoda;

import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class fetchData extends AsyncTask<Void,Void,Void> {
    String data="";
    String dataParse = "";
    String singleParsed="";

    @Override
    protected void onPostExecute(Void aVoid) {


        super.onPostExecute(aVoid);

        MainActivity.data.setText(this.dataParse);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("https://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=439d4b804bc8187953eb36d2a8c26a02");
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();

            InputStream inputStream = httpURLConnection.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line ="";
            while (line!=null){
                line = bufferedReader.readLine();
                data = data + line;

            }
            JSONArray JA = new JSONArray(data);



           for (int i=0;i<JA.length();i++){
               JSONObject JO =(JSONObject) JA.get(i);
               singleParsed = "weather";


               dataParse = dataParse +singleParsed+"\n";


           }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
